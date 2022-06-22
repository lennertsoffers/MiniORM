package com.lennertsoffers.processor.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Optional;

import com.lennertsoffers.persistence.api.ConnectionFactory;
import com.lennertsoffers.persistence.api.repositories.Repository;


public class TestClassTwoBaseRepository implements Repository<Integer, TestClassTwo> {
    public void save(TestClassTwo testClassTwo) {
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection connection = connectionFactory.getConnection();

        String columnNames = "";
        columnNames += "test_class_two_id, ";
        columnNames += "class_name, ";
        columnNames += "base_float, ";
        columnNames += "base_double, ";
        columnNames += "base_long, ";
        columnNames += "base_int, ";
        columnNames += "base_boolean, ";
        if (testClassTwo.getWrapperFloat() != null) {
            columnNames += "wrapper_float, ";
        }
        if (testClassTwo.getWrapperDouble() != null) {
            columnNames += "wrapper_double, ";
        }
        if (testClassTwo.getWrapperLong() != null) {
            columnNames += "wrapper_long, ";
        }
        if (testClassTwo.getWrapperInt() != null) {
            columnNames += "wrapper_int, ";
        }
        if (testClassTwo.isWrapperBoolean() != null) {
            columnNames += "wrapper_boolean, ";
        }

        columnNames = columnNames.substring(0, columnNames.length() - 2);

        int paramCount = 0;
        paramCount++;
        paramCount++;
        paramCount++;
        paramCount++;
        paramCount++;
        paramCount++;
        paramCount++;
        if (testClassTwo.getWrapperFloat() != null) {
            paramCount++;
        }
        if (testClassTwo.getWrapperDouble() != null) {
            paramCount++;
        }
        if (testClassTwo.getWrapperLong() != null) {
            paramCount++;
        }
        if (testClassTwo.getWrapperInt() != null) {
            paramCount++;
        }
        if (testClassTwo.isWrapperBoolean() != null) {
            paramCount++;
        }
        String paramString = "?, ".repeat(paramCount);
        paramString = paramString.substring(0, paramString.length() - 2);

        String insertSql = "INSERT INTO test_class_two(" +
            columnNames +
        ") VALUES (" +
            paramString +
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
            if (testClassTwo.getWrapperFloat() != null) {
                preparedStatement.setFloat(8, testClassTwo.getWrapperFloat());
            }
            if (testClassTwo.getWrapperDouble() != null) {
                preparedStatement.setDouble(9, testClassTwo.getWrapperDouble());
            }
            if (testClassTwo.getWrapperLong() != null) {
                preparedStatement.setLong(10, testClassTwo.getWrapperLong());
            }
            if (testClassTwo.getWrapperInt() != null) {
                preparedStatement.setInt(11, testClassTwo.getWrapperInt());
            }
            if (testClassTwo.isWrapperBoolean() != null) {
                preparedStatement.setBoolean(12, testClassTwo.isWrapperBoolean());
            }
        
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectionFactory.closeConnection(connection);
    }

    public Optional<TestClassTwo> updateById(TestClassTwo testClassTwo, Integer id) {
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection connection = connectionFactory.getConnection();

        int setCount = 1;
        String columnNames = "";
        columnNames += "class_name = ?, ";
        setCount++;
        columnNames += "base_float = ?, ";
        setCount++;
        columnNames += "base_double = ?, ";
        setCount++;
        columnNames += "base_long = ?, ";
        setCount++;
        columnNames += "base_int = ?, ";
        setCount++;
        columnNames += "base_boolean = ?, ";
        setCount++;
        if (testClassTwo.getWrapperFloat() != null) {
            columnNames += "wrapper_float = ?, ";
            setCount++;
        }
        if (testClassTwo.getWrapperDouble() != null) {
            columnNames += "wrapper_double = ?, ";
            setCount++;
        }
        if (testClassTwo.getWrapperLong() != null) {
            columnNames += "wrapper_long = ?, ";
            setCount++;
        }
        if (testClassTwo.getWrapperInt() != null) {
            columnNames += "wrapper_int = ?, ";
            setCount++;
        }
        if (testClassTwo.isWrapperBoolean() != null) {
            columnNames += "wrapper_boolean = ?, ";
            setCount++;
        }
    
        columnNames = columnNames.substring(0, columnNames.length() - 2) + " ";

        String updateSql = "UPDATE test_class_two SET " +
            columnNames +
            "WHERE test_class_two_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);

            preparedStatement.setString(1, testClassTwo.getClassName());
            preparedStatement.setFloat(2, testClassTwo.getBaseFloat());
            preparedStatement.setDouble(3, testClassTwo.getBaseDouble());
            preparedStatement.setLong(4, testClassTwo.getBaseLong());
            preparedStatement.setInt(5, testClassTwo.getBaseInt());
            preparedStatement.setBoolean(6, testClassTwo.isBaseBoolean());
            if (testClassTwo.getWrapperFloat() != null) {
                preparedStatement.setFloat(7, testClassTwo.getWrapperFloat());
            }
            if (testClassTwo.getWrapperDouble() != null) {
                preparedStatement.setDouble(8, testClassTwo.getWrapperDouble());
            }
            if (testClassTwo.getWrapperLong() != null) {
                preparedStatement.setLong(9, testClassTwo.getWrapperLong());
            }
            if (testClassTwo.getWrapperInt() != null) {
                preparedStatement.setInt(10, testClassTwo.getWrapperInt());
            }
            if (testClassTwo.isWrapperBoolean() != null) {
                preparedStatement.setBoolean(11, testClassTwo.isWrapperBoolean());
            }
            preparedStatement.setInt(setCount, id);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectionFactory.closeConnection(connection);

        return this.findById(id);
    }

    public Optional<TestClassTwo> findById(Integer id) {
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
                testClassTwo.setWrapperFloat(resultSet.getFloat("wrapper_float"));
                if (resultSet.wasNull()) {
                    testClassTwo.setWrapperFloat(null);
                }
                testClassTwo.setWrapperDouble(resultSet.getDouble("wrapper_double"));
                if (resultSet.wasNull()) {
                    testClassTwo.setWrapperDouble(null);
                }
                testClassTwo.setWrapperLong(resultSet.getLong("wrapper_long"));
                if (resultSet.wasNull()) {
                    testClassTwo.setWrapperLong(null);
                }
                testClassTwo.setWrapperInt(resultSet.getInt("wrapper_int"));
                if (resultSet.wasNull()) {
                    testClassTwo.setWrapperInt(null);
                }
                testClassTwo.setWrapperBoolean(resultSet.getBoolean("wrapper_boolean"));
                if (resultSet.wasNull()) {
                    testClassTwo.setWrapperBoolean(null);
                }
        
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
