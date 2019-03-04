package demo.zjh.com.annotator;

import com.sun.tools.javac.util.Log;

@Factory(ids = {1},superClass = IFruit.class)
public class Apple implements IFruit {

    @Override
    public void produce() {
        System.out.println("AnnotationDemo 生产苹果");
    }
}
