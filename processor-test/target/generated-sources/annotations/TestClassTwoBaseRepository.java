package com.lennertsoffers.processor.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Optional;

import com.lennertsoffers.persistence.api.ConnectionFactory;
import com.lennertsoffers.persistence.api.repositories.Repository;

public class TestClassTwoBaseRepository implements Repository<TestClassTwo> {
    public void save(TestClassTwo testClassTwo) {
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection connection = connectionFactory.getConnection();

        String insertSql = "INSERT INTO test_class_two(" +
                    "test_class_two_id," +
                    "class_name," +
                    "base_float," +
                    "base_double," +
                    "base_long," +
                    "base_int," +
                    "base_boolean" +
                ") VALUES (" +
                    "?, ?, ?, ?, ?, ?, ?" +
                ")";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
                    preparedStatement.setInt(1, testClassTwo.getId());
                    preparedStatement.setString(2, testClassTwo.getClassName());
                    preparedStatement.setFloat(3, testClassTwo.getBaseFloat());
                    preparedStatement.setDouble(4, testClassTwo.getBaseDouble());
                    preparedStatement.setLong(5, testClassTwo.getBaseLong());
                    preparedStatement.setInt(6, testClassTwo.getBaseInt());
                    preparedStatement.setBoolean(7, testClassTwo.isBaseBoolean());
                
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectionFactory.closeConnection(connection);
    }

    public Optional<TestClassTwo> findById(int id) {
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection connection = connectionFactory.getConnection();

        String querySql = "SELECT * FROM test_class_two WHERE test_class_two_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(querySql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                TestClassTwo testClassTwo = new TestClassTwo();

                testClassTwo.setId(resultSet.getInt("test_class_two_id"));
                testClassTwo.setClassName(resultSet.getString("class_name"));
                testClassTwo.setBaseFloat(resultSet.getFloat("base_float"));
                testClassTwo.setBaseDouble(resultSet.getDouble("base_double"));
                testClassTwo.setBaseLong(resultSet.getLong("base_long"));
                testClassTwo.setBaseInt(resultSet.getInt("base_int"));
                testClassTwo.setBaseBoolean(resultSet.getBoolean("base_boolean"));
        
                connectionFactory.closeConnection(connection);

                return Optional.of(testClassTwo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectionFactory.closeConnection(connection);
        return Optional.empty();
    }
}
