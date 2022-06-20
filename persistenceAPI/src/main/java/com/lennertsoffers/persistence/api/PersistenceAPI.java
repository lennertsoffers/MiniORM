package com.lennertsoffers.persistence.api;

public class PersistenceAPI {
    public static void initialize() {
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        TableLoader tableLoader = new TableLoader(connectionFactory);

        boolean tablesCreated = tableLoader.createTables();
        System.out.println("Tables created: " + tablesCreated);
    }
}
