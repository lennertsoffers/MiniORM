package com.lennertsoffers.annotation.processor.models.generators;

import org.apache.velocity.VelocityContext;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;
import java.io.IOException;

public final class SourceFileGenerator extends Generator {
    public SourceFileGenerator(String templateName, String outputFileName, ProcessingEnvironment processingEnv, VelocityContext velocityContext) {
        super(templateName, outputFileName, processingEnv, velocityContext);
    }

    @Override
    public FileObject getFileObject() throws IOException {
        return this.getProcessingEnv().getFiler().createSourceFile(this.getOutputFileName());
    }
}
