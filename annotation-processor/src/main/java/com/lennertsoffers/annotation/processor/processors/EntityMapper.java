package com.lennertsoffers.annotation.processor.processors;

import com.lennertsoffers.annotation.processor.annotations.Column;
import com.lennertsoffers.annotation.processor.annotations.Entity;
import com.lennertsoffers.annotation.processor.annotations.Id;
import com.lennertsoffers.annotation.processor.models.EntityTable;
import com.lennertsoffers.annotation.processor.models.FieldFactory;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import java.util.HashSet;
import java.util.Set;

public class EntityMapper {
    private final Set<? extends Element> annotatedElements;
    private final FieldFactory fieldFactory = new FieldFactory();

    public EntityMapper(Set<? extends Element> annotatedElements) {
        this.annotatedElements = annotatedElements;
    }

    public Set<EntityTable> mapEntities() {
        Set<EntityTable> entityTableSet = new HashSet<>();

        this.annotatedElements.forEach(element -> {
            // Create entity and set name values
            EntityTable entity = this.createEntity(element);

            // Set fields of the entity
            this.setFields(entity, element);

            entityTableSet.add(entity);
        });

        return entityTableSet;
    }

    /**
     * Creates and sets the name fields of the entity
     * -> TableName: Name of the element or the name specified in the @Entity annotation
     * -> ClassName: SimpleName of the element
     * @param element the current element
     * @return the new EntityTable instance
     */
    private EntityTable createEntity(Element element) {
        // Create new EntityTable instance
        EntityTable entity = new EntityTable();

        // Read the value of the @Entity annotation
        String tableName = element.getAnnotation(Entity.class).value();
        // Sets the tableName of the entity to the value of the annotation or the className if no name is specified
        if (tableName.equals("")) tableName = element.getSimpleName().toString();

        // Set the effective values in the EntityTable object
        entity.setClassName(element.getSimpleName().toString());
        entity.setTableName(tableName);

        return entity;
    }

    /**
     * Sets the fields for the entity based on the fields of the classElement
     * @param entity EntityTable instance of which the fields must be set
     * @param classElement Element that represents the encapsulating class of the fields
     */
    private void setFields(EntityTable entity, Element classElement) {
        classElement.getEnclosedElements().forEach(field -> {
            if (field.getKind().equals(ElementKind.FIELD)) {
                // True if the field should be the primary key of the class
                boolean primaryKey = field.getAnnotation(Id.class) != null;

                // Get the name of the column
                // Same as the simpleName of the field if no @Column annotation is specified
                Column columnAnnotation = field.getAnnotation(Column.class);
                String columName = (columnAnnotation != null) ? columnAnnotation.value() : field.getSimpleName().toString();

                // The field is a class
                // Create the field with type string
                if (field.asType().getKind().equals(TypeKind.DECLARED)) {
                    DeclaredType declaredType = (DeclaredType) field.asType();
                    if (declaredType.asElement().getSimpleName().toString().equals("String")) {
                        entity.addField(fieldFactory.createField(
                                field.getSimpleName().toString(),
                                columName,
                                "String",
                                primaryKey
                        ));
                    }
                }

                // The field is primitive
                // The kind can be used to create the field
                else {
                    entity.addField(fieldFactory.createField(
                            field.getSimpleName().toString(),
                            columName,
                            field.asType().getKind(),
                            primaryKey
                    ));
                }
            }
        });
    }
}
