package org.de.jdbc.preparedstatement;

import org.de.jdbc.mapper.Product;
import org.de.jdbc.mapper.ResultSetMapper;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/de-jdbc", "root", "chaeji1994");
        PreparedStatement psUpdate = con.prepareStatement("UPDATE product SET `price` = `price` + ? WHERE id = ?;");
        psUpdate.setInt(1, 10000);
        psUpdate.setInt(2, 1);

        int updateResult = psUpdate.executeUpdate();
        System.out.println("result of update: " + updateResult);

        PreparedStatement psSelect = con.prepareStatement(
                "SELECT `id`, `name`, `updated_at`, `contents`, `price` from product where id between ? and ?");
        psSelect.setInt(1, 1);
        psSelect.setInt(2, 5);
        ResultSet rs = psSelect.executeQuery();
        while(rs.next()){
            ResultSetMapper.printRs(rs);
        }

        psSelect.setInt(1, 6);
        psSelect.setInt(2, 10);
        ResultSet rs2 = psSelect.executeQuery();
        List<Product> productList = new ArrayList<>();
        while(rs2.next()){
            productList.add(ResultSetMapper.create(rs2));
        }

        PreparedStatement psUpdateProduct = con.prepareStatement(
                "UPDATE product SET `id` = ?, `name` = ?, `updated_at` = ?, `contents` = ?, `price` = ? WHERE id = ?");


        for(Product product : productList) {
            product.setPrice(product.getPrice() - 1000);
            product.setUpdatedAt(LocalDateTime.now());

            psUpdateProduct.setInt(1, product.getId());
            psUpdateProduct.setString(2, product.getName());
            psUpdateProduct.setTimestamp(3, Timestamp.valueOf(product.getUpdatedAt()));
            psUpdateProduct.setString(4, product.getContents());
            psUpdateProduct.setInt(5, product.getPrice());
            psUpdateProduct.setInt(6, product.getId());

            psUpdateProduct.addBatch();
            psUpdateProduct.clearParameters();
        }

        int[] results = psUpdateProduct.executeBatch();
        for(int result : results) {
            System.out.println("result of update: " + result);
        }
    }
}
