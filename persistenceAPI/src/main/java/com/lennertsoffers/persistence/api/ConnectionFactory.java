package com.lennertsoffers.persistence.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

public class ConnectionFactory {
    private String CONNECTION_URL;
    private String USER;
    private String PASSWORD;
    private int POOL_SIZE;
    private boolean connected = false;

    private final Queue<Connection> connectionQueue = new LinkedList<>();

    private static ConnectionFactory connectionFactory;

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }

        return connectionFactory;
    }

    public Connection getConnection() {
        return connectionQueue.poll();
    }

    public void closeConnection(Connection connection) {
        this.connectionQueue.add(connection);
    }

    public boolean isConnected() {
        return this.connected;
    }

    private ConnectionFactory() {
        this.createConnectionPool();
    }

    private void createConnectionPool() {
        boolean loadedProperties = this.loadProperties();
        boolean createdConnections = this.createConnections();

        this.connected = loadedProperties && createdConnections;
    }

    private boolean loadProperties() {
        Properties properties = new Properties();

        try {
            URL resource = this.getClass().getClassLoader().getResource("miniOrmConfig.properties");
            if (resource == null) return false;

            File file = new File(resource.toURI());

            FileInputStream fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);

            this.CONNECTION_URL = properties.getProperty("connection_url");
            this.USER = properties.getProperty("user");
            this.PASSWORD = properties.getProperty("password");
            this.POOL_SIZE = properties.getProperty("pool_size") == null ? 10 : Integer.parseInt(properties.getProperty("pool_size"));

            return true;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean createConnections() {
        try {
            for (int i = 0; i < this.POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(this.CONNECTION_URL, this.USER, this.PASSWORD);
                this.connectionQueue.add(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
