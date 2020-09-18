# 了解:什么是ORM

> - 即Object-Relationl Mapping,它的作用是在关系型数据库和对象之间作一个映射。
> - 这样,我们在具体的操作数据库的时候,就不需要再去和复杂的SQL语句打交道。
> - 只要像平时操作对象一样操作它就可以了 。

# 了解:什么是Mybatis？

> - Mybatis是一个半ORM框架,它内部提供了对JDBC的封装。
> - 开发时我们只需要关注sql本身。
> - 而不需要花费精力去处理加载驱动、创建连接、创建statement等繁杂的过程。
> - 直接编写原生态sql,可以严格控制sql执行性能,灵活度高。

> - MyBatis可以使用XML或注解来配置和映射原生信息,将POJO映射成数据库中的记录。
> - 避免了几乎所有的 JDBC代码和手动设置参数以及获取结果集。

> - 通过xml文件或注解的方式将要执行的各种statement配置起来。
> - 并通过java对象和statement中sql的动态参数进行映射生成最终执行的sql语句。
> - 最后由mybatis框架执行sql并将结果映射为java对象并返回(从执行sql到返回result的过程)。

# 了解:Mybaits的优点：

> - 基于SQL语句编程,相当灵活,不会对现有的应用程序和数据库设计造成影响,SQL写在XML里,j解决sql与代码的耦合,便于统一管理。提供XML标签,支持编写动态SQL语句,并可重用。
> - 与JDBC相比,减少了代码量,消除了JDBC中大量冗余的代码,不需要手动开关连接；
> - 很好的与各种数据库兼容（因为MyBatis使用JDBC来连接数据库，所以只要JDBC支持的数据库MyBatis都支持）。
> - 能够与Spring很好的集成；
> - 提供映射标签,支持对象与数据库的ORM字段关系映射；提供对象关系映射标签,支持对象关系组件维护。

# 了解:MyBatis框架的缺点：

> - SQL依赖数据库,导致数据库移植性差,不能随意更换数据库。

# 了解:MyBatis框架适用场合：

> - MyBatis专注于SQL本身,是一个足够灵活的DAO层解决方案。
> - 性能的要求比较高，需求变化较多的项目，如互联网项目，MyBatis将是不错的选择。

# 了解:MyBatis与Hibernate有哪些不同？

> - mybatis他不完全是一个ORM框架,因为mybatis需要我们自己写sql,适合对关系数据模型要求不高的软件开发。
> - mybatis无法做到数据库无关性,如果需要实现支持多种数据库的软件,则需要自定义多套sql映射文件,工作量大。
> - Hibernate对象/关系映射能力强,数据库无关性好,适合关系模型要求高的软件,可以节省很多代码，提高效率。

# #{}和${}的区别是什么？

#{}:是预编译处理,会将sql中的#{}替换为?号,防止sql注入;

${}是字符串替换,就是把${}替换成变量的值。会有安全问题；

原生jdbc不支持占位符的地方我们就可以使用${}进行取值

```sql
select * from ${year}_salary where xxx;
select * from tbl_employee order by ${f_name} ${order}
```

# 实体类中属性名和表中的字段名不一样 ,怎么办 ？

> - 开启驼峰命名法 在setting中设置
>
>   - autoMappingBehavior默认是PARTIAL开启自动映射的功能。
>     - 唯一的要求是列名和javaBean属性名一致
>   - 如果autoMappingBehavior设置为null则会取消自动映射
>
> - 方式一：通过设置sql字段的别名,让字段名的别名和实体类的属性名一致。
>
> - ```java
>   <select id="selectorder" parametertype="int" resultetype="me.gacl.domain.order">
>       select order_id as id,order_no as orderno form orders where order_id=#{id}
>   </select>
>   ```

> - 第2种： 通过<resultMap>来映射字段名和实体类属性名的一一对应的关系。
>
> - ```java
>   <resultMap type="com.agzy.day2.bean.Employee" id="MySimpleEmp">
>       //column：指定哪一列,
>       //property：指定对应的javaBean属性
>       <id column="id" property="id"/>
>       <result column="last_name" property="lastName"/>
>       <result column="email" property="email"/>
>       <result column="gender" property="gender"/>
>   </resultMap>
>   ```

# 模糊查询like语句该怎么写?

> - 在Java代码中添加sql通配符
>
>   - ```java
>     //xml
>     <select id="getEmpByName">
>         select * from foo where bar like #{value}
>     </select>
>     //java
>     list<name> names = mapper.getEmpByName("%smi%");
>     ```
>
> - 在sql语句中拼接通配符,会引起sql注入
>
>   - ```java
>     //xml
>     <select id="selectlike">
>         select * from foo where bar like "%"#{value}"%"
>     </select>
>     //java
>     list<name> names = mapper.getEmpByName("smi");
>     ```

# 在mapper中如何传递多个参数?

> 第一种 dao中有多个参数 xml中对应使用#{0}，#{1}..
>
> ```java
> //dao
> UserselectUser(String name,String area);
> //xml
> <select id="selectUser"resultMap="BaseResultMap">  
>  select * from user_user_t where user_name = #{0} and user_area=#{1}  
> </select> 
> ```

 

> 第二种： 使用 @param 注解: xml直接使用参数名
>
> ```java
> //dao
> UserselectUser(@param("name") String name,@param("area") String area);
> //xml
> <select id="selectUser"resultMap="BaseResultMap">  
>  select * from user_user_t where user_name = #{name} and user_area=#{area}  
> </select> 
> ```



> 第三种：多个参数封装成map xml中直接使用key
>
> ```java
> //dao
> public Employee selectUser(Map<String,Object> map);
> //xml
> <select id="selectUser" resultType="employee">
> 	select * from user_user_t where user_name = #{name} and user_area=#{area} 
> </select>
> ```



> 第四种:pojo xml中直接属于属性名称
>
> ```java
> //dao
> public Employee selectUser(Employee employee);
> //xml
> <select id="selectUser" resultType="employee">
> 	select * from user_user_t where user_name = #{name} and user_area=#{area} 
> </select>
> ```

# parameterType

> 可以不传，MyBatis会根据TypeHandler自动推断
>
> 用于指定传入参数的类型
>
> - **单个参数**：mybatis不会做特殊处理
>
> - **使用方式**：#{参数名/任意名}：取出参数值
>
> - ---
>
> - **多个参数**：mybatis会做特殊处理。
>
> - 封装成 一个map<key[**参数的索引**arg0...argN],value[**传入的参数值**]>。
>
> - **使用方式**：#{arg0/param1}~#{arg100/param100}：取出参数值
>
> - ---
>
> - **命名参数**：为参数使用@Param起一个名字，MyBatis就会将这些参数封装进map中，key就是我们自己指定的名字
>
> - **使用方式**：#{@Param里的名|param1}：取出参数值
>
> - ---
>
> - **POJO**：如果多个参数正好是我们业务逻辑的数据模型，我们就可以直接传入pojo；
>
> - **使用方式**：#{属性名}：取出传入的pojo的属性值	
>
> - ---
>
> - **Map**：如果多个参数不是业务模型中的数据，没有对应的pojo，不经常使用，为了方便，我们也可以传入map
>
> - **使用方式**：#{key}：取出map中对应的值
>
> - ---
>
> - **Collection**类型或者**数组**：也会特殊处理。也是把传入的list或者数组封装在map中。
>
> - **使用方式**：
>
>   - key：Collection（collection）|  List (list) |  数组 (array)  #{collection|list|array}
>   - 不行就这个@Param("ids") 指定什么是什么。 #{ids}
>
> - 源码分析:
>
>   - ```.
>     就是我通过代理进入invoke方法然后判断你是不是Object中声明的方法如果是就放行了 
>     如果不是在通过一个代理判断你是增删改查哪一个 
>     反正最后都走一个convertArgsToSqlCommandParam给你转成map。
>     ```

