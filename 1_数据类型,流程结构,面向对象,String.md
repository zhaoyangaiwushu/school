# 基本数据类型有哪些,String是不是基本数据类型 ?

> - [ ] 整数类型:byte,int,short,long(建议数据后加L表示);
> - [ ] 浮点类型:Double,float(建议数据后加F表示);
> - [ ] 布尔类型:Boolean;
> - [ ] 字符型:Char;
> - [ ] String 不是基本数据类型.它定义的为对象;

|   数据类型   |     关键字     | 内存占用 |        取值范围        | 默认值 | 包装类              |
| :----------: | :------------: | :------: | :--------------------: | ------ | ------------------- |
|    字节型    |      byte      | 1个字节  |        -128~127        | 0      | java.lang.Byte      |
|    短整型    |     short      | 2个字节  |      -32768~32767      | 0      | java.lang.Short     |
|     整型     |  int（默认）   | 4个字节  | -2的31次方~2的31次方-1 | 0      | java.lang.Integer   |
|    长整型    |      long      | 8个字节  | -2的63次方~2的63次方-1 | 0      | java.lang.Long      |
| 单精度浮点数 |     float      | 4个字节  | 1.4013E-45~3.4028E+38  | 0.0    | java.lang.Float     |
| 双精度浮点数 | double（默认） | 8个字节  |  4.9E-324~1.7977E+308  | 0.0    | java.lang.Double    |
|    字符型    |      char      | 2个字节  |        0-65535         |        | java.lang.Character |
|   布尔类型   |    boolean     | 1个字节  |      true，false       | false  | Boolean             |

# 数据类型转换

> **当两个基本数据类型不一样时,会发生数据转换**
>
> - 自动类型数据转换->**隐式**
>   - 特点:代码不需要做特殊处理，自动转换
>   - 规则:数据范围从小到大,范围小的类型向范围大的类型提升
>   - byte、short、char‐‐>int‐‐>long‐‐>float‐‐>double
> - 强制类型转换->**显式**
>   - 浮点转成整数,直接取消小数点,可能造成数据损失精度.
>   - int强制转成short砍掉2个字节(不是四舍五入),可能造成数据丢失
>   - boolean类型不能发生数据类型转换
> - 对象class的强制类型转换称为->**造型**
>   - 无继承关系不可以转换
>   - 从子类到父类的类型转换可以自动进行
>   - 从父类到子类的类型转换必须通过造型(强制类型转换)实现
>   - 在转换前可以使用instanceof操作符判断一个对象的类型

# int和Integer有什么区别

> - Integer是int的包装类,int是基本数据类型;
> - Integer变量必须实例化后才能使用,而int变量不需要;
> - Integer实际是对象的引用,当new一个Integer时,实际上是生成一个指针指向此对象；而int则是直接存储数据值
> - Integer的默认值是null，int的默认值是0

# ==和Equals区别

> - ==
>   - 比较基本数据类型，比较的是值
>   - 比较引用数据类型，比较的是地址值（两个对象是否指向同一块内存）
>
> - equals
>   - equals属于Object类里的方法，没有重写默认是==
>   - 如果重写了equals我们往往比较的是对象中的属性是否相等
>
> - 重写equals时必须重写hashcode
>
>   - 当我们将在Map和Set集合存储对象时，存储对象要重写equals和hashcode
>
>   - ```java
>     /** 参照hashMap.put的过程 */
>     public static void main(String[] args) {
>             User user1 = new User("dd",12);
>             User user2 = new User("dd",12);
>     
>             System.out.println("user1的hash值为: " + user1.hashCode());
>             System.out.println("user2的hash值为: " + user2.hashCode());
>             Map<User,Integer> mp = new HashMap<User,Integer>();
>             mp.put(user1,1);
>             mp.put(user2,2);
>             System.out.println(mp.size());
>     }
>     /**
>     * 不重写hashCode
>     * ---------------------------
>     * user1的hash值为: 460141958
>     * user2的hash值为: 1163157884
>     * 2
>     * ---------------------------
>     */
>     
>     /**
>     * 重写hashCode
>     * ----------------------
>     * user1的hash值为: 99212
>     * user2的hash值为: 99212
>     * 1
>     * ----------------------
>     */
>     ```

# 基本数据类型,包装类,String三者之间如何转换

```java
/**
 * 1.基本数据和包装类转换自动装箱、自动拆箱
 */
Integer i = 11; int ii = 10;
System.out.println(i == ii);
/**
 * 2.基本类型以及包装类转string
 */
Long l = 12l; long ll = 12l;
String ls = String.valueOf(l),lls = String.valueOf(ll);
System.out.println(ls);  System.out.println(lls);
/**
 * 3.string转基本类型以及包装类
 */
int num = Integer.parseInt("1"); Integer num2 = Integer.parseInt("22");
System.out.println(num);  System.out.println(num2);
```

# char中能不能存贮一个中文汉字?

> char是用来存储Unicode编码的,unicode 编码字符集中包含了汉字,可以存储汉字。
>
> 如果特殊的汉字没有在unicode编码中,char中不能存储。
>
> 补充说明unicode编码占用两个字节,char 类型的变量也是占用两个字节

# switch-case 的穿透性

> 在switch语句中,如果case的后面不写break,将出现穿透现象,也就是不会在判断下一个case的值,
>
> 直接向后运行,直到遇到break,或者整体switch结束.

# 跳出流程语句

1.break终止 switch或者循环(for) 

```java
public static void main(String[] args) {
    for (int i = 1; i<=10; i++) {
        //需求:打印完两次HelloWorld之后结束循环
        if(i == 3){
          break;
        }
        System.out.println("HelloWorld"+i);
    }
}
```

