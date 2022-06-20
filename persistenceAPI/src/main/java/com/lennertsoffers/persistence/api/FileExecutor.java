package com.lennertsoffers.persistence.api;

import java.sql.Connection;

public interface FileExecutor {
    boolean executeFile(String path, Connection connection);
}
