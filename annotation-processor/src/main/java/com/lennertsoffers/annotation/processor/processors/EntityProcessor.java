package com.lennertsoffers.annotation.processor.processors;

import com.google.auto.service.AutoService;
import com.lennertsoffers.annotation.processor.annotations.Entity;
import com.lennertsoffers.annotation.processor.annotations.Id;
import com.lennertsoffers.annotation.processor.models.EntityTable;
import com.lennertsoffers.annotation.processor.models.FieldFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.checkerframework.common.reflection.qual.NewInstance;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


@SupportedAnnotationTypes("com.lennertsoffers.annotation.processor.annotations.Entity")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class EntityProcessor extends AbstractProcessor {
    private Set<EntityTable> entities = new HashSet<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(Entity.class);

        EntityMapper entityMapper = new EntityMapper(annotatedElements);
        this.entities = entityMapper.mapEntities();

        try {
            this.generateScript();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private void generateScript() throws IOException {
        Properties properties = new Properties();
        URL url = this.getClass().getClassLoader().getResource("velocity.properties");

        if (url == null) throw new FileNotFoundException("velocity.properties file not fount in resources folder");
        properties.load(url.openStream());

        VelocityEngine velocityEngine = new VelocityEngine(properties);
        velocityEngine.init();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("entities", this.entities);

        Template template = velocityEngine.getTemplate("createTablesScript.vm");

        FileObject fileObject= processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "createTablesScript.sql");
        Writer writer = fileObject.openWriter();
        template.merge(velocityContext, writer);
        writer.close();
    }
}
