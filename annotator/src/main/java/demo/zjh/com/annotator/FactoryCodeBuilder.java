package demo.zjh.com.annotator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class FactoryCodeBuilder {
    private static final String SUFFIX = "Factory";

    private String mSupperClsName;

    private Map<String, FactoryAnnotatedCls> mAnnotatedClasses = new LinkedHashMap<>();

    public void add(FactoryAnnotatedCls annotatedCls) {
        if (mAnnotatedClasses.get(annotatedCls.getAnnotatedClsElement().getQualifiedName().toString()) != null)
            return ;

        mAnnotatedClasses.put(
                annotatedCls.getAnnotatedClsElement().getQualifiedName().toString(),
                annotatedCls);
    }

    public void clear() {
        mAnnotatedClasses.clear();
    }


    public FactoryCodeBuilder setSupperClsName(String supperClsName) {
        mSupperClsName = supperClsName; //设置上产线接口父类的路径
        return this;
    }

    public void generateCode(Messager messager, Elements elementUtils, Filer filer) throws IOException {
        TypeElement superClassName = elementUtils.getTypeElement(mSupperClsName); //通过Elements工具获取父类元素
        String factoryClassName = superClassName.getSimpleName() + SUFFIX; //然后设置即将生成的工厂类的名字（在这里为IFruitFactory）
        PackageElement pkg = elementUtils.getPackageOf(superClassName); //通过Elements工具，获取父类所在包名路径（在这里为annotation.demo.factorys）
        String packageName = pkg.isUnnamed() ? null : pkg.getQualifiedName().toString(); //获取即将生成的工厂类的包名

        TypeSpec typeSpec = TypeSpec
                .classBuilder(factoryClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(newCreateMethod(elementUtils, superClassName))
                .addMethod(newCompareIdMethod())
                .build();

        // Write file
        JavaFile.builder(packageName, typeSpec).build().writeTo(filer);
    }

}
