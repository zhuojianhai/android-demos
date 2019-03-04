package demo.zjh.com.annotator;

import com.google.auto.service.AutoService;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@AutoService(Processor.class)
public class FactoryProcesser extends AbstractProcessor {
    private Types mTypeUtil;
    private Elements mElementUtil;
    private Filer mFiler;
    private Messager mMessager;

    private FactoryCodeBuilder mFactoryCodeBuilder = new FactoryCodeBuilder();

    /**
     * 获取了几个即将用到的工具：
     * @param processingEnvironment
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mTypeUtil = processingEnvironment.getTypeUtils();
        mElementUtil = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(Factory.class)) { //遍历所有被Factory注解的元素
            if (annotatedElement.getKind() != ElementKind.CLASS) { //判断是否为类，如果不是class，抛出异常
                error(annotatedElement,
                        String.format("Only class can be annotated with @%s",
                                Factory.class.getSimpleName()));
            }

            TypeElement typeElement = (TypeElement) annotatedElement; //将元素转换为TypeElement（因为在上面的代码中，已经判断了元素为class类型）
            FactoryAnnotatedCls annotatedCls = new FactoryAnnotatedCls(typeElement); //接着将该元素保存到先前定义的类中
            supperClsPath = annotatedCls.getSupperClsQualifiedName().toString(); //获取元素的父类路径（在这里为IFruit）

            checkValidClass(annotatedCls);//检查元素是否符合规则

            mFactoryCodeBuilder.add(annotatedCls); //将元素压入列表中，等待最后用于生成工厂代码
        }

        if (supperClsPath != null && !supperClsPath.equals("")) { //检查是否有父类路径
            mFactoryCodeBuilder
                    .setSupperClsName(supperClsPath)
                    .generateCode(mMessager, mElementUtil, mFiler); //开始生成代码
        }

        return true; //return true表示处理完毕
    }

    /**
     * 配置需要处理的注解，这里只处理@Factory注解；
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Factory.class.getCanonicalName());
        return annotations;
    }

    /**
     * 配置支持的Java版本
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
