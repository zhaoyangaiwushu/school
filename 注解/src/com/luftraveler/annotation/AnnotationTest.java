package com.luftraveler.annotation;

@MyAnnotation
public class AnnotationTest<U> {
    @MyAnnotation
    private String name;

    public static void main(String[] args) {
        AnnotationTest<@MyAnnotation String> t = null;
        int a = (@MyAnnotation int) 2L;
        @MyAnnotation
        int b = 10;
    }

    public static <@MyAnnotation T> void method(T t) {
    }

    public static void test(@MyAnnotation String arg) throws @MyAnnotation Exception {
    }
}
//@Target(ElementType.TYPE_USE)
//@interface MyAnnotation {
//}
