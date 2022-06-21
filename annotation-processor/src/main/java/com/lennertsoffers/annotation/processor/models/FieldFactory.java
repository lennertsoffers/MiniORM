package com.lennertsoffers.annotation.processor.models;

import com.lennertsoffers.annotation.processor.exception.UnsupportedTypeException;

import javax.lang.model.type.TypeKind;
import java.util.List;
import java.util.Map;

public class FieldFactory {
    private final static List<TypeKind> allowedKinds = List.of(
            TypeKind.DOUBLE, TypeKind.INT, TypeKind.LONG, TypeKind.FLOAT, TypeKind.CHAR, TypeKind.BOOLEAN
    );

    public final static Map<String, String> declaredTypeMapping = Map.of(
            "Double", "double",
            "Integer", "int",
            "Long", "long",
            "Float", "float",
            "Character", "char",
            "Boolean", "boolean",
            "String", "String"
    );

    public FieldColumn createField(String fieldName, String columnName, TypeKind typeKind, boolean pk) {
        if (!allowedKinds.contains(typeKind)) throw new UnsupportedTypeException("The type " + typeKind + " is not allowed as a datatype");

        String javaType = typeKind.toString().toLowerCase();
        javaType = Character.toUpperCase(javaType.charAt(0)) + javaType.substring(1);

        String sqlType = null;
        String wrapperType = null;

        if (typeKind.equals(TypeKind.DOUBLE)) {
            sqlType = typeKind.toString();
            wrapperType = "Double";
        } else if (typeKind.equals(TypeKind.INT)) {
            sqlType = typeKind.toString();
            wrapperType = "Integer";
        } else if (typeKind.equals(TypeKind.LONG)) {
            sqlType = "BIGINT";
            wrapperType = "Long";
        } else if (typeKind.equals(TypeKind.FLOAT)) {
            sqlType = typeKind.toString();
            wrapperType = "Float";
        } else if (typeKind.equals(TypeKind.CHAR)) {
            sqlType = typeKind.toString();
            wrapperType = "Character";
        } else if (typeKind.equals(TypeKind.BOOLEAN)) {
            sqlType = typeKind.toString();
            wrapperType = "Boolean";
        }

        return new FieldColumn(fieldName, columnName, javaType, wrapperType, sqlType, pk, false);
    }

    public FieldColumn createField(String fieldName, String columnName, String type, boolean pk) {
        return new FieldColumn(fieldName, columnName, declaredTypeMapping.get(type), type, "VARCHAR(255)", pk, !type.equals("String"));
    }

    public boolean isAllowedDeclaredType(String type) {
        return declaredTypeMapping.containsKey(type);
    }
}
