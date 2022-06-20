package com.lennertsoffers.annotation.processor.models.generators;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Properties;

public record ResourceGenerator(String templateName, String outputFileName, ProcessingEnvironment processingEnv, VelocityContext velocityContext) implements Generator {
    public boolean generateFile() {
        try {
            Properties properties = new Properties();
            URL url = this.getClass().getClassLoader().getResource("velocity.properties");

            if (url == null) throw new FileNotFoundException("velocity.properties was not found in resources folder");
            properties.load(url.openStream());

            VelocityEngine velocityEngine = new VelocityEngine(properties);
            velocityEngine.init();

            Template template = velocityEngine.getTemplate(this.templateName);

            FileObject fileObject = this.processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", this.outputFileName);
            Writer writer = fileObject.openWriter();
            template.merge(this.velocityContext, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
