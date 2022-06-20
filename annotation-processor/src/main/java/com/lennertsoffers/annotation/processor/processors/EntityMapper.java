package com.lennertsoffers.annotation.processor.processors;

import com.lennertsoffers.annotation.processor.annotations.Column;
import com.lennertsoffers.annotation.processor.annotations.Entity;
import com.lennertsoffers.annotation.processor.annotations.Id;
import com.lennertsoffers.annotation.processor.models.EntityTable;
import com.lennertsoffers.annotation.processor.models.FieldFactory;
import com.lennertsoffers.annotation.processor.models.generators.ResourceGenerator;
import com.lennertsoffers.annotation.processor.models.generators.SourceFileGenerator;
import com.lennertsoffers.annotation.processor.utils.StringConverter;
import org.apache.velocity.VelocityContext;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import java.util.HashSet;
import java.util.Set;

public class EntityMapper {
    private final Set<? extends Element> annotatedElements;
    private final ProcessingEnvironment processingEnvironment;
    private final FieldFactory fieldFactory = new FieldFactory();
    private final Set<EntityTable> mappedEntities = new HashSet<>();

    public EntityMapper(Set<? extends Element> annotatedElements, ProcessingEnvironment processingEnvironment) {
        this.annotatedElements = annotatedElements;
        this.processingEnvironment = processingEnvironment;
    }

    public void mapEntities() {
        this.annotatedElements.forEach(element -> {
            // Create entity and set name values
            EntityTable entity = this.createEntity(element);

            // Set fields of the entity
            this.setFields(entity, element);

            // Add the entity to the set
            this.mappedEntities.add(entity);
        });

        // Generate files corresponding to the mapped entities
        this.generateFiles();
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
        if (tableName.equals("")) tableName = StringConverter.toSnakeCase(element.getSimpleName().toString());

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
                String columnName;
                if (columnAnnotation != null) {
                    columnName = columnAnnotation.value();
                } else {
                    // If the field is a primary key, the field is set to the table name plus _id
                    if (primaryKey) {
                        columnName = entity.getTableName() + "_id";
                    } else {
                        columnName = StringConverter.toSnakeCase(field.getSimpleName().toString());
                    }
                }

                // The field is a class
                // Create the field with type string
                if (field.asType().getKind().equals(TypeKind.DECLARED)) {
                    DeclaredType declaredType = (DeclaredType) field.asType();
                    if (declaredType.asElement().getSimpleName().toString().equals("String")) {
                        entity.addField(fieldFactory.createField(
                                field.getSimpleName().toString(),
                                columnName,
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
                            columnName,
                            field.asType().getKind(),
                            primaryKey
                    ));
                }
            }
        });
    }

    private void generateFiles() {
        boolean generatedTableCreationScript = this.generateTableCreationScript();
        boolean generatedBaseRepositories = this.generateBaseRepositories();

        System.out.println("Generated table creation script: " + generatedTableCreationScript);
        System.out.println("Generated base repositories: " + generatedBaseRepositories);
    }

    /**
     * Generates the sql script for the creation of the correct tables and columns based on the mapped entities
     * @return if the generation of the script was successful
     */
    private boolean generateTableCreationScript() {
        // Create a velocityContext with the mappedEntities
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("entities", this.mappedEntities);

        // Use a ResourceGenerator to generate the resource
        return new ResourceGenerator(
                "createTablesScript.vm",
                "createTablesScript.sql",
                this.processingEnvironment,
                velocityContext
        ).generateFile();
    }

    /**
     * Generates the base repository java classes to save your entities
     * @return if the generation of the repositories was successful
     */
    private boolean generateBaseRepositories() {
        for (EntityTable entity : this.mappedEntities) {
            // Compose the class and file name
            String fileName = entity.getClassName() + "BaseRepository";

            // Create velocityContext with the mappedEntities
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("entity", entity);
            velocityContext.put("className", fileName);
            velocityContext.put("lowercaseName", Character.toLowerCase(entity.getClassName().charAt(0)) + entity.getClassName().substring(1));

            // Use a SourceFileGenerator to generate the repositories
            boolean sourceFileGenerated = new SourceFileGenerator(
                    "baseRepository.vm",
                    fileName,
                    this.processingEnvironment,
                    velocityContext
            ).generateFile();

            // Return false if the generation of a repository fails
            if (!sourceFileGenerated) return false;
        }

        return true;
    }
}
