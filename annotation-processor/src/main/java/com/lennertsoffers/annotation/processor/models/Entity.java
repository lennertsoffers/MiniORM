package com.lennertsoffers.annotation.processor.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.lang.model.type.TypeKind;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Entity {
    private String tableName;
    private String className;
    private List<Field> fields = new ArrayList<>();

    public void addField(Field field) {
        this.fields.add(field);
    }

    @Override
    public String toString() {
        return this.tableName + " <===> " + this.className + "\n" +
                this.fields.stream().map(Field::toString).collect(Collectors.joining("\n"));
    }
}