2.continue结束本次循环，继续下一次的循环

```java
public static void main(String[] args) {
    for (int i = 1; i <= 10; i++) {
        //需求:不打印第三次HelloWorld
        if(i == 3){
          continue;
        }
        System.out.println("HelloWorld"+i);
    }
}
```

3.return其实也可以

> 有两个用途
>
> 1.当前方法返回什么值
>
> 2.退出当前方法

# switch语句能否作用在 byte,long,String 

> switch 语句中的变量类型可以是： byte、short、int 或者 char或者其包装类，也只string，枚举

# & 和&& 的区别

> - 相同点：
>   - &和&&都可以用作逻辑与的运算符，表示逻辑与（and）。
> - 不同点：
>   - &&具有短路的功能，而&不具备短路功能。
>   - 当&运算符两边的表达式的结果都为true时，整个运算结果才为true。而&&运算符第一个表达式为false时，则结果为false，不再计算第二个表达式。

# 数组中常见的异常有哪些

```java
public static void exceptionArrays() {
    int[] a= new int[6]; //静态创建
    //数组角标越界异常：ArrayIndexOutOfBoundsException:
    System.out.println(a[0]); //合理范围
    System.out.println(a[a.length - 1]); //越界

    //空指针异常NullPointerException:
    int[] arr3 = null;
    System.out.println(arr3[0]);
}
```

# 什么是面向对象

面向对象是把一个需求按照功能和特点进行划分，把这些存在共性的部分封装成对象，封装对象的目的也不是为了完成一个步骤，而是描述某一个事务在解决问题中的具体步骤

# 什么是类，什么是对象

> 类是一组相关属性和行为的集合可以看成是一类事物的模板,使用事物的属性特征和行为特征来描述该类事物.
>
> 对象:对象是类的实例

# 类与对象的关系

> 类是对一类事物的描述,是抽象的
>
> 对象是一类事物的实例,是具体的
>
> 类是对象的模板,对象是类的实体

# 面向对象的特征有哪些方面

```java
/**
 * 封装、继承 、抽象、多态
 * ====================================================
 * 1.封装:
 	答:就是类的私有化。是一种将代码和要处理的数据绑定在一起的编程机制，该机制可以保证程序和数据不受外部干扰。
 * ----------------------------------------------------
 *  1.1.将属性隐藏起来,若需要访问某个属性,提供公共方法对其访问。
 *  	1).使用private关键字来修饰成员变量。
 *  	2).对需要访问的成员变量,提供对应的一对getXxx方法,setXxx方法。
 		3).隐藏一个类中不需要对外提供的实现细节
 		4).使用者只能通过事先定制好的方法来访问数据，可以方便地加入控制逻辑，限制对属性的不合理操作；
		5).便于修改，增强代码的可维护性；
 	1.2.为什么需要封装？封装的作用和含义？
 	    1).我要用洗衣机,只需要按一下开关和洗涤模式就可以了.有必要了解洗衣机内部的结构吗?有必要碰电动机吗?
 	1.3.程序设计追求“高内聚，低耦合”。
 		1).高内聚 ：类的内部数据操作细节自己完成，不允许外部干涉；
 		2).低耦合 ：仅对外暴露少量的方法用于使用。
 	1.4.隐藏对象内部的复杂性,只对外公开简单的接口.便于外界调用,从而提高系统的可扩展性.可维护性.通俗的说,把该隐藏的隐藏起来,该暴露的暴露出来.这就是封装性的设计思想
 * ----------------------------------------------------
 * 2.继承:子类继承父类的所有状态和行为,同时开扩自身新的东西
 * ----------------------------------------------------
 *  2.1.多个类存在相同属性和行为时，将这些内容抽取到单独一个类中那么多个类无需再定义这些属性和行为，只要继承         那个类即可。
 *  2.3.提高代码的复用性
 *  2.4.继承的出现让类与类之间产生了关系，是多态的前提
 *  2.5.顶层父类是Object类,Java单继承,多层继承(继承体系)
 * ----------------------------------------------------
 * 3.抽象:
 * ----------------------------------------------------
 *  是将一类对象的共同特征总结出来构造类的过程，包括数据抽象和行为抽象两方面。抽象只关注对象有哪些属性和行为，
 *  并不关注这些行为的细节是什么。
 * ----------------------------------------------------
 * 4.多态:
 * ----------------------------------------------------
 *  4.1.同一操作作用于不同的对象，可以产生不同的效果。包括重载和重写。重载为编译时多态，重写是运行时多态。
 * ----------------------------------------------------
 */
```

# 成员变量和局部变量区别

|        区别        |                 成员变量                  |                   局部变量                    |
| :----------------: | :---------------------------------------: | :-------------------------------------------: |
|  在类中的位置不同  |                类中,方法外                |          方法中或者方法声明上(参数)           |
|   作用范围不一样   |                   类中                    |                    方法中                     |
|   初始化值的不同   |                 有默认值                  |       没有默认值.须先定义,赋值,才能使用       |
| 在内存中的位置不同 |             堆内存或 静态域内             |                    栈内存                     |
|    生命周期不同    | 随着对象的创建而存在,随着对象的消失而消失 | 随着方法的调用而存在,随着方法的调用完毕而消失 |
|       修饰符       |     private、public、static、final等      |     不能用权限修饰符修饰，可以用final修饰     |

# 匿名对象

```java
/*
	1.匿名对象就是只有右边的对象，没有左边的名字和赋值运算符。
	2.匿名对象只能使用一次，下次使用需要创建一个新对象。
	3.如果有一个对象只需要被调用一次，就可以用匿名对象。
	4.new 类对象().方法();
	5.匿名对象可以作为方法的参数和返回值
 */
```