# resultType

> 配置结果类型。别名或者全类名
>
> 如果返回的是集合，定义集合中元素的类型。mybatis自动帮你封装。
>
> ---
>
> 想实现这种怎么弄呢？
>
> ```java
> //我想把id作为map的key 实体作为map的value
> public Map<Integer, Employee> getEmpByLastNameLikeReturnMap(String lastName);
> 
> //1.@MapKey("id") 告诉mybatis封装这个map的时候使用哪个属性作为map的key
> //dao
> @MapKey("id")
> public Map<String, Employee> getEmpByLastNameLikeReturnMap(String lastName);
> 
> //2.xml
> <!--public Map<Integer, Employee> getEmpByLastNameLikeReturnMap(String lastName);  -->
> <select id="getEmpByLastNameLikeReturnMap" 		 resultType="com.luftraveler.mybatis.mapper.bean.Employee">
>  		select * from tbl_employee where last_name like #{lastName}
> </select>
>     
> //3.测试
> Map<String, Employee> map2 = mapper.getEmpByLastNameLikeReturnMap("%召%");
> System.out.println(map2);
> ```

# resultMap

> - resultMap标签用于建立查询的列名和实体属性名称的对应关系。
>
> - 在select标签中使用resultMap属性指定引用即可。
>
> - 同时resultMap可以实现将查询结果映射为复杂类型的pojo，比如一对一,一对多,多对多。延时加载等等..
>
> - ```xml
>   <!--
>   	自定义某个javaBean的封装规则:
>   		type：自定义规则的Java类型
>   		id:唯一id方便引用
>   -->
>   <resultMap type="com.luftraveler.mybatis.mapper.bean.Employee" id="MySimpleEmp">
>       <!--
>      		指定主键列的封装规则:
>       		id定义主键会底层有优化；
>       		column：指定哪一列
>       		property：指定对应的javaBean属性
>       -->
>       <id column="id" property="id"/>
>       
>       <!-- 定义普通列封装规则 -->
>       <result column="last_name" property="lastName"/>
>       <!-- 其他不指定的列会自动封装：我们只要写resultMap就把全部的映射规则都写上。 -->
>       <result column="email" property="email"/>
>       <result column="gender" property="gender"/>
>       <result column="create_time" property="createTime"/>
>   </resultMap>
>   
>   	<!-- resultMap:自定义结果集映射规则；  -->
>   	<!-- public Employee getEmpById(Integer id); -->
>   	<select id="getEmpById"  resultMap="MySimpleEmp">
>   		select * from tbl_employee where id=#{id}
>   	</select>
>   ```

# 关联查询:一对一

> 在resultMap中添加标签association,属性property对应pojo中的属性,javaType对应属性的类型

## **方式一：**

> - sql
>
>   - ```sql
>     SELECT
>     	e.id id,e.last_name last_name,e.gender gender,e.dept_id d_id,
>     	d.id did,d.dept_name dept_name
>     FROM tbl_employee e,tbl_dept d
>     WHERE e.dept_id=d.id
>     ```
>
>   - ```sql
>     SELECT
>       e.id id,
>       e.last_name last_name,
>       e.gender gender,
>       e.dept_id d_id,
>       d.id did,
>       d.dept_name dept_name
>     FROM tbl_employee e
>     LEFT JOIN tbl_dept d ON d.id = e.dept_id
>     ```
>
> - 实体
>
>   - ```java
>     public class Employee {
>        private Integer id;
>        private String lastName;
>        private String email;
>        private String gender;
>        private LocalDateTime createTime;
>        private Department dept;
>     }
>     ```
>
>   - ```java
>     public class Department {
>        private Integer id;
>        private String departmentName;
>        private List<Employee> emps;
>     }
>     ```
>
> - dao
>
>   - ```java
>     public Department getDeptByIdStep(Integer id);
>     ```
>
> - xml
>
>   - ```xml
>     <!--联合查询：级联属性封装结果集-->
>     <resultMap type="com.luftraveler.mybatis.mapper.bean.Employee" id="MyDifEmp">
>        <id column="id" property="id"/>
>        <result column="last_name" property="lastName"/>
>        <result column="gender" property="gender"/>
>        <result column="did" property="dept.id"/>
>        <result column="dept_name" property="dept.departmentName"/>
>     </resultMap>
>     
>     <!--  public Employee getEmpAndDept();-->
>     <select id="getEmpAndDept" resultMap="MyDifEmp">
>     		SELECT
>     			e.id id,e.last_name last_name,e.gender gender,e.dept_id d_id,
>     			d.id did,d.dept_name dept_name
>     		FROM tbl_employee e,tbl_dept d
>     		WHERE e.dept_id=d.id
>     </select>
>     ```
>
> - test
>
>   - ```java
>     @Test
>     public void test05() throws IOException {
>         SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
>         SqlSession openSession = sqlSessionFactory.openSession();
>         try {
>             List<Employee> empAndDept = mapper.getEmpAndDept();
>             for (Employee employee : empAndDept) {
>                 System.out.println(employee);
>             }
>             System.out.println("======================================");
>         } finally {
>            openSession.close();
>         }
>     }
>     ```

---

## **方式二：association定义关联**

> - sql
>
>   - ```sql
>     SELECT
>     	e.id id,e.last_name last_name,
>     	e.gender gender,e.dept_id d_id,
>     	d.id did,d.dept_name dept_name
>     FROM tbl_employee e
>     LEFT JOIN tbl_dept d ON d.id = e.dept_id
>     ```
>
> - 实体
>
>   - ```java
>     public class Employee {
>        private Integer id;
>        private String lastName;
>        private String email;
>        private String gender;
>        private LocalDateTime createTime;
>        private Department dept;
>     }
>     ```
>
>   - ```java
>     public class Department {
>        private Integer id;
>        private String departmentName;
>        private List<Employee> emps;
>     }
>     ```
>
> - dao
>
>   - ```java
>     public  List<Employee> getEmpAndDeptAssociation();
>     ```
>
> - xml
>
>   - ```xml
>     <!--使用association定义关联的单个对象的封装规则；-->
>     <resultMap type="com.luftraveler.mybatis.mapper.bean.Employee" id="MyDifEmp2">
>     	<id column="id" property="id"/>
>     	<result column="last_name" property="lastName"/>
>     	<result column="gender" property="gender"/>
>     	<!--  association可以指定联合的javaBean对象
>     				property="dept"：指定哪个属性是联合的对象
>     				javaType:指定这个属性对象的类型[不能省略]-->
>     	<association property="dept" javaType="com.luftraveler.mybatis.mapper.bean.Department">
>     		<id column="did" property="id"/>
>     		<result column="dept_name" property="departmentName"/>
>     	 </association>
>     </resultMap>
>     
>     	<!--  public Employee getEmpAndDept();-->
>     <select id="getEmpAndDeptAssociation" resultMap="MyDifEmp">
>     	SELECT
>     		e.id id,e.last_name last_name,
>     		e.gender gender,e.dept_id d_id,
>     		d.id did,d.dept_name dept_name
>     	FROM tbl_employee e
>     	LEFT JOIN tbl_dept d ON d.id = e.dept_id
>     </select>
>     ```
>
> - test
>
>   - ```java
>     @Test
>     public void test05() throws IOException {
>         SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
>         SqlSession openSession = sqlSessionFactory.openSession();
>         try {
>             List<Employee> empAndDept = mapper.getEmpAndDeptAssociation();
>             for (Employee employee : empAndDept) {
>                 System.out.println(employee);
>             }
>             System.out.println("======================================");
>         } finally {
>            openSession.close();
>         }
>     }
>     ```

