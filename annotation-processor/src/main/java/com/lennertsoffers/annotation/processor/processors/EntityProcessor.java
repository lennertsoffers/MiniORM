package com.lennertsoffers.annotation.processor.processors;

import com.google.auto.service.AutoService;
import com.lennertsoffers.annotation.processor.annotations.Entity;
import com.lennertsoffers.annotation.processor.annotations.Id;
import com.lennertsoffers.annotation.processor.models.FieldFactory;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import java.util.Set;


@SupportedAnnotationTypes("com.lennertsoffers.annotation.processor.annotations.Entity")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class EntityProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(Entity.class);

        FieldFactory fieldFactory = new FieldFactory();

        annotatedElements.forEach(element -> {
            com.lennertsoffers.annotation.processor.models.Entity entity = new com.lennertsoffers.annotation.processor.models.Entity();
            String tableName = element.getAnnotation(Entity.class).value();
            if (tableName.equals("")) tableName = element.getSimpleName().toString();
            entity.setClassName(element.getSimpleName().toString());
            entity.setTableName(tableName);

            element.getEnclosedElements().forEach(enclosedElement -> {
                boolean pk = enclosedElement.getAnnotation(Id.class) != null;

                if (enclosedElement.getKind().equals(ElementKind.FIELD)) {
                    if (enclosedElement.asType().getKind().equals(TypeKind.DECLARED)) {
                        DeclaredType declaredType = (DeclaredType) enclosedElement.asType();
                        if (declaredType.asElement().getSimpleName().toString().equals("String")) {
                            entity.addField(fieldFactory.createField(enclosedElement.getSimpleName().toString(), enclosedElement.getSimpleName().toString(), "String", pk));
                        }
                    } else {
                        entity.addField(fieldFactory.createField(enclosedElement.getSimpleName().toString(), enclosedElement.getSimpleName().toString(), enclosedElement.asType().getKind(), pk));
                    }
                }
            });

            System.out.println(entity);
        });

        return true;
    }
}
