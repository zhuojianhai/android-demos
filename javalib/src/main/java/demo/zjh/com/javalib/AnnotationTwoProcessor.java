package demo.zjh.com.javalib;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("demo.zjh.com.javalib.BindView")
public class AnnotationTwoProcessor extends AbstractProcessor {
    //用于打印日志
    private Messager    messager;

    //用于处理元素
    private Elements    elementUtils;

    //用来创建java文件或者class文件
    private Filer filer;

    //该方法再编译期间会被注入一个ProcessingEnvironment对象，该对象包含了很多有用的工具类。
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        elementUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
    }


    /**
     * 该方法将一轮一轮的遍历源代码
     * @param set  该方法需要处理的注解类型
     * @param roundEnvironment  关于一轮遍历中提供给我们调用的信息.
     * @return  该轮注解是否处理完成 true 下轮或者其他的注解处理器将不会接收到次类型的注解.用处不大.
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for(Element element: roundEnvironment.getElementsAnnotatedWith(BindView.class)){
            if(element.getKind() == ElementKind.FIELD){
                    messager.printMessage(Diagnostic.Kind.NOTE,"printMessage:"+element.toString());
            }

        }
        return true;
    }

    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }


    /**
     * 返回我们将要处理的注解
     *
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(BindView.class.getCanonicalName());
        return super.getSupportedAnnotationTypes();
    }

    /**
     * 返回我们Java的版本.
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }


}