---

## 方式三：分段加载

> - sql
>
>   - ```sql
>     //EmployeeMapperPlus.xml
>     select * from tbl_employee where id=#{id}
>     ```
>
>   - ```sql
>     //DepartmentMapper.xml
>     select id,dept_name departmentName from tbl_dept where id=#{id}
>     ```
>
> - 实体
>
>   - ```java
>     public class Employee {
>        private Integer id;
>        private String lastName;
>        private String email;
>        private String gender;
>        private LocalDateTime createTime;
>        private Department dept;
>     }
>     ```
>
>   - ```java
>     public class Department {
>        private Integer id;
>        private String departmentName;
>        private List<Employee> emps;
>     }
>     ```
>
> - dao
>
>   - ```java
>     public Department getEmpByIdStep(Integer id);
>     ```
>
> - xml
>
>   - ```xml
>     //EmployeeMapperPlus.xml
>     <!-- 使用association进行分步查询：
>     		1、先按照员工id查询员工信息
>     		2、根据查询员工信息中的d_id值去部门表查出部门信息
>     		3、部门设置到员工中；
>     -->
>     <resultMap type="com.luftraveler.mybatis.mapper.bean.Employee" id="MyEmpByStep">
>     	<id column="id" property="id"/>
>     	<result column="last_name" property="lastName"/>
>     	<result column="email" property="email"/>
>     	<result column="gender" property="gender"/>
>     	<!-- association定义关联对象的封装规则
>     	 	  select:表明当前属性是调用select指定的方法查出的结果
>     	 	  column:指定将哪一列的值传给这个方法
>     	 	  流程：使用select指定的方法（传入column指定的这列参数的值）查出对象，
>     		  并封装给property指定的属性-->
>      	<association property="dept"
>     	 select="com.luftraveler.mybatis.mapper.dao.DepartmentMapper.getDeptById"
>     	 		column="dept_id">
>      	</association>
>     </resultMap>
>     <!--  public Employee getEmpByIdStep(Integer id);-->
>     <select id="getEmpByIdStep" resultMap="MyEmpByStep">
>     	select * from tbl_employee where id=#{id}
>     </select>
>     
>     //DepartmentMapper.xml
>     <!--public Department getDeptById(Integer id);  -->
>     <select id="getDeptById" resultType="com.luftraveler.mybatis.mapper.bean.Department">
>     		select id,dept_name departmentName from tbl_dept where id=#{id}
>     </select>
>     ```
>
> - test

---

## **方式四：延时加载**

> - sql
>
>   - ```sql
>     //EmployeeMapperPlus.xml
>     select * from tbl_employee where id=#{id}
>     ```
>
>   - ```sql
>     //DepartmentMapper.xml
>     select id,dept_name departmentName from tbl_dept where id=#{id}
>     ```
>
> - 实体
>
>   - ```java
>     public class Employee {
>        private Integer id;
>        private String lastName;
>        private String email;
>        private String gender;
>        private LocalDateTime createTime;
>        private Department dept;
>     }
>     ```
>
>   - ```java
>     public class Department {
>        private Integer id;
>        private String departmentName;
>        private List<Employee> emps;
>     }
>     ```
>
> - dao
>
>   - ```java
>     public Department getEmpByIdStep(Integer id);
>     ```
>
> - xml
>
>   - ```xml
>     //EmployeeMapperPlus.xml
>     <!-- 使用association进行分步查询：
>     		1、先按照员工id查询员工信息
>     		2、根据查询员工信息中的d_id值去部门表查出部门信息
>     		3、部门设置到员工中；
>     -->
>     <resultMap type="com.luftraveler.mybatis.mapper.bean.Employee" id="MyEmpByStep">
>     	<id column="id" property="id"/>
>     	<result column="last_name" property="lastName"/>
>     	<result column="email" property="email"/>
>     	<result column="gender" property="gender"/>
>     	<!-- association定义关联对象的封装规则
>     	 	  select:表明当前属性是调用select指定的方法查出的结果
>     	 	  column:指定将哪一列的值传给这个方法
>     	 	  流程：使用select指定的方法（传入column指定的这列参数的值）查出对象，
>     		  并封装给property指定的属性-->
>      	<association property="dept"
>     	 select="com.luftraveler.mybatis.mapper.dao.DepartmentMapper.getDeptById"
>     	 		column="dept_id">
>      	</association>
>     </resultMap>
>     <!--  public Employee getEmpByIdStep(Integer id);-->
>     <select id="getEmpByIdStep" resultMap="MyEmpByStep">
>     	select * from tbl_employee where id=#{id}
>     </select>
>     
>     	 <!-- 可以使用延迟加载（懒加载）；(按需加载)
>     	 	Employee==>Dept：
>     	 		我们每次查询Employee对象的时候，都将一起查询出来。
>     	 		部门信息在我们使用的时候再去查询；
>     	 		分段查询的基础之上加上两个配置：
>     	  -->
>     
>     //DepartmentMapper.xml
>     <!--public Department getDeptById(Integer id);  -->
>     <select id="getDeptById" resultType="com.luftraveler.mybatis.mapper.bean.Department">
>     		select id,dept_name departmentName from tbl_dept where id=#{id}
>     </select>
>     ```
>
> - mybatis-config.xml 开启延迟加载和属性按需加载
>
>   - ```xml
>     <settings>
>     		<setting name="lazyLoadingEnabled" value="true"/>
>     		<setting name="aggressiveLazyLoading" value="false"/>
>     </settings>
>     ```



# 关联查询:一对多

> 在resultMap中添加标签collection,属性property对应pojo中的属性,ofType对应属性的类型也就是list中对象

```java
<resultMap type="user" id="userMap">
    <id column="id" property="id"></id>
    <result column="username" property="username"/>
    <result column="address" property="address"/>
    <result column="sex" property="sex"/>
    <result column="birthday" property="birthday"/>
    <!-- collection:
    	部分定义了用户关联的账户信息。表示关联查询结果集
		property="accList":
		关联查询的结果集存储在 User 对象的上哪个属性。
		ofType="account" :指定关联查询的结果集中的对象类型即List中的对象类型。
		此处可以使用别名，也可以使用全限定名。
    -->
    <collection property="accounts" ofType="account">
        <id column="aid" property="id"/>
        <result column="uid" property="uid"/>
        <result column="money" property="money"/>
    </collection>
</resultMap>
<!-- 配置查询所有操作 -->
<select id="findAll" resultMap="userMap">
    select u.*,a.id as aid ,a.uid,a.money 
    from user u 
    left outer join account a on u.id =a.uid
</select>
```

# 延时加载

> 延时加载就是把联表的sql语句进行拆分，在我们调用的时候发送sql
>
> <settings>
>		<setting name="lazyLoadingEnabled" value="true"/>
> 		<setting name="aggressiveLazyLoading" value="false"/>
></settings>

# 鉴别器

