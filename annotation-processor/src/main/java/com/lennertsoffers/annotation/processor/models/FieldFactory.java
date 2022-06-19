package com.lennertsoffers.annotation.processor.models;

import com.lennertsoffers.annotation.processor.exception.UnsupportedTypeException;

import javax.lang.model.type.TypeKind;
import java.util.List;

public class FieldFactory {
    private final static List<TypeKind> allowedKinds = List.of(
            TypeKind.DOUBLE, TypeKind.INT, TypeKind.LONG, TypeKind.FLOAT, TypeKind.CHAR, TypeKind.BOOLEAN
    );

    public Field createField(String fieldName, String columnName, TypeKind typeKind, boolean pk) {
        if (!allowedKinds.contains(typeKind)) throw new UnsupportedTypeException("The type " + typeKind + " is not allowed as a datatype");
        return new Field(fieldName, columnName, typeKind.toString(), pk);
    }

    public Field createField(String fieldName, String columnName, String type, boolean pk) {
        return new Field(fieldName, columnName, "VARCHAR(255)", pk);
    }
}
