package com.lennertsoffers.persistence.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SqlFileExecutor implements FileExecutor {
    @Override
    public boolean executeFile(String path, Connection connection) {
        try {
            URL resource = this.getClass().getClassLoader().getResource(path);
            if (resource == null) return false;

            File file = new File(resource.toURI());
            Scanner scanner = new Scanner(file).useDelimiter(";");

            Statement statement = connection.createStatement();

            while (scanner.hasNext()) {
                String query = scanner.next();
                if (!query.isEmpty() && !query.equals("\n") && !query.isBlank()) statement.execute(query);
            }
        } catch (SQLException | FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
