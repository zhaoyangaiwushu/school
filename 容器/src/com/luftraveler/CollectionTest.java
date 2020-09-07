package com.luftraveler;

import com.luftraveler.domain.Personss;
import org.junit.Test;

import java.util.*;

public class CollectionTest {
    @Test
    public void test1(){
        /*1.add(Object e):添加元素*/
        Collection coll1 = new ArrayList();
        coll1.add(456);
        coll1.add("CC");
        System.out.println(coll1.size());//2

        /*2.addAll(Collection coll1):添加多个元素*/
        Collection coll = new ArrayList();
        coll.addAll(coll1);

        /*3.int size():获取元素的长度*/
        System.out.println(coll.size());//2

        /*3.clear():清空集合元素*/
        coll.clear();
        System.out.println(coll);

        /*5.isEmpty():判断当前集合是否为空*/
        System.out.println(coll.isEmpty());
    }

    @Test
    public void test2(){
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        Personss p = new Personss("Jerry",20);
        coll.add(p);
        coll.add(new Personss("Jerry",20));
        coll.add(new String("Tom"));
        coll.add(false);

        //1.contains(Object obj):判断当前集合中是否包含obj 我们在判断时会调用obj对象所在类的equals()。
        System.out.println(coll.contains(123));
        System.out.println(coll.contains(new String("Tom")));
        System.out.println(coll.contains(p));//true
        System.out.println(coll.contains(new Personss("Jerry",20)));//false -->true

        //2.containsAll(Collection coll1):判断形参coll1中的所有元素是否都存在于当前集合中。
        Collection coll1 = Arrays.asList(123,4567);
        System.out.println(coll.containsAll(coll1));
    }

    @Test
    public void test3(){
        //3.remove(Object obj):从当前集合中移除obj元素。
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        coll.add(new Personss("Jerry",20));
        coll.add(new String("Tom"));
        coll.add(false);
        coll.remove(123);
        System.out.println(coll);//[456, Person{name='Jerry', age=20}, Tom, false]

        coll.remove(new Personss("Jerry",20));
        System.out.println(coll);//[456, Tom, false]

        //4. removeAll(Collection coll1):差集：从当前集合中移除coll1中所有的元素。
        Collection coll1 = Arrays.asList(123,456);
        coll.removeAll(coll1);
        System.out.println(coll); //[Tom, false]
    }

    @Test
    public void test4(){
        Collection coll = Arrays.asList(456,789);
        Collection coll1 = Arrays.asList(123,456,789);
        //5.retainAll(Collection coll1):交集：获取当前集合和coll1集合的交集，并返回给当前集合
        coll.retainAll(coll1);
        System.out.println(coll); //[456, 789]

        //6.equals(Object obj):要想返回true，需要当前集合和形参集合的元素都相同。
//        Collection coll1 = new ArrayList();
//        coll1.add(456);
//        coll1.add(123);
//        coll1.add(new Person("Jerry",20));
//        coll1.add(new String("Tom"));
//        coll1.add(false);

//        System.out.println(coll.equals(coll1));
    }

    @Test
    public void test5(){
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        coll.add(new Personss("Jerry",20));
        coll.add(new String("Tom"));
        coll.add(false);

        //7.hashCode():返回当前对象的哈希值
        System.out.println(coll.hashCode());

        //8.集合 --->数组：toArray()
        Object[] arr = coll.toArray();
        for(int i = 0;i < arr.length;i++){
            System.out.println(arr[i]);
        }

        //拓展：数组 --->集合:调用Arrays类的静态方法asList()
        List<String> list = Arrays.asList(new String[]{"AA", "BB", "CC"});
        System.out.println(list);

        List arr1 = Arrays.asList(new int[]{123, 456});
        System.out.println(arr1.size());//1

        List arr2 = Arrays.asList(new Integer[]{123, 456});
        System.out.println(arr2.size());//2

        //9.iterator():返回Iterator接口的实例，用于遍历集合元素。放在IteratorTest.java中测试

    }
}
