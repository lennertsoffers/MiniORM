package com.lennertsoffers.annotation.processor.models;

import com.lennertsoffers.annotation.processor.exception.UnsupportedTypeException;

import javax.lang.model.type.TypeKind;
import java.util.List;

public class FieldFactory {
    private final static List<TypeKind> allowedKinds = List.of(
            TypeKind.DOUBLE, TypeKind.INT, TypeKind.LONG, TypeKind.FLOAT, TypeKind.CHAR, TypeKind.BOOLEAN
    );

    public FieldColumn createField(String fieldName, String columnName, TypeKind typeKind, boolean pk) {
        if (!allowedKinds.contains(typeKind)) throw new UnsupportedTypeException("The type " + typeKind + " is not allowed as a datatype");

        String sqlType;
        if (typeKind.equals(TypeKind.LONG)) {
            sqlType = "BIGINT";
        } else {
            sqlType = typeKind.toString();
        }

        String javaType = typeKind.toString().toLowerCase();
        javaType = Character.toUpperCase(javaType.charAt(0)) + javaType.substring(1);

        return new FieldColumn(fieldName, columnName, javaType, sqlType, pk);
    }

    public FieldColumn createField(String fieldName, String columnName, String type, boolean pk) {
        return new FieldColumn(fieldName, columnName, type, "VARCHAR(255)", pk);
    }
}
