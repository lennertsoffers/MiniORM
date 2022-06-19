package com.lennertsoffers.annotation.processor.models;

public record Field(String fieldName, String columnName, String sqlName, boolean pk) {
    @Override
    public String toString() {
        return "\t\t" + this.columnName + " <===> " + this.columnName + "\n" +
                "\t\t" + this.sqlName + (pk ? "\tPK" : "");
    }
}
