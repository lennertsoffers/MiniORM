package com.lennertsoffers.processor.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Optional;

import com.lennertsoffers.persistence.api.ConnectionFactory;
import com.lennertsoffers.persistence.api.repositories.Repository;

public class TestClassBaseRepository implements Repository<TestClass> {
    public void save(TestClass testClass) {
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection connection = connectionFactory.getConnection();

        String insertSql = "INSERT INTO little_class(" +
                    "little_class_id," +
                    "name" +
                ") VALUES (" +
                    "?, ?" +
                ")";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
                    preparedStatement.setInt(1, testClass.getId());
                    preparedStatement.setString(2, testClass.getName());
                
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectionFactory.closeConnection(connection);
    }

    public Optional<TestClass> findById(int id) {
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection connection = connectionFactory.getConnection();

        String querySql = "SELECT * FROM little_class WHERE little_class_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(querySql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                TestClass testClass = new TestClass();

                testClass.setId(resultSet.getInt("little_class_id"));
                testClass.setName(resultSet.getString("name"));
        
                connectionFactory.closeConnection(connection);

                return Optional.of(testClass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectionFactory.closeConnection(connection);
        return Optional.empty();
    }
}
