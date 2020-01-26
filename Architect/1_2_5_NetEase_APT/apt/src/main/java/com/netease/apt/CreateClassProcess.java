package com.netease.apt;

import com.google.auto.service.AutoService;

import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class CreateClassProcess extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Override.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        try {
            JavaFileObject javaFileObject = filer.createSourceFile("ActivityMvvmLoginBinding");
            Writer writer = javaFileObject.openWriter();

            writer.write("package com.netease.apt;");
            writer.write("\n");
            writer.write("public class ActivityMvvmLoginBinding {\n}");

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
