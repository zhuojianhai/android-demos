package demo.zjh.com.annotator;

import sun.rmi.runtime.Log;

@Factory(ids = {2,3},superClass = IFruit.class)
public class Pear implements IFruit {

    @Override
    public void produce() {
        System.out.println("AnnotationDemo 生产梨子");
    }
}