# 构造方法,构造器

```java
/*
 * 3.构造方法,构造器
 * （1）在创建对象时候被自动调用的特殊方法,java为其提供了额外的垃圾回收器,对于不在使用的内存资源,垃圾回收期将其自己释放
 * （2）与类同名,没有返回值,可以进行重载的.重载根据:方法名称相同,参数列表不同.
 * （3）如果没有构造方法，那么编译器将会默认赠送一个构造方法，没有参数、方法体什么事情都不做。一旦编 写了至少一个构造方法，那么编译器将不再赠送。
 * （4）在java中初始化与创建是捆绑在一起的,两者不能分离
 * （5）构造器不能被重写(子类覆盖父类)
 */
```

# 方法重载(Overload)

```java
/**
 * 方法重载:在同一个类中,允许存在一个或以上的同名方法,只要它们的参数列表不同即可,与修饰符和返
 * 回值类型无关.
 * 参数列表:个数不同，数据类型不同，顺序不同.
 * 重载方法调用:JVM通过方法的参数列表，调用不同的方法.
 * 注意事项:不可取的方案->参数顺序不同(不好维护),基本类型的重载(自动提升,隐式转换)
 * 案例：System.out.println();
 */
```

# 方法重写(override/overwrite)

```java
/**
 * 1).子类中出现与父类一模一样的方法时(返回值类型,方法名和参数列表都相同),会出现覆盖效果,也称为重写或者复写.	  声明不变,重新实现。对父类已有方法增强
 * 注意事项
 * 2).子类方法覆盖父类方法,必须要保证权限大于等于父类权限。
 * 3).子类方法覆盖父类方法,返回值类型,函数名和参数列表都要一模一样。
 * 4).构造函数不能被重写
 * 5).static方法不能被重写,因为方法重写是基于运行时动态绑定的,而static
方法是编译时静态绑定的。
 */
```

# super和this的含义

> super ：代表父类的存储空间标识(可以理解为父亲的引用)。
> this ：代表当前对象的引用(谁调用就代表谁)。

# 作用域public,private,protected,不写时的区别

|    作用域     | 当前类 | 同一 package | 子孙类 | 其他package | 默认 |
| :-----------: | :----: | :----------: | :----: | :---------: | :--: |
|    public     |   √    |      √       |   √    |      √      |  ×   |
|   protected   |   √    |      √       |   √    |      ×      |  ×   |
| default(缺省) |   √    |      √       |   ×    |      ×      |  √   |
|    private    |   √    |      ×       |   ×    |      ×      |  ×   |

# static关键字

```java
/**
 * 1.类变量
 *  > 当static修饰成员变量时,该变量称为类变量.该类的每个对象都共享同一个类变量的值.任何对象都可以更改
 *  > 该类变量的值,但也可以在不创建该类的对象的情况下对类变量进行操作 class.static= 1
 *
 * 2.静态方法
 *  > 当 static 修饰成员方法时,该方法称为类方法.静态方法在声明中有 static,建议使用类名来调用,而不需要
 *  > 创建类的对象.调用方式非常简单
 *  静态方法调用的注意事项
 *      > 1.静态方法可以直接访问类变量和静态方法.
 *      > 2.静态方法 不能直接访问普通成员变量或成员方法。反之，成员方法可以直接访问类变量或静态方法.
 *      > 3.静态方法中,不能使用this关键字.
 *      > 4.静态方法只能访问静态成员
 *
 * 3.静态代码块
 *  > 定义在成员位置,使用static修饰的代码块{ }
 *  > 位置:类中方法外.
 *  > 执行:随着类的加载而执行且执行一次，优先于 main方法和构造方法的执行.
 *  > 作用:给类变量进行初始化赋值
 *
 * 4.静态原理
 * 1.是随着类的加载而加载的，且只加载一次。
 * 2.存储于一块固定的内存区域（静态区），所以，可以直接被类名调用。
 * 3.它优先于对象存在，所以，可以被所有对象共享。
 */
```

# static方法内发出对非static方法的调用?

> 1.普通方法必须创建一个对象才可以调用,静态方法调用时不需要创建对象直接调用。
>
> 2.比如当一个静态方法被调用时,可能还没有创建任何实例对象,如果从静态方法中发出对非
> 静态方法的调用，那个非静态方法是关联到哪个对象上的呢?

# 静态变量和实例变量的区别?

> 1.静态变量前要加 static 关键字，而实例变量前则不加
>
> 2.1.实例变量属于对象的属性,必须创建实例对象,其中的实例变量才会被分配空间,才能使用这个实例变量
>
> 2.2.静态变量被称为类变量,只要程序加载了类的字节码,不用创建任何实例对象,静态变量就会被分配空间,静态变量就可以用了

# final(最终的)

声明类,变量和方法时,可使用关键字final来修饰,表示最终的

final标记的类不能被继承

final标记的方法不能被子类重写

final标记的变量(成员变量或局部变量)即称为常量.名称大写，且只能被赋值一次

# 抽象类与抽象方法abstract

超类声明一个方法但不提供实现，该方法的实现由子类提供。

这样的方法称为抽象方法.有一个或更多抽象方法的类称为抽象类。

1 . 抽象类不能创建对象，如果创建，编译无法通过而报错。只能创建其非抽象子类的对象。

> 理解：假设创建了抽象类的对象，调用抽象的方法，而抽象方法没有具体的方法体，没有意义。

2 . 抽象类中，可以有构造方法，是供子类创建对象时，初始化父类成员使用的。

