package com.lennertsoffers.processor.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Optional;

import com.lennertsoffers.persistence.api.ConnectionFactory;
import com.lennertsoffers.persistence.api.repositories.Repository;


public class TestClassBaseRepository implements Repository<Integer, TestClass> {
    public void save(TestClass testClass) {
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection connection = connectionFactory.getConnection();

        String columnNames = "";
        columnNames += "little_class_id, ";
        columnNames += "name, ";

        columnNames = columnNames.substring(0, columnNames.length() - 2);

        int paramCount = 0;
        paramCount++;
        paramCount++;
        String paramString = "?, ".repeat(paramCount);
        paramString = paramString.substring(0, paramString.length() - 2);

        String insertSql = "INSERT INTO little_class(" +
            columnNames +
        ") VALUES (" +
            paramString +
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

    public Optional<TestClass> updateById(TestClass testClass, Integer id) {
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection connection = connectionFactory.getConnection();

        int setCount = 1;
        String columnNames = "";
        columnNames += "name = ?, ";
        setCount++;
    
        columnNames = columnNames.substring(0, columnNames.length() - 2) + " ";

        String updateSql = "UPDATE little_class SET " +
            columnNames +
            "WHERE little_class_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);

            preparedStatement.setString(1, testClass.getName());
            preparedStatement.setInt(setCount, id);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectionFactory.closeConnection(connection);

        return this.findById(id);
    }

    public Optional<TestClass> findById(Integer id) {
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

    public boolean delete(Integer id) {
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection connection = connectionFactory.getConnection();

        String deleteSql = "DELETE FROM little_class WHERE little_class_id = ?";
        boolean deleteSucceeded = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setInt(1, id);

            deleteSucceeded = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectionFactory.closeConnection(connection);
        return deleteSucceeded;
    }
}
