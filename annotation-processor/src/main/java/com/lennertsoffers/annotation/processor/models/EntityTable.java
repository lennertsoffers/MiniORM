package com.lennertsoffers.annotation.processor.models;

import java.util.ArrayList;
import java.util.List;

public class EntityTable {
    private String tableName;
    private String className;
    private List<FieldColumn> fieldColumns = new ArrayList<>();
    private FieldColumn primaryKeyField;

    public EntityTable() {
    }

    public EntityTable(String tableName, String className, List<FieldColumn> fieldColumns) {
        this.tableName = tableName;
        this.className = className;
        this.fieldColumns = fieldColumns;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<FieldColumn> getFieldColumns() {
        return fieldColumns;
    }

    public void setFieldColumns(List<FieldColumn> fieldColumns) {
        this.fieldColumns = fieldColumns;
    }

    public FieldColumn getPrimaryKeyField() {
        return primaryKeyField;
    }

    public void setPrimaryKeyField(FieldColumn primaryKeyField) {
        this.primaryKeyField = primaryKeyField;
    }

    public void addField(FieldColumn fieldColumn) {
        if (fieldColumn.pk()) this.primaryKeyField = fieldColumn;
        this.fieldColumns.add(fieldColumn);
    }
}
