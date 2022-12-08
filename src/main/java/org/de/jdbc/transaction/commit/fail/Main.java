package org.de.jdbc.transaction.commit.fail;

import org.de.jdbc.mapper.ResultSetMapper;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection con= DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/de-jdbc","root","chaeji1994");
        try {
            con.setAutoCommit(false);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT `id`, `name`, `updated_at`, `contents`, `price` FROM product WHERE id = 2");
            System.out.println("====== Before Start Update ======");
            while (rs.next()) {
                ResultSetMapper.printRs(rs);
            }
            stmt.executeUpdate("UPDATE product SET `price` = `price` + 10000 WHERE id = 2");
            stmt.executeUpdate("UPDATE product SET `price` = `price` + 10000 WHERE id = 3");

            stmt.executeUpdate("DELETE FROM product WHERE id = 2");

            con.commit();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getErrorCode() + ", " + sqlException.getMessage());
        } finally {
            con.close();
        }

        System.out.println("====== After Commit Failed ======");

        Connection con2 = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/de-jdbc","root","chaeji1994");
        Statement stmt2 = con2.createStatement();
        ResultSet rs2 = stmt2.executeQuery("SELECT `id`, `name`, `updated_at`, `contents`, `price` FROM product WHERE id = 2");
        if (rs2.next()) {
            // id 1
            ResultSetMapper.printRs(rs2);
        } else {
            System.out.println("no result");
        }
        con2.close();
    }
}
