package com.williams.kailyn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

/*Connects and Log-on to database*/
public class Database_Connection {

    public Database_Connection(){
        connectJDBC();
    }

    private void connectJDBC() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); //Load the Oracle JDBC driver
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load the driver");
        }
    }

    public Connection validateUserConnection() {

        try {
            String userName ="testx";
            String password ="t123456";
            String url = "jdbc:oracle:thin:@fedora2.uscupstate.edu:1521:xe";
            Connection conn = DriverManager.getConnection(url, userName, password);//connect oracle
            return conn;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;


    }


    public String readEntry(String prompt) {
        try {
            StringBuffer buffer = new StringBuffer();
            System.out.print(prompt);
            System.out.flush();
            int c = System.in.read();
            while (c != '\n' && c != -1) {
                buffer.append((char) c);
                c = System.in.read();
            }
            return buffer.toString().trim();
        } catch (IOException e) {
            return "";
        }
    }
}