> 理解：子类的构造方法中，有默认的super()，需要访问父类构造方法。

3.抽象类中，不一定包含抽象方法，但是有抽象方法的类必定是抽象类。

> 理解：未包含抽象方法的抽象类，目的就是不想让调用者创建该类对象，通常用于某些特殊的类结构设
> 计。

4 . 抽象类的子类，必须重写抽象父类中所有的抽象方法，否则，编译无法通过而报错。除非该子类也是抽象
类。

> 理解：假设不重写所有抽象方法，则类中可能包含抽象方法。那么创建对象后，调用抽象的方法，没有
> 意义

# 实现向下转型?存在什么问题?解决?

```java
/**
 * 强转Man m = (Man)p;
 * 直接使用强转可以报ClassCastException异常,
 * 使用instanceof在进行向下转型前判断。
 * if(p instanceof Man){Man m = (Man)p;}
 */
class User{
    String name;
    int age;
    public boolean equals(Object obj){ //重写其equals()方法
        if(obj == this){
            return true;
        }
        if(obj instanceof User){
            User u = (User)obj;
            return this.age == u.age && this.name.equals(u.name);
        }
        return false;
    }

}
```

# 什么是接口?

- 接口的本质是契约,标准,规范,就像国家的法律一样.制定好后大家都要遵守。
- 在Java类的语法格式为先写extends,后写implements。
- 一个类可以实现多个接口,接口也可以继承其它接口。
- 实现接口的类中必须把接口中所有方法,才能实例化,否则,仍为抽象类。
- Java 8中，你可以为接口添加静态方法和默认方法。

# 抽象类与接口的区别

> - [ ] 有abstract修饰符是抽象类。
> - [ ] 接口中的方法都是抽象的,默认为public abstract类型,接口中的成员变量类型默认为 public static final。
> - [ ] 抽象类中可以有普通成员变量；
> - [ ] 接口中没有普通成员变量。
> - [ ] 抽象类可以包含普通方法；
> - [ ] 接口中必须都是抽象方法。
> - [ ] 抽象类中的抽象方法访问权限可以是 public、protected； 
> - [ ] 接口中的抽象方法只能是 public 类型的；
> - [ ] 抽象类单继承,接口多实现

# 内部类

```java
/**
 * 描述:在b类中定义的a类,a类的就是内部类,b为外部类
 * 场景:描述事物时,若一个事物内部还包含其他事物,就可以使用内部类这种结构.
 * 案例:汽车类Car中包含发动机类Engine,这时Engine就可以使用内部类来描述,定义在成员位置。
 * 特点:内部类可以直接访问外部类的成员(包括私有成员),外部类要访问内部类的成员,必须要建立内部类的对象。.
 * 总结:内部类仍然是一个独立的类,在编译之后会内部类会被编译成独立的.class文件,但是前面冠以外部类的类名和$符号    (Person$Heart.class)
 */
public class Car {
    private String 汽车名称 = "福美来2代";

    class Engine {
        private String 发动机型号 = "马自达ZM2";

        public void sayDescription() {
            System.out.println("汽车名称:"+汽车名称+"->"+"发动机型号:"+发动机型号);
            System.out.println(isCarName());
        }
    }

    private String isCarName() {
        return 汽车名称;
    }

    public void print(){
        Engine engine = new Engine();
        engine.sayDescription();
        System.out.println(engine.发动机型号);
    }
}

class InnerDemo {
    public static void main(String[] args) {
        // 创建外部类对象
        Car p = new Car();
        // 创建内部类对象
        Car.Engine engine = p.new Engine();
        engine.sayDescription();
        p.print();
    }
}
```

#  匿名内部类

```java
/**
 * 1.匿名内部类:是内部类的简化写法。
 * 2.接口案例:当你使用一个接口时
 * 1).你需要定义一个类去实现接口,
 * 2).并且重写接口中的方法,
 * 3).创建实现了接口类的对象
 * 4).调用重写之后的方法
 * 目的最终只是为了调用方法,能不能简化一下,把以上四步合成一步呢?匿名内部类就是做这样的快捷方式
 * 3.使用前提:匿名内部类必须继承一个父类或者实现一个父接口
 * 4.注意:
 * 1).匿名内部类不能定义任何静态成员、方法。
 * 2).匿名内部类中的方法不能是抽象的；
 * 3).匿名内部类必须实现接口或抽象父类的所有抽象方法。
 * 4).匿名内部类访问的外部类成员变量或成员方法必须用static修饰；
 */
//1.实现接口案例 ===================================================================================
public interface Animal {
    //写不写都一样都得必须实现
    public abstract void eat();
}

class Cat implements Animal {
    @Override
    public void eat() {
        System.out.println("猫吃鱼");
    }
}

class Dog implements Animal {
    @Override
    public void eat() {
        System.out.println("狗啃骨头");
    }
}

class InnerDemo5 {
    public static void main(String[] args) {
        method(new Dog());
        method(new Cat());
        method(new Animal() {
            @Override
            public void eat() {
                System.out.println("谢召阳吃梁晓鑫");
            }
        });
    }

    public static void method(Animal a) {
        a.eat();
    }
}


//2.实现抽象类案例 ===================================================================================
abstract class FlyAble {
    public abstract void fly();
}

class InnerDemo {
    public static void main(String[] args) {
        FlyAble f = new FlyAble() {
            public void fly() {
                System.out.println("我飞了~~~");
            }
        };
        f.fly();

        /**
         * 通常在方法的形式参数是接口或者抽象类时，也可以将匿名内部类作为参数传递。代码如下
         * 创建匿名内部类,直接传递给showFly(FlyAble f)
         */
        showFly(new FlyAble() {
            public void fly() {
                System.out.println("我飞了~~~");
            }
        });
    }

    public static void showFly(FlyAble f) {
        f.fly();
    }
}
```

