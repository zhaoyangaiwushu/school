# 1.什么是spring

spring是一个轻量级的ioc和aop框架，是为java提供基础性能服务的框架

# 2.spring的优点

spring属于低侵入式设计，代码污染性低

spring中的ioc机制，把容器的依赖关系交给spring来管理降低程序的耦合性

spring中的aop技术，可以将一些通用操作，如权限，日志，事务等进行统一处理，实现代码更好的复用

# 3.Spring的IoC理解：

ioc就是控制反转,即创建对象的控制权的转移。

简单说就是spring的ioc机制在我们需要用对象的时候不需要去new一个新的了。

spring使用java反设机制，根据配置文件,动态的去创建对象，管理对象，调用对象的方法，以及传递参数等

# 4.Spring的DI理解：

DI 依赖注入，既应用程序在运行时，根据ioc容器动态的注入所需要的外部资源

# 5.aop

面向切面编程，

你比如说我管理后台没访问一个接口就往日志表中存日志

比如事务，我想调用一个方法之前开启事务，结束之后关闭事务

它呢有几个关键词

**切面**:就是很多功能都有的重复的代码抽取，这个时候就需要往业务方法上动态植入“切面类代码”

**切点**:可以通过切点表达式，指定拦截哪些类的哪些方法； 给指定的类植入切面类代码。

**通知** :在对象上面执行的内容

**aop实现原理**:aop的底层实现是代理模式加反射

反射不说了,代理模式分为多种,静态代理和动态代理,动态代理面又分为jdk动态代理和cglib动态代理

代理模式的好处:可以防止对方得到我们真实的方法;

**CGLIB动态代理与JDK动态区别**

java动态代理是利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理。

而cglib动态代理是利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。

**在Spring**中。

1)、如果目标对象实现了接口，默认情况下会采用JDK的动态代理实现AOP

2)、如果目标对象实现了接口，可以强制使用CGLIB实现AOP

3)、如果目标对象没有实现了接口，必须采用CGLIB库，spring会自动在JDK动态代理和CGLIB之间转换

# 6.BeanFactory和ApplicationContext的区别

- BeanFactroy采用的是延迟加载形式来注入Bean的，什么时候使用什么时候创建
- （貌似是）ApplicationContext，它是在容器启动时，一次性创建了所有的Bean。只要一读取配置文件就会创建所有对象
- 相对于BeanFactory，ApplicationContext 唯一的不足是占用内存空间。当应用程序配置Bean较多时，程序启动较慢。

# 7.ApplicationContext接口的实现类

- ClassPathXmlApplicationContext ：它是从类的根路径下加载配置文件 推荐使用这种
- FileSystemXmlApplicationContext ：它是从磁盘路径上加载配置文件，配置文件可以在磁盘的任意位置。
- AnnotationConfigApplicationContext:当我们使用注解配置容器对象时，需要使用此类来创建 spring 容器。它用来读取注解。

# 8.spring基于xml注入bean的几种方式

set方法，构造函数，p名称空间

# 9.Spring 框架中都用到了哪些设计模式？

- 工厂模式：BeanFactory就是简单工厂模式的体现，用来创建对象的实例；
- 单例模式：Bean默认为单例模式。
- 代理模式：Spring的AOP功能用到了JDK的动态代理和CGLIB字节码生成技术；
- 模板方法：用来解决代码重复的问题。比如. RestTemplate,JmsTemplate, JpaTemplate。
- 观察者模式：定义对象键一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都会得到通知被制动更新，如Spring中listener的实现--ApplicationListener。

# 10.bean  的作用范围和生命周期

- singleton：默认，每个容器中只有一个bean的实例。
- prototype：为每一个bean请求提供一个实例。
- request：为每一个网络请求创建一个实例，在请求完成以后，bean会失效并被垃圾回收器回收。
- session：与request范围类似，确保每个session中有一个bean的实例，在session过期后，bean会随之失效。
- global-session：全局作用域

# **8、Spring框架中的单例Beans是线程安全的么？**

​    Spring框架并没有对单例bean进行任何多线程的封装处理。关于单例bean的线程安全和并发问题需要开发者自行去搞定。但实际上，大部分的Spring bean并没有可变的状态(比如Serview类和DAO类)，所以在某种程度上说Spring的单例bean是线程安全的。如果你的bean有多种状态的话（比如 View Model 对象），就需要自行保证线程安全。最浅显的解决办法就是将多态bean的作用域由“singleton”变更为“prototype”。

# **9、Spring如何处理线程并发问题？**

在一般情况下，只有无状态的Bean才可以在多线程环境下共享，在Spring中，绝大部分Bean都可以声明为singleton作用域，因为Spring对一些Bean中非线程安全状态采用ThreadLocal进行处理，解决线程安全问题。

ThreadLocal和线程同步机制都是为了解决多线程中相同变量的访问冲突问题。同步机制采用了“时间换空间”的方式，仅提供一份变量，不同的线程在访问前需要获取锁，没获得锁的线程则需要排队。而ThreadLocal采用了“空间换时间”的方式。

ThreadLocal会为每一个线程提供一个独立的变量副本，从而隔离了多个线程对数据的访问冲突。因为每一个线程都拥有自己的变量副本，从而也就没有必要对该变量进行同步了。ThreadLocal提供了线程安全的共享对象，在编写多线程代码时，可以把不安全的变量封装进ThreadLocal。

# 11.常用注解

