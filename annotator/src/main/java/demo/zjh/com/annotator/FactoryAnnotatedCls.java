package demo.zjh.com.annotator;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * 编写注解解析器
 * i. 首先，定义一个注解属性类，用于保存获取到的每个生产线类相关的属性
 */
public class FactoryAnnotatedCls {
    private TypeElement mAnnotatedClsElement; //被注解类元素

    private String mSupperClsQualifiedName; //被注解的类的父类的完全限定名称（即类的绝对路径）


    private String mSupperClsSimpleName; //被注解类的父类类名

    private int[] mIds; //被注解的类的对应的ID数组


    public FactoryAnnotatedCls(TypeElement classElement) throws ProcessingException {
        this.mAnnotatedClsElement = classElement;
        Factory annotation = classElement.getAnnotation(Factory.class);
        mIds = annotation.ids();
        try {
            //直接获取Factory中的supperClass参数的类名和完全限定名字，如果是源码上的注解，会抛异常
            mSupperClsSimpleName = annotation.superClass().getSimpleName();
            mSupperClsQualifiedName = annotation.superClass().getCanonicalName();
        } catch (MirroredTypeException mte) {
            //如果获取异常，通过mte可以获取到上面无法解析的superClass元素
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            mSupperClsQualifiedName = classTypeElement.getQualifiedName().toString();
            mSupperClsSimpleName = classTypeElement.getSimpleName().toString();
        }

        if (mIds == null || mIds.length == 0) { //判断是否存在ID，不存在则抛出异常
            throw new ProcessingException(classElement,
                    "id() in @%s for class %s is null or empty! that's not allowed",
                    Factory.class.getSimpleName(), classElement.getQualifiedName().toString());
        }

        if (mSupperClsSimpleName == null || mSupperClsSimpleName == "") { //判断是否存在父类接口，不存在抛出异常
            throw new ProcessingException(classElement,
                    "superClass() in @%s for class %s is null or empty! that's not allowed",
                    Factory.class.getSimpleName(), classElement.getQualifiedName().toString());
        }
    }

    public int[] getIds() {
        return mIds;
    }

    public String getSupperClsQualifiedName() {
        return mSupperClsQualifiedName;
    }

    public String getSupperClsSimpleName() {
        return mSupperClsSimpleName;
    }

    public TypeElement getAnnotatedClsElement() {
        return mAnnotatedClsElement;
    }

}
