package org.de.jdbc.resultset.methods;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/de-jdbc", "root", "chaeji1994"
        );
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("SELECT `id`, `name`, `updated_at`, `contents`, `price` from product where id between 1 and 5");

        // cursor init
        if(rs.next()) {
            // cursor
            printRs(rs);
        }
        if(rs.last()) {
            // cursor 5
            printRs(rs);
        }
        if(!rs.next()) {
            try {
                printRs(rs);
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getErrorCode() + ", " + sqlException.getMessage());
            }
        }
        rs.last();
        if(rs.previous()) {
            // cursor 4
            printRs(rs);
        }
        if(rs.absolute(2)) {
            // cursor 2
            printRs(rs);
        }
        if(rs.relative(2)) {
            // cursor 4
            printRs(rs);
        }
        if(rs.relative(-2)) {
            // cursor 2
            printRs(rs);
        }
        if(rs.first()) {
            // cursor 1
            printRs(rs);
        }
        if(!rs.previous()) {
            try {
                printRs(rs);
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getErrorCode() + ", " + sqlException.getMessage());
            }
        }
        if(!rs.previous()) {
            try {
                printRs(rs);
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getErrorCode() + ", " + sqlException.getMessage());
            }
        }
        con.close();
    }

    private static void printRs(ResultSet rs) throws SQLException {
        System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "
                +rs.getDate(3)+" "+rs.getString(4)
                +" "+rs.getInt(5));
    }
}
