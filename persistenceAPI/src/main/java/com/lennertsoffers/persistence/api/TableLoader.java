package com.lennertsoffers.persistence.api;

import java.sql.Connection;

public record TableLoader(ConnectionFactory connectionFactory) {
    public boolean createTables() {
        if (!this.connectionFactory.isConnected()) return false;

        Connection connection = this.connectionFactory.getConnection();
        boolean tablesCreated = new SqlFileExecutor().executeFile("createTablesScript.sql", connection);
        this.connectionFactory.closeConnection(connection);

        return tablesCreated;
    }
}
