package ord.de.jdbc.statement.batch;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection con= DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/de-jdbc","root",null);
        Statement stmt = con.createStatement();
        stmt.addBatch("SELECT `id`, `name`, `updated_at`, `contents`, `price` from product where id between 1 and 5");
        stmt.addBatch("UPDATE product SET `price` = `price` + 10000 where id = 1");
        try {
            int[] results = stmt.executeBatch();
        } catch (BatchUpdateException batchUpdateException) {
            System.out.println(batchUpdateException.getMessage());
        }

        stmt.addBatch("UPDATE product SET `price` = `price` + 10000 where id = 1");
        stmt.addBatch("UPDATE product SET `price` = `price` + 10000 where id = 2");
        stmt.addBatch("UPDATE product SET `price` = `price` + 10000 where id between 3 and 5");
        int[] results =stmt.executeBatch();

        for(int result : results) {
            if(result >= 0) {
                System.out.println("result of update: " + result);
            }
        }
        con.close();
    }
}
