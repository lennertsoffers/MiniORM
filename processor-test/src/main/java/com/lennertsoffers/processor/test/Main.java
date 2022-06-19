package com.lennertsoffers.processor.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useSSL=false";
    private static final String USER = "admin";
    private static final String PASSWORD = "sql";

    public static void main(String[] args) throws SQLException, IOException, URISyntaxException {
        URL resource = Main.class.getClassLoader().getResource("createTablesScript.sql");
        File file = new File(resource.toURI());
        Scanner scanner = new Scanner(file).useDelimiter(";");

        Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
        Statement statement = connection.createStatement();

        while (scanner.hasNext()) {
            String query = scanner.next();
            System.out.println(query);
            if (!query.isEmpty() && !query.equals("\n")) statement.execute(query);
            System.out.println("-----------------------");
        }
    }
}