匿名内部类案例

```java
//entity
public class Apple {
    private String color;
    private long weight;
    public Apple() {}
    public Apple(String color, long weight) {
        this.color = color;this.weight = weight;
    }
    public String getColor() {return color;}
    public void setColor(String color) {this.color = color;}
    public long getWeight() {return weight; }
    public void setWeight(long weight) {this.weight = weight;}
    @Override
    public String toString() {
        return "Apple{" +"color='" + color + '\'' +", weight=" + weight +'}';
    }
}

//service
interface AppleFilter {
    public boolean filter(Apple apple);
}

//test
class test {
    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(new Apple("green", 150),
                                         new Apple("yellow", 120), 
                                         new Apple("green", 170));
        List<Apple> apple = findApple(list, new AppleFilter() {
            @Override
            public boolean filter(Apple apple) {
                return ("green".equals(apple.getColor()));
            }
        });
        System.out.println(apple);
    }

    public static List<Apple> findApple(List<Apple> apples, AppleFilter appleFilter) {
        List<Apple> list = new ArrayList<>();
        for (Apple apple : apples) {
            if (appleFilter.filter(apple))
                list.add(apple);
        }
        return list;
    }
}
```

# 异常处理方式有几种

> 1.throws用在方法的声明上后接异常类名,是把异常抛给调用者
>
> 2.try...catch...finally捕获异常自己处理,处理完程序可以继续
>
> ​	try->可能出现异常的代码
>
> ​	catch-> 对异常处理
>
> ​	finally->有没有异常,都执行,一般用于释放资源.

# 常见的异常

> ```java
> /**
>  * 1.NullPointerException空指针异常 
>  * 出现原因：调用了未经初始化的对象或者是不存在的对象。
>  * 变量str的值为null，调用方法时，报空指针异常NullPointerException
>  * Exception in thread "main" java.lang.NullPointerException
>  * @param args
>  */
> public static void main(String[] args) {
>     String str=null;
>     System.out.println(str.length());
> }
> ```
>
> ```java
> /**
>  * 2.ArrayIndexOutOfBoundsException:数组索引越界异常
>  * 索引值2大于等于数组arr的长度时，报数组索引越界异常ArrayIndexOutOfBoundsException
>  * Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 2
>  * @param args
>  */
> public static void main(String[]args){
>     int arr[]={1,2};
>     System.out.println(arr[2]);
> }
> ```
>
> ```java
> /**
>  * 3.ArithmeticException:算术运算异常
>  * 整数0做了分母，报算术运算异常ArithmeticException:/by zero
>  * @param args
>  */
> public static void main(String[]args){
>     System.out.println(1/0);
> }
> ```
>
> ```java
> /**
>  * 4.NumberFormatException:数字格式异常
>  * 出现原因：字符型数据中包含非数字型字符
>  * 把字符串“itcast”转换为Integer类型时，当然会报数字格式化异常啦
>  * java.lang.NumberFormatException: For input string: "itcast"
>  * @param args
>  */
> public static void main(String[]args){
>     System.out.println(Integer.parseInt("itcast"));
> }
> 
> ClassNotFoundException 指定的类找不到；出现原因：类的名称和路径加载错误；通常都是程序试图通过字符串来加载某个类时可能引发异常。
> java.lang.IllegalArgumentException 方法传递参数错误。
> java.lang.ClassCastException 数据类型转换异常。
> ```

# Final、Finally、Finalize

> 1.final:修饰符(类和变量和方法).
>
> ​	1).修饰类时意味着它不能被继承，因此它和abstract为互斥的。
>
> ​	2).修饰变量时必须给初始值,该变量只能读取不可修改
>
> ​	3).修饰方法时,只能使用不能被重写。
>
> 2.finally:处理异常时候用,无论正常执行还是发生异常,只要JVM不关闭都能执行,一般用作释放资源
>
> 3.finalize：Object类的方法,该方法是由垃圾回收机制在销毁对象时调用的,通过重写finalize() 方法可以整理系统资源或者执行其他清理工作。

# 什么是String，它是什么数据类型？

> - [ ]  String声明为final的，不可被继承
> - [ ] String实现了Serializable接口：表示字符串支持序列化。
> - [ ] String实现了Comparable接口：表示String可以比较大小
> - [ ] String内部定义了final char[] value用于存储字符串数据
> - [ ] String:代表不可变的字符序列。
> - [ ] 只要你对字符串的内容进行修改就得重新造,因为字符串常量池中是不会存储相同内容的字符串的

# String是不可变的有什么好处？

> - [ ] 由于String是final的,所以在多线程中使用是安全的,我们不需要做任何其他同步操作。
> - [ ] String是不可变的,它的值也不能被改变,所以用来存储数据密码很安全。
> - [ ] 因为java字符串是不可变的,可以在java运行时节省大量java堆空间。
> - [ ] 因为不同的字符串变量可以引用池中的相同的字符串。
> - [ ] 如果字符串是可变得话，任何一个变量的值改变，就会反射到其他变量，那字符串池也就没有任何意义了。

# 创建String对象的不同方式有哪些？

> - [ ] 通过字面量的方式给一个字符串赋值,此时的字符串值声明在字符串常量池中。
> - [ ] 通过String str = new String("abc")方式创建对象。
>   - [ ] 其实创建了两个对象
>     - [ ] 一个是堆空间中地址引用，
>     - [ ] 另一个是String中char[]数组对应的字符串常量池中的数据："abc"

# String中的intern方法

