package com.lennertsoffers.processor.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.lennertsoffers.persistence.api.ConnectionFactory;
import com.lennertsoffers.persistence.api.repositories.Repository;

public class TestClassBaseRepository implements Repository<TestClass> {
    public void save(TestClass testClassBaseRepository) {
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection connection = connectionFactory.getConnection();

        String insertSql = "INSERT INTO little_class(" +
                    "id," +
                    "name" +
                ") VALUES (" +
                    "?, ?" +
                ")";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
                    preparedStatement.setInt(1, testClassBaseRepository.getId());
                    preparedStatement.setString(2, testClassBaseRepository.getName());
                
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectionFactory.closeConnection(connection);
    }
}
