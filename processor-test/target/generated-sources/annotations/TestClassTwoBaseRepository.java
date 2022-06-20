package com.lennertsoffers.processor.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.lennertsoffers.persistence.api.ConnectionFactory;
import com.lennertsoffers.persistence.api.repositories.Repository;

public class TestClassTwoBaseRepository implements Repository<TestClassTwo> {
    public void save(TestClassTwo testClassTwoBaseRepository) {
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection connection = connectionFactory.getConnection();

        String insertSql = "INSERT INTO TestClassTwo(" +
                    "id," +
                    "className," +
                    "baseFloat," +
                    "baseDouble," +
                    "baseLong," +
                    "baseInt," +
                    "baseBoolean" +
                ") VALUES (" +
                    "?, ?, ?, ?, ?, ?, ?" +
                ")";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
                    preparedStatement.setInt(1, testClassTwoBaseRepository.getId());
                    preparedStatement.setString(2, testClassTwoBaseRepository.getClassName());
                    preparedStatement.setFloat(3, testClassTwoBaseRepository.getBaseFloat());
                    preparedStatement.setDouble(4, testClassTwoBaseRepository.getBaseDouble());
                    preparedStatement.setLong(5, testClassTwoBaseRepository.getBaseLong());
                    preparedStatement.setInt(6, testClassTwoBaseRepository.getBaseInt());
                    preparedStatement.setBoolean(7, testClassTwoBaseRepository.isBaseBoolean());
                
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectionFactory.closeConnection(connection);
    }
}