intern()方法将指定字符串对象存储在字符串常量池中,如果字符串池已经有了同样值的字符串，则返回引用。

```java
/** 
*	当intern()方法被调用，如果字符串池中含有一个字符串和当前调用方法的字符串eqauls相等，那么就会返回池中的字符串。
*   如果池中没有的话，则首先将当前字符串加入到池中，然后返回引用。
*/
public static void main(String[] args) {
    String s3 = new String("123") + new String("123");
    s3.intern();
    String s4 = "123123";
    System.out.println(s3 == s4); //true
    System.out.println(s3.equals(s4)); //true
}
```

# 如何判断两个String是否相等？

> - [ ] 使用"=="或者使用equals方法。
> - [ ] 当使用"=="操作符时,不仅比较字符串的值,还会比较引用的内存地址。
> - [ ] 大多数情况下,我们只需要判断值是否相等,此时用equals方法比较即可。

# String是线程安全的吗？

> - [ ] String是不可变类,一旦创建了String对象,我们就无法改变它的值。
> - [ ] 因此，它是线程安全的，可以安全地用于多线程环境中。

# 为什么我们在使用HashMap的时候总是用String做key？

> - [ ] 因为字符串是不可变的,当创建字符串时,它的hashcode被缓存下来,不需要再次计算。
> - [ ] 因为HashMap内部实现是通过key的hashcode来确定value的存储位置,所以相比于其他对象更快。
> - [ ] 这也是为什么我们平时都使用String作为HashMap对象。

```java
public static void main(String[] args) {
    String s3 = new String("123") + new String("123");
    s3.intern();
    String s4 = "123123";
    System.out.println(s3 == s4); //true
    System.out.println(s3.equals(s4)); //true

    String s1 = new String("abc");
    String s2 = new String("abc");
    String intern = s1.intern();
    String intern1 = s2.intern();
    //比较地址信息所以不相等了
    System.out.println(s1 == s2); //false
    //都跑字符串常量池里了 因为字符串常量池中是不会存储相同内容的字符串的 所以相等
    System.out.println(intern == intern1); //true
}
```

# 下面的代码将创建几个字符串对象。

String s1 = new String("Hello");  
String s2 = new String("Hello");

> 答案:是3个对象.
>
> 第一，行1 字符串池中的“hello”对象。
>
> 第二，行1，在堆内存中带有值“hello”的新字符串。
>
> 第三，行2，在堆内存中带有“hello”的新字符串。这里“hello”字符串池中的字符串被重用。

# string常用方法

> - [ ] **int length()**    获取字符串的长度
>
> - [ ] **char charAt(int index)**    获取指定索引位置的字符串
>
> - [ ] **boolean isEmpty()**   判断字符串是否为空
>
> - [ ] **String toLowerCase()**   将字符串转小写
>
> - [ ] **String toUpperCase()**   将字符串装大写
>
> - [ ] **String trim()**   去除首尾空格
>
> - [ ] **boolean equals(Object obj)**   比较字符串的内容是否相同
>
> - [ ] **boolean equalsIgnoreCase(String anotherString)**   比较字符串的内容是否相同,忽略大小写
>
> - [ ] **String concat(String str)**   字符串拼接。等价于用“+”
>
> - [ ] **int compareTo(String anotherString)**   比较两个字符串的大小返回差值-1,-2
>
> - [ ] **String substring(int beginIndex)**   从指定位置开始截取一直截取到最后一个字符串并返回一个新的字符串
>
> - [ ] **String substring(int beginIndex, int endIndex)**   从beginIndex开始截取到endIndex并返回一个新字符串
>
> - [ ] **boolean endsWith(String suffix)**   判断字符串是否以指定的后缀结尾
>
> - [ ] **boolean startsWith(String prefix)**   判断字符串是否以指定的前缀开始
>
> - [ ] **boolean startsWith(String prefix, int toffset)**  判断此字符串从指定索引位置开始的子字符串是否以指定前缀开始
>
> - [ ] **boolean contains(CharSequence s)**   判断字符是否在当前字符串中出现
>
> - [ ] **int indexOf(String str)**   返回指定子字符第一次出现的索引位置
>
> - [ ] **int indexOf(String str, int fromIndex)**   从指定的位置开始，查看字符第一次出现的索引位置
>
> - [ ] **int lastIndexOf(String str)**   返回指定子字符串在此字符串中最右边出现处的索引
>
> - [ ] **int lastIndexOf(String str, int fromIndex)**   返回指定子字符串在此字符串中最后一次出现的索引,从指定的索引开始反向搜索
>
> - [ ] ```java
>   //注：indexOf和lastIndexOf方法如果未找到都是返回-1
>   //什么情况下，indexOf(str)和lastIndexOf(str)返回值相同？
>   //情况一：存在唯一的一个str。情况二：不存在str
>   ```
>
> - [ ] **String replace(char oldChar, char newChar)**   把指定char替换为你要的char,并返回新字符串。
>
> - [ ] **String replace(CharSequence target, CharSequence replacement)**   把指定字符串替换为你要的字符串,并返回新字符串
>
> - [ ] **split(",")**   字符串转数组