> 就是swicth 根据数据库字段返回的值执行不同的结果
>
> ```xml
> <!-- =======================鉴别器============================ -->
> <!-- <discriminator javaType=""></discriminator>
>    鉴别器：mybatis可以使用discriminator判断某列的值，然后根据某列的值改变封装行为
>    封装Employee：
>       如果查出的是女生：就把部门信息查询出来，否则不查询；
>       如果是男生，把last_name这一列的值赋值给email;
>  -->
>  <resultMap type="com.luftraveler.mybatis.mapper.bean.Employee" id="MyEmpDis">
>    <id column="id" property="id"/>
>    <result column="last_name" property="lastName"/>
>    <result column="email" property="email"/>
>    <result column="gender" property="gender"/>
>    <!--
>       column：指定判定的列名
>       javaType：列值对应的java类型  -->
>    <discriminator javaType="string" column="gender">
>       <!--女生  resultType:指定封装的结果类型；不能缺少。/resultMap-->
>       <case value="0" resultType="com.luftraveler.mybatis.mapper.bean.Employee">
>          <association property="dept"
>             select="com.luftraveler.mybatis.mapper.dao.DepartmentMapper.getDeptById"
>             column="d_id">
>          </association>
>       </case>
>       <!--男生 ;如果是男生，把last_name这一列的值赋值给email; -->
>       <case value="1" resultType="com.luftraveler.mybatis.mapper.bean.Employee">
>          <id column="id" property="id"/>
>          <result column="last_name" property="lastName"/>
>          <result column="last_name" property="email"/>
>          <result column="gender" property="gender"/>
>       </case>
>    </discriminator>
>  </resultMap>
> ```

# Mybatis动态sql有什么用?执行原理?有哪些动态sql?

> 动态拼接sql(trim,where,set,foreach,if,choose,when,otherwise,bind)
>
> 使用OGNL从sql参数对象中计算表达式的值，根据表达式的值动态拼接sql，以此来完成动态sql的功能

# 除了select|insert|update|delete标签,还有哪些标签？

> - <resultMap> 封装返回结果与实体映射
> - <sql> 为sql片段标签
> - <include> 引入sql片段

# 动态sql->if 条件分支

```xml
<!-- 查询员工，要求，携带了哪个字段查询条件就带上这个字段的值 -->
<!-- public List<Employee> getEmpsByConditionIf(Employee employee); -->
<select id="getEmpsByConditionIf" resultType="com.luftraveler.mybatis.DynamicSQL.bean.Employee">
   select * from tbl_employee
   <!-- where -->
   <where>
       <!-- test：判断表达式（OGNL）
       OGNL参照PPT或者官方文档。
           c:if  test
       从参数中取值进行判断

       遇见特殊符号应该去写转义字符：
       &&：
       -->
       <if test="id!=null">
          id=#{id}
       </if>
       <if test="lastName!=null &amp;&amp; lastName!=&quot;&quot;">
          and last_name like #{lastName}
       </if>
       <if test="email!=null and email.trim()!=&quot;&quot;">
          and email=#{email}
       </if>
       <!-- ognl会进行字符串与数字的转换判断  "0"==0 -->
       <if test="gender==0 or gender==1">
          and gender=#{gender}
       </if>
   </where>
</select>
```

# 动态sql->trim (where, set) 字符处理

> where(封装查询条件), set(封装修改条件)

```xml
<!--public List<Employee> getEmpsByConditionTrim(Employee employee);  -->
<select id="getEmpsByConditionTrim" resultType="com.luftraveler.mybatis.DynamicSQL.bean.Employee">
   select * from tbl_employee
   <!-- 后面多出的and或者or where标签不能解决
   prefix="":前缀：trim标签体中是整个字符串拼串 后的结果。
         prefix给拼串后的整个字符串加一个前缀
   prefixOverrides="":
         前缀覆盖： 去掉整个字符串前面多余的字符
   suffix="":后缀
         suffix给拼串后的整个字符串加一个后缀
   suffixOverrides=""
         后缀覆盖：去掉整个字符串后面多余的字符

   -->
   <!-- 自定义字符串的截取规则 -->
   <trim prefix="where" suffixOverrides="and">
      <if test="id!=null">
          id=#{id} and
      </if>
      <if test="lastName!=null &amp;&amp; lastName!=&quot;&quot;">
          last_name like #{lastName} and
      </if>
      <if test="email!=null and email.trim()!=&quot;&quot;">
          email=#{email} and
      </if>
       <!-- ognl会进行字符串与数字的转换判断  "0"==0 -->
      <if test="gender==0 or gender==1">
          gender=#{gender}
      </if>
   </trim>
</select>
```

# 动态sql->set 修改

```xml
<!--public void updateEmp(Employee employee);  -->
 <update id="updateEmp">
     <!-- Set标签的使用 -->
     update tbl_employee
     <set>
         <if test="lastName!=null">
             last_name=#{lastName},
         </if>
         <if test="email!=null">
             email=#{email},
         </if>
         <if test="gender!=null">
             gender=#{gender}
         </if>
     </set>
     where id=#{id}
<!--
      Trim：更新拼串
      update tbl_employee
      <trim prefix="set" suffixOverrides=",">
         <if test="lastName!=null">
            last_name=#{lastName},
         </if>
         <if test="email!=null">
            email=#{email},
         </if>
         <if test="gender!=null">
            gender=#{gender}
         </if>
      </trim>
      where id=#{id}  -->
</update>
```

# 动态sql->choose (when, otherwise)分支选择

> choose (when, otherwise):分支选择；带了break的swtich-case。
> 如果带了id就用id查，如果带了lastName就用lastName查;只会进入其中一个

```xml
<!-- public List<Employee> getEmpsByConditionChoose(Employee employee); -->
<select id="getEmpsByConditionChoose" resultType="com.luftraveler.mybatis.DynamicSQL.bean.Employee">
   select * from tbl_employee
   <where>
      <!-- 如果带了id就用id查，如果带了lastName就用lastName查;只会进入其中一个 -->
      <choose>
         <when test="id!=null">
            id=#{id}
         </when>
         <when test="lastName!=null">
            last_name like #{lastName}
         </when>
         <when test="email!=null">
            email = #{email}
         </when>
         <otherwise>
            gender = 0
         </otherwise>
      </choose>
   </where>
</select>
```

# 动态sql->foreach 循环

```xml
<!--public List<Employee> getEmpsByConditionForeach(List<Integer> ids);  -->
 <select id="getEmpsByConditionForeach" resultType="com.luftraveler.mybatis.DynamicSQL.bean.Employee">
   select * from tbl_employee
   <!--
      collection：指定要遍历的集合：
         list类型的参数会特殊处理封装在map中，map的key就叫list
      item：将当前遍历出的元素赋值给指定的变量
      separator:每个元素之间的分隔符
      open：遍历出所有结果拼接一个开始的字符
      close:遍历出所有结果拼接一个结束的字符
      index:索引。遍历list的时候是index就是索引，item就是当前值
                  遍历map的时候index表示的就是map的key，item就是map的值

      #{变量名}就能取出变量的值也就是当前遍历出的元素
     -->
   <foreach collection="ids" item="item_id" separator=","
      open="where id in(" close=")">
      #{item_id}
   </foreach>
 </select>

 <!-- 批量保存 -->
 <!--public void addEmps(@Param("emps") List<Employee> emps);  -->
 <!--MySQL下批量保存：可以foreach遍历   mysql支持values(),(),()语法-->
<insert id="addEmps">
   insert into tbl_employee(
      <include refid="insertColumn"></include>
   )
   values
   <foreach collection="emps" item="emp" separator=",">
      (#{emp.lastName},#{emp.email},#{emp.gender},#{emp.dept.id})
   </foreach>
 </insert>


```

# 动态sql->bind 值绑定到一个变量中