- 用于创建对象的
  - 相当于<bean id="" class="">
  - @Component
    - 作用：把资源让 spring 来管理。相当于在 xml 中配置一个 bean。
    - 属性：value：指定 bean 的 id。如果不指定 value 属性，默认 bean 的 id 是当前类的类名。首字母小写。
  - @Controller @Service @Repository
    - 他们三个注解都是针对一个的衍生注解，他们的作用及属性都是一模一样的。
    - 他们只不过是提供了更加明确的语义化。
    - @Controller ：一般用于表现层的注解。
    - @Service ：一般用于业务层的注解。
    - @Repository ：一般用于持久层的注解。
    - 细节：如果注解中有且只有一个属性 要赋值时是 ，且名称是 value ，value  在赋值是可以不写。
- 用于注入数据的
  - 相当于：<property name="" ref="">
  - @Autowired
    - 作用：自动按照类型注入。当使用注解注入属性时，set方法可以省略。它只能注入其他 bean 类型。当有多个类型匹配时，使用要注入的对象变量名称作为 bean 的 id，在 spring 容器查找，找到了也可以注入成功。找不到就报错。
  - @Qualifier
    - 作用：在自动按照类型注入的基础之上，再按照 Bean 的 id 注入。它在给字段注入时不能独立使用，必须和@Autowire 一起使用；但是给方法参数注入时，可以独立使用。
    - 属性：value：指定 bean 的 id。
  - @Resource
    - 作用：直接按照 Bean 的 id 注入。它也只能注入其他 bean 类型。
    - 属性：name：指定 bean 的 id。
  - @Value
    - 作用：注入基本数据类型和 String 类型数据的
    - 属性：value：用于指定值
- 用于改变作用范围的
  - 相当于：<bean id="" class="" scope="">
  - @Scope
    - 作用：指定 bean 的作用范围。
      属性：value：指定范围的值。
      取值：singleton prototype request session globalsession
- 和生命周期相关的
  - 相当于：<bean id="" class="" init-method="" destroy-method="" />
  - @PostConstruct 
    - 用于指定初始化方法
  - @PreDestroy 
    - 用于指定销毁方法
- 新注解说明
  - @ComponentScan
    - <context:component-scan base-package="com.itheima"/>是一样的。
    - 用于指定 spring 在初始化容器时要扫描的包
    - basePackages：用于指定要扫描的包。和该注解中的 value 属性作用一样。
  - @Bean
    - 作用：该注解只能写在方法上，表明使用此方法创建一个对象，并且放入 spring 容器。
    - 属性：name：给当前@Bean 注解方法创建的对象指定一个名称(即 bean 的 id）。
  - @PropertySource
    - 作用：用于加载.properties 文件中的配置。例如我们配置数据源时，可以把连接数据库的信息写到
      properties 配置文件中，就可以使用此注解指定 properties 配置文件的位置
    - 属性：value[]：用于指定 properties 文件位置。如果是在类路径下，需要写上 classpath:
  - @Import
    - 作用：用于导入其他配置类，在引入其他配置类时，可以不用再写@Configuration 注解
    - 属性：value[]：用于指定其他配置类的字节码。
  - @Configuration
    - 作用：用于指定当前类是一个 spring 配置类，当创建容器时会从该类上加载注解。获取容器时需要使用AnnotationApplicationContext(有@Configuration 注解的类.class)。
    - 属性：value:用于指定配置类的字节码

# **11、Spring 框架中都用到了哪些设计模式？**

（1）工厂模式：BeanFactory就是简单工厂模式的体现，用来创建对象的实例；

（2）单例模式：Bean默认为单例模式。

（3）代理模式：Spring的AOP功能用到了JDK的动态代理和CGLIB字节码生成技术；

（4）模板方法：用来解决代码重复的问题。比如. RestTemplate, JmsTemplate, JpaTemplate。

（5）观察者模式：定义对象键一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都会得到通知被制动更新，如Spring中listener的实现--ApplicationListener。

# **12、Spring事务的实现方式和实现原理：**

# 12.SpringMVC执行原理

1.一旦[Http请求](https://www.baidu.com/s?wd=Http%E8%AF%B7%E6%B1%82&tn=SE_PcZhidaonwhc_ngpagmjz&rsv_dl=gh_pc_zhidao)到来，DispatcherSevlet将负责将请求分发。

2.在DispatcherServlet将请求分发给Spring Controller之前，需要借助于Spring提供的HandlerMapping定位到具体的Controller。

> 3.DispatcherServlet可以认为是Spring提供的前端控制器，所有的请求都有经过它来统一分发

3.Spring Controller处理来自DispatcherServlet的请求并且接受HttpServletRequest和HttpServletResponse。

4.Controller处理完客户请求，则返回ModelAndView对象给DispatcherServlet前端控制器

> 5.ModelAndView是Http请求过程中返回的模型和视图。

5.DispatcherServlet将View对象渲染出的结果返回给客户

---



（1）用户发送请求至前端控制器DispatcherServlet；
（2） DispatcherServlet收到请求后，调用HandlerMapping处理器映射器，请求获取Handle；
（3）处理器映射器根据请求url找到具体的处理器，生成处理器对象及处理器拦截器(如果有则生成)一并返回给DispatcherServlet；
（4）DispatcherServlet 调用 HandlerAdapter处理器适配器；
（5）HandlerAdapter 经过适配调用 具体处理器(Handler，也叫后端控制器)；
（6）Handler执行完成返回ModelAndView；
（7）HandlerAdapter将Handler执行结果ModelAndView返回给DispatcherServlet；
（8）DispatcherServlet将ModelAndView传给ViewResolver视图解析器进行解析；
（9）ViewResolver解析后返回具体View；
（10）DispatcherServlet对View进行渲染视图（即将模型数据填充至视图中）
（11）DispatcherServlet响应用户。