```java
@Test
public void test1() {
    //int length()  返回字符串的长度:return value.length
    String s1 = "HelloWorld";
    System.out.println("字符串长度为:"+s1.length());

    //char charAt(int index)  返回某索引处的字符:return value[index]
    System.out.println("返回某索引处的字符0:"+s1.charAt(0));
    System.out.println("返回某索引处的字符9:"+s1.charAt(9));

    //boolean isEmpty()  判断是否是空字符串:return value.length == 0
    System.out.println("判断是否是空字符串:"+s1.isEmpty());

    //String toLowerCase()：使用默认语言环境，将 String 中的所有字符转换为小写
    //String toUpperCase()：使用默认语言环境，将 String 中的所有字符转换为大写
    String s2 = s1.toLowerCase();
    System.out.println("原字符:"+s1);//s1不可变的，仍然为原来的字符串
    System.out.println("小写之后的字符"+s2);//改成小写以后的字符串

    //String trim()：返回字符串的副本，忽略前导空白和尾部空白
    String s3 = "   he  llo   world   ";
    String s4 = s3.trim();
    System.out.println("没忽略之前:"+s3);
    System.out.println("忽略前导空白和尾部空白:"+s4);
}
```

```java
@Test
public void test2() {
    String s1 = "HelloWorld";
    String s2 = "helloworld";
    //boolean equals(Object obj)：比较字符串的内容是否相同
    System.out.println("比较字符串是否相等不忽略大小写:"+s1.equals(s2));

    //boolean equalsIgnoreCase(String anotherString)：与equals方法类似，忽略大小写
    System.out.println("比较字符串是否相等忽略大小写:"+s1.equalsIgnoreCase(s2));

    //String concat(String str)：将指定字符串连接到此字符串的结尾。 等价于用“+”
    String s3 = "abc";
    String s4 = s3.concat("def");
    System.out.println("字符串拼接:"+s4);

    //int compareTo(String anotherString)：比较两个字符串的大小
    String s5 = "abc";
    String s6 = new String("abe");
    System.out.println("比较两个字符串的大小:"+s5.compareTo(s6));//涉及到字符串排序

    //String substring(int beginIndex)：返回一个新的字符串，它是此字符串的从beginIndex开始截取到最后的一个子字符串。
    String s7 = "北京尚硅谷教育";
    String s8 = s7.substring(2);
    System.out.println(s7);
    System.out.println(s8);

    //String substring(int beginIndex, int endIndex) ：返回一个新字符串，它是此字符串从beginIndex开始截取到endIndex(不包含)的一个子字符串。
    String s9 = s7.substring(2, 5);
    System.out.println(s9);
}
```

```java
@Test
public void test3(){
    //boolean endsWith(String suffix)：判断字符串是否以指定的后缀结尾
    String str1 = "hellowworld";
    boolean b1 = str1.endsWith("rld");
    System.out.println(b1);

    //boolean startsWith(String prefix)：判断字符串是否以指定的前缀开始
    boolean b2 = str1.startsWith("He");
    System.out.println(b2);
		
    //boolean startsWith(String prefix, int toffset)：判断此字符串从指定索引位置开始的子字符串是否以指定前缀开始
    boolean b3 = str1.startsWith("ll",2);
    System.out.println(b3);

    //boolean contains(CharSequence s)：当且仅当此字符串包含指定的 char 值序列时，返回 true
    String str2 = "wor";
    System.out.println(str1.contains(str2));

    //int indexOf(String str)：返回指定子字符串在此字符串中第一次出现处的索引
    System.out.println(str1.indexOf("lol"));

    //int indexOf(String str, int fromIndex)：返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始
    System.out.println(str1.indexOf("lo",5));

    /**
     * 注：indexOf和lastIndexOf方法如果未找到都是返回-1
     */
    String str3 = "hellorworld";
    //int lastIndexOf(String str)：返回指定子字符串在此字符串中最右边出现处的索引
    System.out.println(str3.lastIndexOf("or"));

    //int lastIndexOf(String str, int fromIndex)：返回指定子字符串在此字符串中最后一次出现处的索引，从指定的索引开始反向搜索
    System.out.println(str3.lastIndexOf("or",6));

    //什么情况下，indexOf(str)和lastIndexOf(str)返回值相同？
    //情况一：存在唯一的一个str。情况二：不存在str
}
```

```java
@Test
public void test4(){
    //String replace(char oldChar, char newChar)：把指定char替换为你要的char,并返回新的字符串。
    String str1 = "北京尚硅谷教育北京";
    String str2 = str1.replace('北', '东');
    System.out.println(str1);//北京尚硅谷教育北京
    System.out.println(str2);//东京尚硅谷教育东京

    //String replace(CharSequence target, CharSequence replacement)：把指定字符串替换为你要的字符串,并返回新的字符串。。
    String str3 = str1.replace("北京", "上海");
    System.out.println(str3); //上海尚硅谷教育上海

    //String replaceAll(String regex, String replacement)：使用给定的 replacement 替换此字符串所有匹配给定的正则表达式的子字符串。
    String str = "12hello34world5java7891mysql456";
    //把字符串中的数字替换成,，如果结果中开头和结尾有，的话去掉
    String string = str.replaceAll("\\d+", ",").replaceAll("^,|,$", "");
    System.out.println(string);
    System.out.println("*************************");

    //boolean matches(String regex)：告知此字符串是否匹配给定的正则表达式。
    str = "12345";
    //判断str字符串中是否全部有数字组成，即有1-n个数字组成
    boolean matches = str.matches("\\d+");
    System.out.println(matches);
    String tel = "0571-4534289";
    //判断这是否是一个杭州的固定电话
    boolean result = tel.matches("0571-\\d{7,8}");
    System.out.println(result);
    System.out.println("*************************");

    //字符串转数组split(",")
    str = "hello|world|java";
    String[] strs = str.split("\\|");
    for (int i = 0; i < strs.length; i++) {
        System.out.println(strs[i]);
    }
    System.out.println();
    str2 = "hello.world.java";
    String[] strs2 = str2.split(".");
    for (int i = 0; i < strs2.length; i++) {
        System.out.println(strs2[i]);
    }

}
```

