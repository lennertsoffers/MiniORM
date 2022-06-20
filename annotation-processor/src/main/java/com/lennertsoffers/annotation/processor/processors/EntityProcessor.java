package com.lennertsoffers.annotation.processor.processors;

import com.google.auto.service.AutoService;
import com.lennertsoffers.annotation.processor.annotations.Entity;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;


@SupportedAnnotationTypes("com.lennertsoffers.annotation.processor.annotations.Entity")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class EntityProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(Entity.class);

        EntityMapper entityMapper = new EntityMapper(annotatedElements, this.processingEnv);
        entityMapper.mapEntities();

        return true;
    }
}
