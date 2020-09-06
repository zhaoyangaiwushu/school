package com.luftraveler.io;

import java.io.*;

public class SerializeDemo01 {
 
    enum Sex {
 
        MALE, FEMALE
 
    }
 
    static class Person implements Serializable {
 
        private static final long serialVersionUID = 1L;
 
        private String name = null;
 
        private Integer age = null;
 
        private Sex sex;
 
        public Person() {
 
            System.out.println("call Person()");
 
        }
 
        public Person(String name, Integer age, Sex sex) {
 
            this.name = name;
 
            this.age = age;
 
            this.sex = sex;
 
        }
 
        public String toString() {
 
            return "name: " + this.name + ", age: " + this.age + ", sex: " + this.sex;
 
        }
 
    }
 
    /**
     * 序列化
     */
 
    private static void serialize(String filename) throws IOException {
 
        File f = new File(filename); // 定义保存路径
 
        OutputStream out = new FileOutputStream(f); // 文件输出流
 
        ObjectOutputStream oos = new ObjectOutputStream(out); // 对象输出流
 
        oos.writeObject(new Person("Jack12121", 30, Sex.MALE)); // 保存对象
 
        oos.close();
 
        out.close();
 
    }
 
    /**
     * 反序列化
     */
 
    private static void deserialize(String filename) throws IOException, ClassNotFoundException {
 
        File f = new File(filename); // 定义保存路径
 
        InputStream in = new FileInputStream(f); // 文件输入流
 
        ObjectInputStream ois = new ObjectInputStream(in); // 对象输入流
 
        Object obj = ois.readObject(); // 读取对象
 
        ois.close();
 
        in.close();
        Person a = (Person) obj;
        System.out.println(a.age);
        System.out.println(obj);
 
    }
 
    public static void main(String[] args) throws IOException, ClassNotFoundException {
 
        final String filename = "io流\\text.dat";
 
        serialize(filename);
 
        deserialize(filename);
 
    }
 
}