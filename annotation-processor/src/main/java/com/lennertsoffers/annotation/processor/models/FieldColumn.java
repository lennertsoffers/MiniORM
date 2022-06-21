package com.lennertsoffers.annotation.processor.models;

public record FieldColumn(String fieldName,
                          String columnName,
                          String javaType,
                          String wrapperType,
                          String sqlType,
                          boolean pk,
                          boolean wrapper) {}