```xml
<!--public List<Employee> getEmpsTestInnerParameter(Employee employee);  -->
<select id="getEmpsTestInnerParameter" resultType="com.luftraveler.mybatis.DynamicSQL.bean.Employee">
      <!-- bind：可以将OGNL表达式的值绑定到一个变量中，方便后来引用这个变量的值 -->
      <bind name="_lastName" value="'%'+lastName+'%'"/>
      <if test="_databaseId=='mysql'">
         select * from tbl_employee
         <if test="_parameter!=null">
            where last_name like #{lastName}
         </if>
      </if>
      <if test="_databaseId=='oracle'">
         select * from employees
         <if test="_parameter!=null">
            where last_name like #{_parameter.lastName}
         </if>
      </if>
</select>
```

# 标签< sql >

```xml
<!--
   抽取可重用的sql片段。方便后面引用
   1、sql抽取：经常将要查询的列名，或者插入用的列名抽取出来方便引用
   2、include来引用已经抽取的sql：
   3、include还可以自定义一些property，sql标签内部就能使用自定义的属性
         include-property：取值的正确方式${prop},
         #{不能使用这种方式}
-->
<sql id="insertColumn">
      <if test="_databaseId=='oracle'">
         employee_id,last_name,email
      </if>
      <if test="_databaseId=='mysql'">
         last_name,email,gender,d_id
      </if>
</sql>
```

# 通常一个Xml映射文件,都会写一个Dao接口与之对应,请问，这个Dao接口的工作原理是什么?Dao接口里的方法,参数不同时,方法能重载吗？

> Mapper接口里的方法，是不能重载的，因为是使用 全限名+方法名 的保存和寻找策略。
>
> Mapper 接口的工作原理是JDK动态代理。
>
> Mybatis运行时会使用JDK动态代理为Mapper接口生成代理对象proxy。
>
> 代理对象会拦截接口方法。
>
> 转而执行MapperStatement.get(全限名+方法名)查询所代表的sql。
>
> 以及resultType和resultMap。
>
> 然后将sql执行结果返回。
>
> 注意：在以前的Mybatis版本的namespace是可选的，不过新版本的namespace已经是必须的了。

# Mybatis是如何进行分页的？分页插件的原理是什么？

Mybatis使用RowBounds对象进行分页，它是针对ResultSet结果集执行的内存分页，而非物理分页。可以在sql内直接书写带有物理分页的参数来完成物理分页功能，也可以使用分页插件来完成物理分页。

分页插件的基本原理是使用Mybatis提供的插件接口，实现自定义插件，在插件的拦截方法内拦截待执行的sql，然后重写sql，根据dialect方言，添加对应的物理分页语句和物理分页参数。

# Mybatis是如何将sql执行结果封装为目标对象并返回的？都有哪些映射形式？

> 第一种是使用<resultMap>标签，逐一定义数据库列名和对象属性名之间的映射关系。
>
> 第二种是使用sql列的别名功能，将列的别名书写为对象属性名。
>
> 有了列名与属性名的映射关系后，Mybatis通过反射创建对象，
>
> 同时使用反射给对象的属性逐一赋值并返回，
>
> 那些找不到映射关系的属性，是无法完成赋值的。

# 如何执行批量插入?

首先,创建一个简单的insert语句:

<insert id="insertname">insert into names (name) values (#{value}) </insert>

然后在java代码中像下面这样执行批处理插入：

list<string> names = new arraylist();

names.add(“fred”); 

names.add(“barney”); 

names.add(“betty”); 

names.add(“wilma”); 

// 注意这里 executortype.batch sqlsession sqlsession = sqlsessionfactory.opensession(executortype.batch); try { namemapper mapper = sqlsession.getmapper(namemapper.class); for (string name : names) { mapper.insertname(name); } sqlsession.commit(); }catch(Exception e){ e.printStackTrace(); sqlSession.rollback(); throw e; } finally { sqlsession.close(); }



# Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？

> 一对一(association)关联查询以及一对多(collection)关联查询查询,可以配置是否启用延迟加载。
>
> <settings>
> 		<setting name="lazyLoadingEnabled" value="true"/>
> 		<setting name="aggressiveLazyLoading" value="false"/>
> </settings>
>
> 它的原理是，使用CGLIB创建目标对象的代理对象，当调用目标方法时，进入拦截器方法。
>
> 比如调用a.getB().getName()，拦截器invoke()方法发现a.getB()是null值。
>
> 那么就会单独发送事先保存好的查询关联B对象的sql，把B查询上来。
>
> 然后调用a.setB(b)，于是a的对象b属性就有值了，接着完成a.getB().getName()方法的调用。
>
> 基本原理可能就这样 没细看大概扫了一眼。
>
> 当然了，不光是Mybatis，几乎所有的包括Hibernate，支持延迟加载的原理都是一样的。

# 什么是MyBatis的接口绑定？有哪些实现方式？

> 接口绑定，就是在MyBatis中任意定义接口,然后把接口里面的方法和SQL语句绑定, 
>
> 我们直接调用接口方法就可以,这样比起原来了SqlSession提供的方法我们可以有更加灵活的选择和设置。
>
> 接口绑定有两种实现方式,一种是通过注解绑定，
>
> 就是在接口的方法上面加上 @Select、@Update等注解，里面包含Sql语句来绑定；
>
> 另外一种就是通过xml里面写SQL来绑定, 在这种情况下,
>
> 要指定xml映射文件里面的namespace必须为接口的全路径名。
>
> 当Sql语句比较简单时候,用注解绑定, 当SQL语句比较复杂时候,用xml绑定,一般用xml绑定的比较多。

# 使用MyBatis的mapper接口调用时有哪些要求？

> ①  Mapper接口方法名和mapper.xml中定义的每个sql的id相同；
> ②  Mapper接口方法的输入参数类型和mapper.xml中定义的每个sql 的parameterType的类型相同；
> ③  Mapper接口方法的输出参数类型和mapper.xml中定义的每个sql的resultType的类型相同；
> ④  Mapper.xml文件中的namespace即是mapper接口的类路径。

# Mybatis执行批量插入,能返回数据库主键列表吗？

没试过

# 如何获取自动生成的(主)键值?

> > mysql 在insert标签中添加属性。
> >
> > **使用自增主键获取主键值策略**[useGeneratedKeys="true"] 。
> >
> > **指定对应的主键属性 **[keyProperty="id"] 你要封装到那个属性。
> >
> > 对象.getId()就可以了。
> >
> > oracle 忘了 不会 不总用
>
> ```java
> /**
>   * parameterType：参数类型，可以省略，
>   * 获取自增主键的值：
>   *    mysql支持自增主键，自增主键值的获取，mybatis也是利用statement.getGenreatedKeys()；
>   *    useGeneratedKeys="true"；使用自增主键获取主键值策略
>   *	   keyProperty；指定对应的主键属性，也就是mybatis获取到主键值以后，将这个值封装给javaBean的哪个属性
>   *    databaseId:厂商编号
>   */
> <insert id="addEmp" parameterType="com.luftraveler.mybatis.mapper.bean.Employee"
> 			useGeneratedKeys="true" keyProperty="id" databaseId="mysql">
> 	insert into tbl_employee(last_name,email,gender) values(#{lastName},#{email},#{gender})
> </insert>
>     
>     
> //获取主键id
> EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
> Employee em = new Employee(null,"第33333个","11111@qq.com","111");
> mapper.addEmp(em);
> System.out.println(em);
> ```

# Mybatis中如何执行批处理？

答：使用BatchExecutor完成批处理。

# Mybatis都有哪些Executor执行器？它们之间的区别是什么？

答：Mybatis有三种基本的Executor执行器，**SimpleExecutor、ReuseExecutor、BatchExecutor。**

**SimpleExecutor：**每执行一次update或select，就开启一个Statement对象，用完立刻关闭Statement对象。

**ReuseExecutor：**执行update或select，以sql作为key查找Statement对象，存在就使用，不存在就创建，用完后，不关闭Statement对象，而是放置于Map<String, Statement>内，供下一次使用。简言之，就是重复使用Statement对象。

**BatchExecutor：**执行update（没有select，JDBC批处理不支持select），将所有sql都添加到批处理中（addBatch()），等待统一执行（executeBatch()），它缓存了多个Statement对象，每个Statement对象都是addBatch()完毕后，等待逐一执行executeBatch()批处理。与JDBC批处理相同。

作用范围：Executor的这些特点，都严格限制在SqlSession生命周期范围内。

# Mybatis中如何指定使用哪一种Executor执行器？

答：在Mybatis配置文件中，可以指定默认的ExecutorType执行器类型，也可以手动给DefaultSqlSessionFactory的创建SqlSession的方法传递ExecutorType类型参数。

# Mybatis是否可以映射Enum枚举类？

答：Mybatis可以映射枚举类，不单可以映射枚举类，Mybatis可以映射任何对象到表的一列上。映射方式为自定义一个TypeHandler，实现TypeHandler的setParameter()和getResult()接口方法。TypeHandler有两个作用，一是完成从javaType至jdbcType的转换，二是完成jdbcType至javaType的转换，体现为setParameter()和getResult()两个方法，分别代表设置sql问号占位符参数和获取列查询结果。

# Mybatis映射文件中，如果A标签通过include引用了B标签的内容，请问，B标签能否定义在A标签的后面，还是说必须定义在A标签的前面

答：虽然Mybatis解析Xml映射文件是按照顺序解析的，但是，被引用的B标签依然可以定义在任何地方，Mybatis都可以正确识别。

原理是，Mybatis解析A标签，发现A标签引用了B标签，但是B标签尚未解析到，尚不存在，此时，Mybatis会将A标签标记为未解析状态，然后继续解析余下的标签，包含B标签，待所有标签解析完毕，Mybatis会重新解析那些被标记为未解析的标签，此时再解析A标签时，B标签已经存在，A标签也就可以正常解析完成了。

# 简述Mybatis的Xml映射文件和Mybatis内部数据结构之间的映射关系？

答：Mybatis将所有Xml配置信息都封装到All-In-One重量级对象Configuration内部。在Xml映射文件中，<parameterMap>标签会被解析为ParameterMap对象，其每个子元素会被解析为ParameterMapping对象。<resultMap>标签会被解析为ResultMap对象，其每个子元素会被解析为ResultMapping对象。每一个<select>、<insert>、<update>、<delete>标签均会被解析为MappedStatement对象，标签内的sql会被解析为BoundSql对象。

# 接口绑定有几种实现方式,分别是怎么实现的?

　接口绑定有两种实现方式：

- 一种是通过注解绑定,就是在接口的方法上面加上@Select@Update等注解里面包含Sql语句来绑定
- 另外一种就是通过xml里面写SQL来绑定,在这种情况下,要指定xml映射文件里面的namespace必须为接口的全路径名.

# 简述Mybatis的插件运行原理，以及如何编写一个插件

　　Mybatis仅可以**编写针对ParameterHandler、ResultSetHandler、StatementHandler、Executor这4种接口的插件，Mybatis使用JDK的动态代理，为需要拦截的接口生成代理对象以实现接口方法拦截功能**，每当执行这4种接口对象的方法时，就会进入拦截方法，具体就是InvocationHandler的invoke()方法，当然，只会拦截那些你指定需要拦截的方法。

　　实现Mybatis的Interceptor接口并复写intercept()方法，然后在给插件编写注解，指定要拦截哪一个接口的哪些方法即可，记住，别忘了在配置文件中配置你编写的插件。





# mybatis学习知识点

## 1.typeAliases别名处理器

> 类型别名是为 Java类型设置一个比较短的名字,可以方便我们引用某个类
>
> 其实也就是resultType的返回值

```java
//案例 resultType里面的返回值太长了。
<select id="getEmpById" resultType="com.agzy.day1.bean.Employee">
	select * from tbl_employee where id = #{id}
</select>
```

```java
<configuration>
    <!-- 3、typeAliases：别名处理器：可以为我们的java类型起别名,别名不区分大小写-->
    <typeAliases>
        <!-- 1、typeAlias:为某一个javaBean起别名:{
                  type:指定要起别名的类型全类名;默认别名就是类名小写；employee
                  alias:指定新的别名
                }-->
         <typeAlias type="com.agzy.day1.bean.Employee" alias="emp"/>

        <!-- 2、package:为某个包下的所有类批量起别名{
                  name：指定包名（别名是类名的小写）
                }-->
        <package name="com.agzy.day1.bean"/>
        <!-- 3、批量起别名的情况下，使用@Alias注解为某个类型指定新的别名 -->
        /**
         *  @Alias("emp1111")
		 *  public class Employee {}
		 */
    </typeAliases>
</configuration>
```

> 值得注意的是,MyBatis已经为许多常见的 Java 类型内建了相应的类型别名。
>
> 它们都是大小写不敏感的,我们在起别名的时候千万不要占用已有的别名。

|    别名    | 映射的类型 |
| :--------: | :--------: |
|   _byte    |    byte    |
|   _long    |    long    |
|   _short   |   short    |
|    _int    |    int     |
|  _integer  |    int     |
|  _double   |   double   |
|   _float   |   float    |
|  _boolean  |  boolean   |
|   string   |   String   |
|    byte    |    Byte    |
|    long    |    Long    |
|   short    |   Short    |
|    int     |  Integer   |
|  integer   |  Integer   |
|   double   |   Double   |
|   float    |   Float    |
|  boolean   |  Boolean   |
|    date    |    Date    |
|  decimal   | BigDecimal |
| bigdecimal | BigDecimal |
|   object   |   Object   |
|    map     |    Map     |
|  hashmap   |  HashMap   |
|    list    |    List    |
| arraylist  | ArrayList  |
| collection | Collection |
|  iterator  |  Iterator  |

## 2.typeHandlers自定义类型处理

> 我们可以重写类型处理器或创建自己的类型处理器来处理不支持的或非标准的类型。
>
> <typeHandlers>
>
> ​	<typeHandler handler="java.time.LocalDateTime" />
>
> </typeHandlers>
>
> 上边的案例是在3.4版本之前没做处理的如果我们想使用需要自己注册一个。
>
> 然后我们实体类型就可以是 private LocalDateTime createTime了。
>
> 3.5已经实现了

## 3.plugins插件

>  插件是MyBatis提供的一个非常强大的机制，我们可以通过插件来修改MyBatis的一些核心行为。
>
> 插件通过动态代理机制，可以介入四大对象的任何一个方法的执行。
>
> - **Executor** (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
>   - **执行器** 我们可以再执行查询以及增删改时改变一下mybatis的默认行为达到我们的自定义效果
> - **ParameterHandler** (getParameterObject, setParameters) 
>   - **参数处理器** sql语句预编译时使用他来做，**入参**
> -  **ResultSetHandler** (handleResultSets, handleOutputParameters) 
>   - **结果集处理器**   返回值处理的
> -  **StatementHandler** (prepare, parameterize, batch, update, query)
>   - **sql 语句处理器**

## 4.mybatis可以支持多环境配置

```java
<environments default="dev_mysql">
	<environment id="dev_mysql">
		<transactionManager type="JDBC"></transactionManager>
		<dataSource type="POOLED">
			<property name="driver" value="${jdbc.driver}" />
			<property name="url" value="${jdbc.url}" />
			<property name="username" value="${jdbc.username}" />
			<property name="password" value="${jdbc.password}" />
		</dataSource>
	</environment>
	
	<environment id="dev_oracle">
		<transactionManager type="JDBC" />
		<dataSource type="POOLED">
			<property name="driver" value="${orcl.driver}" />
			<property name="url" value="${orcl.url}" />
			<property name="username" value="${orcl.username}" />
			<property name="password" value="${orcl.password}" />
		</dataSource>
	</environment>
</environments>
```

## 5.mybatis可以根据不同的数据库执行不同的sql

> mysql和orace sql语句不一样所以我们需要根据不同的数据库执行不同的sql语句
>
> 数据库驱动中有个方法可以获取标识 **getDatabaseProductName()**

```xml
<!-- mybatis-config配置 -->
<databaseIdProvider type="DB_VENDOR">
		<!-- 为不同的数据库厂商起别名 -->
		<property name="MySQL" value="mysql"/>
		<property name="Oracle" value="oracle"/>
     	<property name="DB2" value="db2"/>
		<property name="SQL Server" value="sqlserver"/>
</databaseIdProvider>
```

```xml
<!-- mapper具体配置 -->
<!-- mysql -->
<select id="getEmpById" resultType="com.luftraveler.mybatis.config.bean.Employee"
		databaseId="mysql">
		select * from tbl_employee where id = #{id}
</select>
<!-- oracle -->
<select id="getEmpById" resultType="com.luftraveler.mybatis.config.bean.Employee"
		databaseId="oracle">
		select EMPLOYEE_ID id,LAST_NAME	lastName,EMAIL email 
		from employees where EMPLOYEE_ID=#{id}
</select>
```

## 6.mapper映射方式

> - 直接指定xml在resources的地址
>
>   - ```xml
>     <!-- 使用相对于类路径的资源引用 -->
>     <mappers>
>       <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
>       <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
>       <mapper resource="org/mybatis/builder/PostMapper.xml"/>
>     </mappers>
>     ```
>
> - 指定文件盘符或者一个网络路径
>
>   - ```xml
>     <!-- 使用完全限定资源定位符（URL） -->
>     <mappers>
>       <mapper url="file:///var/mappers/AuthorMapper.xml"/>
>       <mapper url="file:///var/mappers/BlogMapper.xml"/>
>       <mapper url="file:///var/mappers/PostMapper.xml"/>
>     </mappers>
>     ```
>
> - 使用mapper接口类的完全限定类名
>
>   - ```xml
>     <!-- 使用映射器接口实现类的完全限定类名 -->
>     <mappers>
>       <mapper class="org.mybatis.builder.AuthorMapper"/>
>       <mapper class="org.mybatis.builder.BlogMapper"/>
>       <mapper class="org.mybatis.builder.PostMapper"/>
>     </mappers>
>     ```
>
> - 指定一个mapper接口的包去批量引入
>
>   - ```xml
>     <!-- 将包内的映射器接口实现全部注册为映射器 -->
>     <mappers>
>       <package name="org.mybatis.builder"/>
>     </mappers>
>     ```
>
> - 这些配置会告诉 MyBatis 去哪里找映射文件

# 执行流程分析

> **①**.通过classLoad类获取配置文件获取流信息。

```java
String resource = "mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);
```

> **②**.根据流信息创建SQLSessionFactory。
>
> - mybatis底层通过JDK的xPath以及w3c的document来解析xml。
> - 首先解析mybatis-config下的configuration节点。
> - 获取全局设置settings，数据库连接，缓存等等一大堆。
> - 解析之后存储到configuration对应属性中。
> - 解析mappers节点。
> - 通过mappers配置中的mapper节点或者package节点得值获取mapper.xml的具体位置。
> - 我一般用批量注册package 的name
> - 解析所有mapper.xml并分别获取把你namespace,自定义返回值,公共sql,什么一些公共配置等等一些。
> - 然后在解析你每一个mapper.xml中的select|insert|update|delete标签。
> - 并把标签的属性resultType,resultMap,id,paramType,sql语句啊等等一些全部解析出来。
> - 把mapper.xml解析之后通过一个addMappedStatement封装成MappedStatement。
> - 然后把MappedStatement封装成一个map
> - key就是你mapper的namespace+每个标签的id用.拼接。
> - value就是你的MappedStatement 一个MappedStatement代表一个增删改查标签
> - 最后返回DefaultSqlSessionFactory

```java
SqlSessionFactory sqlSessionFactory =  new SqlSessionFactoryBuilder().build(inputStream);
```

> ③.获取sqlSession对象
>
> - 根据你返回的SqlSession创建tx(数据库链接事务等等)。
> - 根据execType(全局配置文件中的defaultExecutorType的值)创建Executor对象。
> - 然后把tx(数据库信息传过去)以及sqlsession和参数传递进去
> - Executor对象会创建一个StatementHandler对象（同时也会创建出ParameterHandler和ResultSetHandler）。
> - 调用StatementHandler预编译参数以及设置参数值
> - 使用ParameterHandler来给sql设置参数
> - 调用StatementHandler的增删改查方法
> - ResultSetHandler封装结果
> - 上述四个对象每个创建的时候都有一个interceptorChain.pluginAll(parameterHandler)重新封装一下。
> - 最后返回DefaultSqlSession 里面包含Executor。

```java
SqlSession openSession = sqlSessionFactory.openSession();
```

> ④.获取代理对象
>
> - mybatis 通过JDK动态代理的方式帮你生成一个代理对象。
> - 拿到Mapper接口对应的MapperProxy。
> - 动态代理可以在不修改代理对象代码的基础上,通过扩展代理类,进行一些功能的附加与增强。
> - 通过Proxy.newProxyInstance()来实现。
>   - 参数1：类加载器(Class Loader)
>   - 参数2：需要实现的接口数组
>   - 参数3：实现了InvocationHandler接口的类。
>   - 就是你有个MapperProxy实现了InvocationHandler并重写了invoke方法
>   - 然后你在invoke方法里就能获取参数,方法名什么的。
> - 返回一个代理对象了MapperProxy

```java
EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
```

> ⑤.调用方法实现查询了呗.
>
> - MapperProxy里面有（DefaultSqlSession）。
> - 自然就能获取MappedStatement的map了。
> - 通过动态代理获取根据路径和方法名找到map中的MappedStatement。
> - 取出里面的sql语句什么返回值之类的。
> - 解析你sql语句吧你#{}换成?具体咋写没研究太多了。
> - 然后就是jdbc那一套了PreparedStatement往里封装参数呗。
> - 通过反射机制给你返回值里面扔值。
> - 然后就查询了呗。
> - 然后就关闭什么什么链接什么的我也不知道我猜的。

```
Employee employee = mapper.getEmpById(1);
```



# Cglib和jdk动态代理的区别

## Cglib和jdk动态代理的区别？

> - Jdk动态代理：利用拦截器（必须实现InvocationHandler）加上反射机制生成一个代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理
> - Cglib动态代理：利用ASM框架，对代理对象类生成的class文件加载进来，通过修改其字节码生成子类来处理

## 什么时候用cglib什么时候用jdk动态代理？

> 1、目标对象生成了接口 默认用JDK动态代理
>
> 2、如果目标对象使用了接口，可以强制使用cglib
>
> 3、如果目标对象没有实现接口，必须采用cglib库，Spring会自动在JDK动态代理和cglib之间转换

## JDK动态代理和cglib字节码生成的区别？

> 1、JDK动态代理只能对实现了接口的类生成代理，而不能针对类
>
> 2、Cglib是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法，并覆盖其中方法的增强，但是因为采用的是继承，所以该类或方法最好不要生成final，对于final类或方法，是无法继承的

## Cglib比JDK快？

> 1、cglib底层是ASM字节码生成框架，但是字节码技术生成代理类，在JDL1.6之前比使用java反射的效率要高
>
> 2、在jdk6之后逐步对JDK动态代理进行了优化，在调用次数比较少时效率高于cglib代理效率
>
> 3、只有在大量调用的时候cglib的效率高，但是在1.8的时候JDK的效率已高于cglib
>
> 4、Cglib不能对声明final的方法进行代理，因为cglib是动态生成代理对象，final关键字修饰的类不可变只能被引用不能被修改

## Spring如何选择是用JDK还是cglib？

> 1、当bean实现接口时，会用JDK代理模式
>
> 2、当bean没有实现接口，用cglib实现
>
> 3、可以强制使用cglib（在spring配置中加入<aop:aspectj-autoproxy proxyt-target-class=”true”/>）

## Cglib原理

> 动态生成一个要代理的子类，子类重写要代理的类的所有不是final的方法。在子类中采用方法拦截技术拦截所有的父类方法的调用，顺势织入横切逻辑，它比Java反射的jdk动态代理要快
>
> Cglib是一个强大的、高性能的代码生成包，它被广泛应用在许多AOP框架中，为他们提供方法的拦截
>
> 最底层的是字节码Bytecode,字节码是java为了保证依次运行，可以跨平台使用的一种虚拟指令格式
>
> 在字节码文件之上的是ASM，只是一种直接操作字节码的框架，应用ASM需要对Java字节码、class结构比较熟悉
>
> 位于ASM上面的是Cglib，groovy、beanshell，后来那个种并不是Java体系中的内容是脚本语言，他们通过ASM框架生成字节码变相执行Java代码，在JVM中程序执行不一定非要写java代码，只要能生成java字节码，jvm并不关系字节码的来源
>
> 位于cglib、groovy、beanshell之上的就是hibernate和spring AOP
>
> 最上面的是applications，既具体应用，一般是一个web项目或者本地跑一个程序、
>
> 使用cglib代码对类做代理？
>
> 使用cglib定义不同的拦截策略？
>
> 构造函数不拦截方法
>
> 用MethodInterceptor和Enhancer实现一个动态代理

##  Jdk中的动态代理

> JDK中的动态代理是通过反射类Proxy以及InvocationHandler回调接口实现的，但是JDK中所有要进行动态代理的类必须要实现一个接口，也就是说只能对该类所实现接口中定义的方法进行代理，这在实际编程中有一定的局限性，而且使用反射的效率也不高

##  Cglib实现

> 使用cglib是实现动态代理，不受代理类必须实现接口的限制，因为cglib底层是用ASM框架，使用字节码技术生成代理类，你使用Java反射的效率要高，cglib不能对声明final的方法进行代理，因为cglib原理是动态生成被代理类的子类

## Cglib的第三方库提供的动态代理

```
 1 /**
 2  * 动态代理：
 3  *  特点：字节码随用随创建，随用随加载
 4  *  作用：不修改源码的基础上对方法增强
 5  *  分类：
 6  *      基于接口的动态代理
 7  *      基于子类的动态代理
 8  *  基于子类的动态代理：
 9  *      涉及的类：Enhancer
10  *      提供者：第三方cglib库
11  *  如何创建代理对象：
12  *      使用Enhancer类中的create方法
13  *  创建代理对象的要求：
14  *      被代理类不能是最终类
15  *  newProxyInstance方法的参数：在使用代理时需要转换成指定的对象
16  *      ClassLoader:类加载器
17  *          他是用于加载代理对象字节码的。和被代理对象使用相同的类加载器。固定写法
18  *      Callback：用于提供增强的代码
19  *          他是让我们写如何代理。我们一般写一个该接口的实现类，通常情况加都是匿名内部类，但不是必须的。
20  *          此接口的实现类，是谁用谁写。
21  *          我们一般写的都是该接口的子接口实现类，MethodInterceptor
22  */
23 com.dynamic.cglib.Producer cglibProducer= (com.dynamic.cglib.Producer) Enhancer.create(
24         com.dynamic.cglib.Producer.class,
25         new MethodInterceptor() {
26             /**
27              *  执行被代理对象的任何方法都会经过该方法
28              * @param obj
29              * @param method
30              * @param args
31              *      以上三个参数和基于接口的动态代理中invoke方法的参数是一样的
32              * @param proxy：当前执行方法的代理对象
33              * @return
34              * @throws Throwable
35              */
36             @Override
37             public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
38                 Object returnValue=null;
39                 Float money=(Float)args[0];
40                 if("saleProduct".equals(method.getName())){
41                    returnValue= method.invoke(producer,money*0.8f);
42                 }
43                 return returnValue;
44             }
45         }
46 );
47 cglibProducer.saleProduct(100.0f);
```

## JDK本身提供的动态代理实现

```
 1 /**
 2          * 动态代理：
 3          *  特点：字节码随用随创建，随用随加载
 4          *  作用：不修改源码的基础上对方法增强
 5          *  分类：
 6          *      基于接口的动态代理
 7          *      基于子类的动态代理
 8          *  基于接口的动态代理：
 9          *      涉及的类：proxy
10          *      提供者：Jdk官方
11          *  如何创建代理对象：
12          *      使用Proxy类中的newProxyInstance方法
13          *  创建代理对象的要求：
14          *      被代理类最少实现一个接口，如果没有则不能使用
15          *  newProxyInstance方法的参数：在使用代理时需要转换成指定的对象
16          *      ClassLoader:类加载器
17          *          他是用于加载代理对象字节码的。和被代理对象使用相同的类加载器。固定写法
18          *      Class[]：字节码数组
19          *          它是用于让代理对象和被代理对象有相同方法。固定写法
20          *      InvocationHandler：用于提供增强的代码
21          *          他是让我们写如何代理。我们一般写一个该接口的实现类，通常情况加都是匿名内部类，但不是必须的。
22          *          此接口的实现类，是谁用谁写。
23          */
24        IProducer proxyProducer=  (IProducer) Proxy.newProxyInstance(
25                 producer.getClass().getClassLoader(),
26                 producer.getClass().getInterfaces(),
27 
28                new InvocationHandler() {
29                    /**
30                     * 作用：执行被代理对象的任何接口方法都会经过该方法
31                     * @param proxy  代理对象的引用
32                     * @param method 当前执行的方法
33                     * @param args   当前执行方法所需的参数
34                     * @return       和被代理对象有相同返回值
35                     * @throws Throwable
36                     */
37                     @Override
38                     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
39 //                        提供增强的代码
40 //                        1、获取方法执行的参数
41                         Object returnValue=null;
42                         Float money=(Float)args[0];
43                         if("saleProduct".equals(method.getName())){
44                            returnValue= method.invoke(producer,money*0.8f);
45                         }
46                         return returnValue;
47                     }
48                 }
49         );
```

## JDK和Cglib的区别：

|                  | Cglib                                                        | JDK                                                          |
| ---------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 是否提供子类代理 | 是                                                           | 否                                                           |
| 是否提供接口代理 | 是（可强制）                                                 | 是                                                           |
| 区别             | 必须依赖于CGLib的类库，但是它需要类来实现任何接口代理的是指定的类生成一个子类，覆盖其中的方法 | 实现InvocationHandler 使用Proxy.newProxyInstance产生代理对象 被代理的对象必须要实现接口 |