# String 与 byte[]之间的转换

```java
@Test
public void test3() throws UnsupportedEncodingException {
    String str1 = "abc123中国";
    byte[] bytes = str1.getBytes();//使用默认的字符集，进行编码。
    System.out.println(Arrays.toString(bytes));

    byte[] gbks = str1.getBytes("gbk");//使用gbk字符集进行编码。
    System.out.println(Arrays.toString(gbks));

    System.out.println("******************");

    String str2 = new String(bytes);//使用默认的字符集，进行解码。
    System.out.println(str2);

    String str3 = new String(gbks);
    System.out.println(str3);//出现乱码。原因：编码集和解码集不一致！

    String str4 = new String(gbks, "gbk");
    System.out.println(str4);//没有出现乱码。原因：编码集和解码集一致！
}
```

# String 与 char[]之间的转换

```java
@Test
public void test2(){
    String str1 = "abc123";  //题目： a21cb3
    char[] charArray = str1.toCharArray();
    for (int i = 0; i < charArray.length; i++) {
        System.out.println(charArray[i]);
    }

    char[] arr = new char[]{'h','e','l','l','o'};
    String str2 = new String(arr);
    System.out.println(str2);
}
```

# String 与基本数据类型、包装类之间的转换

```java
@Test
public void test1(){
String str1 = "123";
//int num = (int)str1;//错误的
int num = Integer.parseInt(str1);

String str2 = String.valueOf(num);//"123"
String str3 = num + "";

System.out.println(str1 == str3);
}
```

# StringBuffer,StringBuilder的常用方法

```java
public static void main(String[] args) {
    StringBuffer s1 = new StringBuffer("hello Word");

    //1.StringBuffer append(xxx)：从尾部拼接字符串
    s1.append(',');
    s1.append("java");
    System.out.println(s1); //hello Word,java

    //2.StringBuffer replace(int start, int end, String str)：字符串替换
    s1.replace(0,1,"Hi,H"); //Hi,Hello Word,java
    System.out.println(s1);

    //3.StringBuffer insert(int offset, xxx)：在指定位置插入字符串
    s1.insert(2,"!"); //Hi!,Hello Word,java
    System.out.println(s1);
    //3.1public char charAt(int n ) //返回指定位置的字符
    char c = s1.charAt(1);
    System.out.println(c);
    //3.2public void setCharAt(int n ,char ch) //在指定位置插入字符
    s1.setCharAt(1,'-');
    System.out.println(s1);

    //4.StringBuffer reverse() ：字符序列翻转
    System.out.println(s1.reverse());
    System.out.println(s1.reverse());

    //5.public int indexOf(String str) //查看字符串出现的位置
    //5.1.public int lastIndexOf(String str) //查看字符串出现的位置
    System.out.println(s1.lastIndexOf("e"));

    //6.public String substring(int start,int end):返回一个从start开始到end索引结束的左闭右开区间的子字符串
    String s2 = s1.substring(1, 3);
    System.out.println(s2);

    //7.public int length() 返回字符串长度
    System.out.println(s1.length());

    //8.StringBuffer delete(int start,int end)：删除指定位置的内容
    s1.delete(2,4);
    System.out.println(s1);
}
```

# 浅谈一下String, StringBuffer,StringBuilder的区别？

> - [ ] String是不可变类,每当我们对String进行操作的时候,总是会创建新的字符串。
> - [ ] 操作String很耗资源,所以Java提供了两个工具类来操作StringBuffer和StringBuilder。
> - [ ] StringBuffer和StringBuilder是可变类。
> - [ ] StringBuffer是线程安全的,StringBuilder则不是线程安全的。
> - [ ] 所以在多线程对同一个字符串操作的时候,我们应该选择用StringBuffer。
> - [ ] 由于不需要处理多线程的情况,StringBuilder的效率比StringBuffer高。
> - [ ] String str = new String();//底层创建了这样的char[] value = new char[0]; 数组。
> - [ ] StringBuffer sb1 = new StringBuffer();底层创建了一个长度是16的数组(new char[16])。
> - [ ] 扩容的机制为原来的二倍,并将原数组的元素复制到新的数组中。
> - [ ] 开发中建议使用StringBuffer或StringBuilder因为String每次都得新造效率最低
> - [ ] 效率的话从高到低排列StringBuilder > StringBuffer > String

# String、StringBuffer、StringBuilder效率测试

```java
/**
 * 对比String、StringBuffer、StringBuilder三者的效率：
 * 从高到低排列：StringBuilder > StringBuffer > String
 * StringBuffer的执行时间：3
 * StringBuilder的执行时间：2
 * String的执行时间：1495
 * @param args
 */
public static void main(String[] args) {
    //初始设置
    long startTime = 0L;
    long endTime = 0L;
    String text = "";
    StringBuffer buffer = new StringBuffer("");
    StringBuilder builder = new StringBuilder("");
    //开始对比
    startTime = System.currentTimeMillis();
    for (int i = 0; i < 20000; i++) {
        buffer.append(String.valueOf(i));
    }
    endTime = System.currentTimeMillis();
    System.out.println("StringBuffer的执行时间：" + (endTime - startTime));

    startTime = System.currentTimeMillis();
    for (int i = 0; i < 20000; i++) {
        builder.append(String.valueOf(i));
    }
    endTime = System.currentTimeMillis();
    System.out.println("StringBuilder的执行时间：" + (endTime - startTime));

    startTime = System.currentTimeMillis();
    for (int i = 0; i < 20000; i++) {
        text = text + i;
    }
    endTime = System.currentTimeMillis();
    System.out.println("String的执行时间：" + (endTime - startTime));
}
```