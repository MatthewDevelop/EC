package cn.fxn.svm.compiler;

import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import cn.fxn.svm.annotations.AppRegisterGenerator;
import cn.fxn.svm.annotations.EntryGenerator;
import cn.fxn.svm.annotations.PayEntryGenerator;

/**
 * @author:Matthew
 * @date:2018/10/22
 * @email:guocheng0816@163.com
 * @func:
 */
@AutoService(Processor.class)
public class EcProcessor extends AbstractProcessor {
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> types = new LinkedHashSet<>();
        final Set<Class<? extends Annotation>> supportAnimations = getSupportAnnotations();
        for (Class<? extends Annotation> supportAnimation : supportAnimations) {
            types.add(supportAnimation.getCanonicalName());
        }
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        generateEntryCode(roundEnvironment);
        generatePayCode(roundEnvironment);
        generateAppRegisterCode(roundEnvironment);
        return true;
    }

    private Set<Class<? extends Annotation>> getSupportAnnotations() {
        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(EntryGenerator.class);
        annotations.add(PayEntryGenerator.class);
        annotations.add(AppRegisterGenerator.class);
        return annotations;
    }

    private void scan(RoundEnvironment environment,
                      Class<? extends Annotation> annotation,
                      AnnotationValueVisitor visitor) {
        for (Element element : environment.getElementsAnnotatedWith(annotation)) {
            final List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
            for (AnnotationMirror annotationMirror : annotationMirrors) {
                final Map<? extends ExecutableElement, ? extends AnnotationValue>
                        elementValues = annotationMirror.getElementValues();
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue>
                        entry : elementValues.entrySet()) {
                    entry.getValue().accept(visitor, null);
                }
            }
        }
    }

    private void generateEntryCode(RoundEnvironment environment){
        final EntryVisitor entryVisitor=new EntryVisitor();
        entryVisitor.setFiler(processingEnv.getFiler());
        scan(environment, EntryGenerator.class, entryVisitor);
    }

    private void generatePayCode(RoundEnvironment environment){
        final PayEntryVisitor entryVisitor=new PayEntryVisitor();
        entryVisitor.setFiler(processingEnv.getFiler());
        scan(environment, PayEntryGenerator.class, entryVisitor);
    }

    private void generateAppRegisterCode(RoundEnvironment environment){
        final AppRegisterVisitor entryVisitor=new AppRegisterVisitor();
        entryVisitor.setFiler(processingEnv.getFiler());
        scan(environment, AppRegisterGenerator.class, entryVisitor);
    }
}
