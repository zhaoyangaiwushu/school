# ========SQL=========

# 1.select查看

> - 基本语法: [**select  查询列表  from  表名;**]
>
> - 查询列表可以是：表中的字段、常量值、表达式、函数
>
> - 查询的结果是一个虚拟的表格
>
> - 方式一:按字段查询
>
>   - ```
>     SELECT last_name,department_id FROM employees	
>     ```
>
> - 方式二:查询全部
>
>   - ```sql
>     SELECT * FROM employees;
>     ```
>
> - 方式三:查询常量值:
>
>   - ```sql
>     SELECT 100; SELECT 'john';
>     ```
>
> - 方式四:查询表达式:
>
>   - ```sql
>     SELECT 100%98;
>     ```
>
> - 方式五:查询函数:
>
>   - ```sql
>     SELECT VERSION(); --查看数据库版本
>     ```

# 2.别名(as)

> - 方式一：使用as
>
>   - ```sql
>     SELECT 100%98 AS 结果;
>     ```
>
> - 方式二：使用空格
>
>   - ```sql
>     SELECT last_name AS 姓,first_name AS 名 FROM employees;
>     ```

# 3.去除重复数据(DISTINCT)

> 分组函数本身也有去重个特点
>
> SELECT DISTINCT department_id FROM employees;

# 4.字符串拼接(CONCAT)

> mysql中的加号+只能是运算操作
>
> 要想拼接字符串需要使用CONCAT
>
> - CONCAT：拼接
>
>   - ```sql
>     SELECT CONCAT('a','b','c') AS 结果;
>     ```
>
>   - ```sql
>     SELECT CONCAT(last_name,first_name) AS 姓名 FROM employees;
>     ```
>
>   - 显示出表employees的全部列，各个列之间用逗号连接，列头显示成OUT_PUT
>
>   - ```sql
>     SELECT CONCAT(`first_name`,',',`last_name`,',',`job_id`,',',IFNULL(commission_pct,0)) AS out_put FROM employees;
>     ```

# 5.显示表的结构(departments)

> DESC t_app_user;

# 6.为空处理(IFNULL)

> IFNULL 如果为空希望返回什么值
>
> - ```sql
>   -- 如果为空返回0
>   SELECT IFNULL(commission_pct,0) AS 奖金率,commission_pct FROM employees;
>   ```

# 7.条件(where)

> 语法：select 查询列表 from 表名 where 筛选条件;

## 条件表达式

> 按条件表达式筛选:[>、< 、= 、!=、 <>、 >=、 <=]
>
> ```sql
> -- 大于
> SELECT last_name,department_id FROM employees WHERE department_id > 60;
> -- 小于
> SELECT last_name,department_id FROM employees WHERE department_id < 60;
> -- 等于
> SELECT last_name,department_id FROM employees WHERE department_id = 60;
> -- 不等于
> SELECT last_name,department_id FROM employees WHERE department_id != 60;
> -- 不等于
> SELECT last_name,department_id FROM employees WHERE department_id <> 60;
> -- 大于等于
> SELECT last_name,department_id FROM employees WHERE department_id >= 60;
> -- 小于等于
> SELECT last_name,department_id FROM employees WHERE department_id <= 60;
> ```

## 逻辑表达式

> 按逻辑表达式筛选:[and(&& ) 、or(||) 、not(!)]
>
> ```sql
> -- and &&
> -- 案例1：查询工资z在10000到20000之间的员工名、工资以及奖金
> SELECT last_name,salary,commission_pct FROM employees
> WHERE salary>=10000 AND salary<=20000;
> 
> -- NOT != , OR ||
> -- 案例2：查询部门编号不是在90到110之间，或者工资高于15000的员工信息
> SELECT * FROM employees
> WHERE NOT(department_id>=90 AND  department_id<=110) OR salary>15000;
> ```

## 模糊查询

> 模糊查询：【like、between and、 in 、is null】

### like 

> 特点:一般和通配符搭配使用
>
> 通配符：
>
> ​	% 任意多个字符,包含0个字符
>
> ​	_ 任意单个字符
>
> 案例:
>
> ```sql
> #案例1：查询员工名中包含字符a的员工信息
> select * from employees where last_name like '%a%';#abc
> #案例2：查询员工名中第三个字符为n，第五个字符为l的员工名和工资
> select last_name,salary FROM employees WHERE last_name LIKE '__n_l%';
> #案例3：查询员工名中第二个字符为_的员工名
> SELECT last_name FROM employees WHERE last_name LIKE '_$_%' ESCAPE '$';
> ```

### between and

> ①使用between and 可以提高语句的简洁度
>
> ②包含临界值
>
> ③两个临界值不要调换顺序
>
> ```sql
> -- 案例1：查询员工编号在100到120之间的员工信息
> SELECT * FROM employees WHERE employee_id >= 100 AND employee_id<=120;
> -- --------BETWEEN---AND-----------
> SELECT * FROM employees WHERE employee_id BETWEEN 100 AND 120;
> ```

### in

> in : 判断某字段的值是否属于in列表中的某一项
>
> ```sql
> -- ①使用in提高语句简洁度
> -- ②in列表的值类型必须一致或兼容
> -- ③in列表中不支持通配符
> -- or
> -- 案例：查询员工的工种编号是 IT_PROG、AD_VP、AD_PRES中的一个员工名和工种编号
> SELECT last_name,job_id FROM employees
> WHERE job_id = 'IT_PROT' OR job_id = 'AD_VP' OR JOB_ID ='AD_PRES';
> 
> -- in
> SELECT last_name,job_id FROM employees
> WHERE job_id IN( 'IT_PROT' ,'AD_VP','AD_PRES');
> ```

### is null

> ```sql
> -- =或<>不能用于判断null值
> -- is null或is not null 可以判断null值
> 
> -- IS NULL
> #案例1：查询没有奖金的员工名和奖金率
> SELECT last_name,commission_pct FROM employees
> WHERE commission_pct IS NULL;
> 
> -- IS NOT NULL
> #案例1：查询有奖金的员工名和奖金率
> SELECT last_name,commission_pct FROM employees
> WHERE commission_pct IS NOT NULL;
> ```

### 安全等于  <=>

> ```sql
> -- #安全等于  <=>
> -- #案例1：查询没有奖金的员工名和奖金率
> SELECT last_name,commission_pct FROM employees
> WHERE commission_pct <=>NULL;
> 	
> -- #案例2：查询工资为12000的员工信息
> SELECT last_name,salary FROM employees
> WHERE salary <=> 12000;
> 
> # is null pk <=>
> 
> -- IS NULL:仅仅可以判断NULL值，可读性较高，建议使用
> -- <=>    :既可以判断NULL值，又可以判断普通的数值，可读性较低
> ```

# 8.排序(order by)

> - 语法：[select 查询列表 from 表名【where  筛选条件】 order by 排序的字段或表达式;]
>
> - asc代表的是升序，可以省略,desc代表的是降序
>
> - order by子句可以支持 单个字段、别名、表达式、函数、多个字段
>
> - order by子句在查询语句的最后面，除了limit子句
>
> - ```sql
>   -- 1、按单个字段排序
>   SELECT * FROM employees ORDER BY salary DESC;
>   
>   -- 2、添加筛选条件再排序
>   -- 案例：查询部门编号>=90的员工信息，并按员工编号降序
>   SELECT * FROM employees WHERE department_id>=90 ORDER BY employee_id DESC;
>   
>   #3、按表达式排序
>   #案例：查询员工信息 按年薪降序
>   SELECT *,salary*12*(1+IFNULL(commission_pct,0)) FROM employees
>   ORDER BY salary*12*(1+IFNULL(commission_pct,0)) DESC;
>   
>   #4、按别名排序
>   #案例：查询员工信息 按年薪升序
>   SELECT *,salary*12*(1+IFNULL(commission_pct,0)) 年薪 FROM employees ORDER BY 年薪 ASC;
>   
>   #5、按函数排序
>   #案例：查询员工名，并且按名字的长度降序
>   SELECT LENGTH(last_name),last_name FROM employees ORDER BY LENGTH(last_name) DESC;
>   
>   #6、按多个字段排序
>   
>   #案例：查询员工信息，要求先按工资降序，再按employee_id升序
>   SELECT * FROM employees ORDER BY salary DESC,employee_id ASC;
>   #1.查询员工的姓名和部门号和年薪，按年薪降序 按姓名升序
>   SELECT last_name,department_id,salary*12*(1+IFNULL(commission_pct,0)) 年薪
>   FROM employees
>   ORDER BY 年薪 DESC,last_name ASC;
>   
>   #2.选择工资不在8000到17000的员工的姓名和工资，按工资降序
>   SELECT last_name,salary
>   FROM employees
>   WHERE salary NOT BETWEEN 8000 AND 17000
>   ORDER BY salary DESC;
>   
>   #3.查询邮箱中包含e的员工信息，并先按邮箱的字节数降序，再按部门号升序
>   SELECT *,LENGTH(email)
>   FROM employees
>   WHERE email LIKE '%e%'
>   ORDER BY LENGTH(email) DESC,department_id ASC;
>   ```

# 9.字符函数

## 获取字符或者字段的长度(length)

> 描述:获取字节个数(utf-8一个汉字代表3个字节,gbk为2个字节)
>
> ```sql
> -- 查看字符支持格式
> SHOW VARIABLES LIKE '%char%'
> -- 1.length 获取参数值的字节个数
> SELECT LENGTH('john');
> SELECT LENGTH('张三丰hahaha');
> select first_name,LENGTH(first_name) FROM employees
> ```

## 字符串拼接(concat)

> 描述:拼接字符串
>
> ```sql
> SELECT CONCAT(last_name,'_',first_name) 姓名 FROM employees;
> 
> SELECT CONCAT(`first_name`,',',`last_name`,',',`job_id`,',',IFNULL(commission_pct,0)) AS out_put 
> FROM employees;
> ```

## 字符串截取(substr)

> 描述:截取从指定索引处后面所有字符,注意：索引从1开始
>
> ```sql
> SELECT SUBSTR('李莫愁爱上了陆展元',7)  out_put;
> 
> SELECT SUBSTR('李莫愁爱上了陆展元',1,3) out_put;
> 
> SELECT SUBSTR(first_name,1,2),first_name FROM employees
> 
> #案例：姓名中首字符大写，其他字符小写然后用_拼接，显示出来
> SELECT CONCAT(UPPER(SUBSTR(last_name,1,1)),'_',LOWER(SUBSTR(last_name,2))) out_put FROM employees;
> ```

## 字符串查询(instr)

> 描述:返回子串第一次出现的索引，如果找不到返回0
>
> ```sql
> SELECT INSTR('杨不殷六侠悔爱上了殷六侠','殷六侠') AS out_put;
> ```

## 去除字符串(trim)

> 描述:去重指定字符("要去除的字符" FROM "目标字符串")
>
> ```sql
> SELECT TRIM('    张翠山    ') AS out_put; --张翠山
> SELECT LENGTH(TRIM('    张翠山    ')) AS out_put; -- 9
> SELECT TRIM('a' FROM 'aaaaaaaaa张aaaaaaaaaaaa翠山aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')  AS out_put; //张aaaaaaaaaaaa翠山
> ```

## 字符转大写(upper)

> 描述:内容转大写
>
> ```sql
> SELECT UPPER('john');
> 
> SELECT CONCAT(UPPER(last_name),LOWER(first_name))  姓名 FROM employees;
> ```

## 字符转小写(lower)

> 描述:内容转小写
>
> ```sql
> SELECT LOWER('joHn');
> 
> SELECT CONCAT(UPPER(last_name),LOWER(first_name))  姓名 FROM employees;
> ```

## 字符串填充左边(lpad)

> 描述:用指定的字符实现左填充指定长度
>
> ```sql
> SELECT LPAD('殷素素',12,'*') AS out_put;
> ```

## 字符串填充右边(rpad)

> 描述:用指定的字符实现右填充指定长度
>
> ```sql
> SELECT RPAD('殷素素',12,'ab') AS out_put;
> ```

## 字符串替换(replace)

> 描述:替换
>
> ```sql
> SELECT REPLACE('周芷若周芷若周芷若周芷若张无忌爱上了周芷若','周芷若','赵敏') AS out_put;
> ```

# 10.数学函数

## 四舍五入(round)

> 描述:四舍五入
>
> ```sql
> SELECT ROUND(-1.55);
> SELECT ROUND(1.567,2);
> ```

## 向上取整(ceil)

> 描述:向上取整,返回>=该参数的最小整数
>
> ```sql
> SELECT CEIL(-1.02);
> ```

## 向下取整(floor)

> 描述:向下取整，返回<=该参数的最大整数
>
> ```sql
> SELECT FLOOR(-9.99);
> ```

## 保留几位小数其余舍弃(truncate)

> 描述:截断
>
> ```sql
> SELECT TRUNCATE(1.69999,1);  //1.6
> SELECT TRUNCATE(1.69999,2);  //1.69
> ```

## 取余(mod)

> 描述:取余
>
> ```sql
> SELECT MOD(10,-3);
> SELECT 10%3;
> ```

# 11.日期函数

## 返回当前系统日期+时间(now)

> 描述:返回当前系统日期+时间
>
> ```sql
> SELECT NOW();
> ```

## 返回当前系统日期，不包含时间(curdate)

> 描述:返回当前系统日期，不包含时间
>
> ```sql
> SELECT CURDATE();
> ```

## 返回当前时间，不包含日期(curtime)

> 描述:返回当前时间，不包含日期
>
> ```sql
> SELECT CURTIME();
> ```

## 可以获取指定年(year)

> 描述:可以获取指定的部分，年、月、日、小时、分钟、秒
>
> ```sql
> SELECT YEAR(NOW()) 年;
> SELECT YEAR('1998-1-1') 年;
> 
> SELECT  YEAR(hiredate) 年 FROM employees;
> ```

## 可以获取指定月(month)

> 描述:可以获取指定的部分，月
>
> ```sql
> SELECT MONTH(NOW()) 月;
> SELECT MONTHNAME(NOW()) 月;
> ```

## 可以获取指定日(day)

> 描述:可以获取指定的部分，日
>
> ```sql
> SELECT DAY(NOW())
> ```

## 可以获取指定小时(hour)

> 描述:可以获取指定的部分，小时
>
> ```sql
> SELECT hour(NOW())
> ```

## 可以获取指定分钟(minute)

> 描述:可以获取指定的部分，分钟
>
> ```sql
> SELECT minute(NOW())
> ```

## 可以获取指定秒(second)

> 描述:可以获取指定的部分，秒
>
> ```sql
> SELECT second(NOW())
> ```

## 将字符通过指定的格式转换成日期(str_to_date)

> 描述:将字符通过指定的格式转换成日期
>
> ```sql
> SELECT STR_TO_DATE('1998-3-2','%Y-%c-%d') AS out_put;
> #查询入职日期为1992--4-3的员工信息
> SELECT * FROM employees WHERE hiredate = '1992-4-3';
> 
> SELECT * FROM employees WHERE hiredate = STR_TO_DATE('4-3 1992','%c-%d %Y');
> ```

## 格式化时间格式(date_format)

> 描述:将日期转换成字符
>
> 案例:
>
> ```sql
> SELECT DATE_FORMAT(NOW(),'%y年%m月%d日') AS out_put;
> 
> #查询有奖金的员工名和入职日期(xx月/xx日 xx年)
> SELECT last_name,DATE_FORMAT(hiredate,'%m月/%d日 %y年') 入职日期
> FROM employees
> WHERE commission_pct IS NOT NULL;
> ```

# 12.其他函数

## 获取数据库版本(version)

> 描述:获取数据库版本
>
> ```sql
> SELECT VERSION();
> ```

## 获取当前数据库名(database)

> 描述:获取当前数据库名
>
> ```sql
> SELECT DATABASE();
> ```

## 获取当前用户信息(user)

> 描述:获取当前用户信息
>
> ```sql
> SELECT USER();
> ```

# 13.控制函数

## 条件语句(if-else)

> 描述:if else 的效果
>
> ```sql
> SELECT IF(10<5,'大','小');
> SELECT last_name,commission_pct,IF(commission_pct IS NULL,'没奖金，呵呵','有奖金，嘻嘻') 备注 FROM employees;
> ```

## 条件语句(switch -case)

> 描述:switch case 的效果
>
> ```sql
> /*案例：查询员工的工资，要求
> 部门号=30，显示的工资为1.1倍
> 部门号=40，显示的工资为1.2倍
> 部门号=50，显示的工资为1.3倍
> 其他部门，显示的工资为原工资
> */
> 
> SELECT salary 原始工资,department_id,
> CASE department_id
> WHEN 30 THEN salary*1.1
> WHEN 40 THEN salary*1.2
> WHEN 50 THEN salary*1.3
> ELSE salary
> END AS 新工资
> FROM employees;
> 
> #案例：查询员工的工资的情况
> -- 如果工资>20000,显示A级别
> -- 如果工资>15000,显示B级别
> -- 如果工资>10000，显示C级别
> -- 否则，显示D级别
> 
> SELECT salary,
> CASE 
> WHEN salary>20000 THEN 'A'
> WHEN salary>15000 THEN 'B'
> WHEN salary>10000 THEN 'C'
> ELSE 'D'
> END AS 工资级别
> FROM employees;
> ```

## while循环

```sql
-- 从1加到十
delimiter $ 
create procedure pro_test8(n int) 
begin 
	declare total int default 0; 
	declare num int default 1; 
	while num<=n do 
		set total = total + num; 
		set num = num + 1; 
	end while; 
	select total; 
end$ 
delimiter ;

CALL pro_test8(10)
```

## repeat结构

> 有条件的循环控制语句, 当满足条件的时候退出循环 。
>
> while 是满足条件才执行，repeat 是满足条件就退出循环。
>
> ```sql
> -- 从1加到十
> delimiter $ 
> create procedure pro_test10(n int) 
> begin 
> 	declare total int default 0; 
> 	repeat 
> 		set total = total + n; 
> 		set n = n - 1; 
> 		-- 如果等于0退出循环
> 		until n=0  
> 	end repeat; 
> 	select total ; 
> end$ 
> delimiter ;
> 
> CALL pro_test10(10)
> ```

## loop循环-LEAVE退出循环

> - leave语句:
>
>   - 用来从标注的流程构造中退出，通常和 BEGIN ... END 或者循环一起使用。
>
>   -  LOOP 和 LEAVE 的简单例子 , 退出循环：
>
>   - ```sql
>     delimiter $ 
>     CREATE PROCEDURE pro_test11(n int) 
>     BEGIN 
>     	declare total int default 0; 
>     	
>     	ins: LOOP  -- 开始循环
>     		IF n <= 0 then 
>     			leave ins;  -- 如果小于等于0对出循环
>     		END IF; 
>     		set total = total + n; 
>     		set n = n - 1; 
>     	END LOOP ins; 
>     	
>     	select total; 
>     END$ 
>     
>     delimiter ;
>     
>     
>     CALL pro_test11(10)
>     ```



# 14.分组查询

> 语法：select 查询列表  from 表【where 筛选条件】group by 分组的字段【order by 排序的字段】;
>
> 特点：
>
> - 1、和分组函数一同查询的字段必须是group by后出现的字段
> - 2、筛选分为两类：分组前筛选和分组后筛选
>   - 针对的表			位置		连接的关键字
>   - 分组前筛选	原始表				group by前	where
>   - 分组后筛选	group by后的结果集    		group by后	having
> - 问题1：分组函数做筛选能不能放在where后面
>   - 答：不能
> - 问题2：where——group by——having
>   - 一般来讲，能用分组前筛选的，尽量使用分组前筛选，提高效率
> - 3、分组可以按单个字段也可以按多个字段
> - 4、可以搭配着排序使用
>
> ```sql
> #引入：查询每个部门的员工个数
> SELECT COUNT(*) FROM employees WHERE department_id=90;
> #1.简单的分组
> 
> #案例1：查询每个工种的员工平均工资
> SELECT AVG(salary),job_id FROM employees GROUP BY job_id;
> #案例2：查询每个位置的部门个数
> SELECT COUNT(*),location_id FROM departments GROUP BY location_id;
> 
> #2、可以实现分组前的筛选
> #案例1：查询邮箱中包含a字符的 每个部门的最高工资
> SELECT MAX(salary),department_id FROM employees WHERE email LIKE '%a%' GROUP BY department_id;
> #案例2：查询有奖金的每个领导手下员工的平均工资
> SELECT AVG(salary),manager_id FROM employees WHERE commission_pct IS NOT NULL GROUP BY manager_id;
> 
> #3、分组后筛选
> #案例：查询哪个部门的员工个数>5
> #①查询每个部门的员工个数
> SELECT COUNT(*),department_id FROM employees GROUP BY department_id;
> 
> #② 筛选刚才①结果
> SELECT COUNT(*),department_id FROM employees
> GROUP BY department_id
> HAVING COUNT(*)>5;
> 
> #案例2：每个工种有奖金的员工的最高工资>12000的工种编号和最高工资
> 
> SELECT job_id,MAX(salary)
> FROM employees
> WHERE commission_pct IS NOT NULL
> GROUP BY job_id
> HAVING MAX(salary)>12000;
> 
> #案例3：领导编号>102的每个领导手下的最低工资大于5000的领导编号和最低工资
> 
> manager_id>102
> 
> SELECT manager_id,MIN(salary)
> FROM employees
> GROUP BY manager_id
> HAVING MIN(salary)>5000;
> 
> #4.添加排序
> 
> #案例：每个工种有奖金的员工的最高工资>6000的工种编号和最高工资,按最高工资升序
> 
> SELECT job_id,MAX(salary) m
> FROM employees
> WHERE commission_pct IS NOT NULL
> GROUP BY job_id
> HAVING m>6000
> ORDER BY m ;
> 
> #5.按多个字段分组
> 
> #案例：查询每个工种每个部门的最低工资,并按最低工资降序
> 
> SELECT MIN(salary),job_id,department_id
> FROM employees
> GROUP BY department_id,job_id
> ORDER BY MIN(salary) DESC;
> ```

# 15.分组函数

> - 功能：用作统计使用，又称为聚合函数或统计函数或组函数
> - 分类：sum 求和、avg 平均值、max 最大值 、min 最小值 、count 计算个数
> - 特点：
>   - sum、avg一般用于处理数值型
>     - max、min、count可以处理任何类型
>   - 以上分组函数都忽略null值
>   - 可以和distinct搭配实现去重的运算
>   - count函数的单独介绍一般使用count(*)用作统计行数
>   - 和分组函数一同查询的字段要求是group by后的字段
>
> ```sql
> #1、简单 的使用
> SELECT SUM(salary) FROM employees;
> SELECT AVG(salary) FROM employees;
> SELECT MIN(salary) FROM employees;
> SELECT MAX(salary) FROM employees;
> SELECT COUNT(salary) FROM employees;
> 
> 
> SELECT SUM(salary) 和,AVG(salary) 平均,MAX(salary) 最高,MIN(salary) 最低,COUNT(salary) 个数
> FROM employees;
> 
> SELECT SUM(salary) 和,ROUND(AVG(salary),2) 平均,MAX(salary) 最高,MIN(salary) 最低,COUNT(salary) 个数
> FROM employees;
> 
> #2、参数支持哪些类型
> 
> SELECT SUM(last_name) ,AVG(last_name) FROM employees;
> SELECT SUM(hiredate) ,AVG(hiredate) FROM employees;
> 
> SELECT MAX(last_name),MIN(last_name) FROM employees;
> 
> SELECT MAX(hiredate),MIN(hiredate) FROM employees;
> 
> SELECT COUNT(commission_pct) FROM employees;
> SELECT COUNT(last_name) FROM employees;
> 
> #3、是否忽略null
> 
> SELECT SUM(commission_pct) ,AVG(commission_pct),SUM(commission_pct)/35,SUM(commission_pct)/107 FROM employees;
> 
> SELECT MAX(commission_pct) ,MIN(commission_pct) FROM employees;
> 
> SELECT COUNT(commission_pct) FROM employees;
> SELECT commission_pct FROM employees;
> 
> 
> #4、和distinct搭配
> 
> SELECT SUM(DISTINCT salary),SUM(salary) FROM employees;
> 
> SELECT COUNT(DISTINCT salary),COUNT(salary) FROM employees;
> 
> 
> 
> #5、count函数的详细介绍
> 
> SELECT COUNT(salary) FROM employees;
> 
> 
> SELECT COUNT(*) FROM employees;
> 
> SELECT COUNT(1) FROM employees;
> 
> 效率：
> MYISAM存储引擎下  ，COUNT(*)的效率高
> INNODB存储引擎下，COUNT(*)和COUNT(1)的效率差不多，比COUNT(字段)要高一些
> 
> 
> #6、和分组函数一同查询的字段有限制
> 
> SELECT AVG(salary),employee_id  FROM employees;
> ```

# 16.联合查询union

```sql
/*
union 联合 合并：将多条查询语句的结果合并成一个结果
语法：
查询语句1
union
查询语句2
union
...

应用场景：
要查询的结果来自于多个表，且多个表没有直接的连接关系，但查询的信息一致时

特点：★
1、要求多条查询语句的查询列数是一致的！
2、要求多条查询语句的查询的每一列的类型和顺序最好一致
3、union关键字默认去重，如果使用union all 可以包含重复项
*/

-- 引入的案例：查询部门编号>90或邮箱包含a的员工信息
SELECT * FROM employees WHERE email LIKE '%a%' OR department_id>90;

SELECT * FROM employees  WHERE email LIKE '%a%'
UNION
SELECT * FROM employees  WHERE department_id>90;

-- 案例：查询中国用户中男性的信息以及外国用户中年男性的用户信息
SELECT id,cname FROM t_ca WHERE csex='男'
UNION ALL
SELECT t_id,tname FROM t_ua WHERE tGender='male';
```

# 17.子查询

> ```sql
> -- 进阶7：子查询
> /*
> 含义：
> 出现在其他语句中的select语句，称为子查询或内查询
> 外部的查询语句，称为主查询或外查询
> 
> 分类：
> 按子查询出现的位置：
> 	select后面：
> 		仅仅支持标量子查询
> 	
> 	from后面：
> 		支持表子查询
> 	where或having后面：★
> 		标量子查询（单行） √
> 		列子查询  （多行） √
> 		
> 		行子查询
> 		
> 	exists后面（相关子查询）
> 		表子查询
> 按结果集的行列数不同：
> 	标量子查询（结果集只有一行一列）
> 	列子查询（结果集只有一列多行）
> 	行子查询（结果集有一行多列）
> 	表子查询（结果集一般为多行多列）
> */
> 
> -- 一、where或having后面
> /*
> 1、标量子查询（单行子查询）
> 2、列子查询（多行子查询）
> 3、行子查询（多列多行）
> 
> 特点：
> ①子查询放在小括号内
> ②子查询一般放在条件的右侧
> ③标量子查询，一般搭配着单行操作符使用
> > < >= <= = <>
> 
> 列子查询，一般搭配着多行操作符使用
> in、any/some、all
> 
> ④子查询的执行优先于主查询执行，主查询的条件用到了子查询的结果
> */
> #1.标量子查询★
> 
> #案例1：谁的工资比 Abel 高?
> 
> #①查询Abel的工资
> SELECT salary
> FROM employees
> WHERE last_name = 'Abel'
> 
> #②查询员工的信息，满足 salary>①结果
> SELECT *
> FROM employees
> WHERE salary>(
> 	SELECT salary
> 	FROM employees
> 	WHERE last_name = 'Abel'
> );
> 
> #案例2：返回job_id与141号员工相同，salary比143号员工多的员工 姓名，job_id 和工资
> 
> #①查询141号员工的job_id
> SELECT job_id
> FROM employees
> WHERE employee_id = 141
> 
> #②查询143号员工的salary
> SELECT salary
> FROM employees
> WHERE employee_id = 143
> 
> #③查询员工的姓名，job_id 和工资，要求job_id=①并且salary>②
> 
> SELECT last_name,job_id,salary
> FROM employees
> WHERE job_id = (
> 	SELECT job_id
> 	FROM employees
> 	WHERE employee_id = 141
> ) AND salary>(
> 	SELECT salary
> 	FROM employees
> 	WHERE employee_id = 143
> 
> );
> 
> 
> #案例3：返回公司工资最少的员工的last_name,job_id和salary
> 
> #①查询公司的 最低工资
> SELECT MIN(salary)
> FROM employees
> 
> #②查询last_name,job_id和salary，要求salary=①
> SELECT last_name,job_id,salary
> FROM employees
> WHERE salary=(
> 	SELECT MIN(salary)
> 	FROM employees
> );
> 
> 
> #案例4：查询最低工资大于50号部门最低工资的部门id和其最低工资
> 
> #①查询50号部门的最低工资
> SELECT  MIN(salary)
> FROM employees
> WHERE department_id = 50
> 
> #②查询每个部门的最低工资
> 
> SELECT MIN(salary),department_id
> FROM employees
> GROUP BY department_id
> 
> #③ 在②基础上筛选，满足min(salary)>①
> SELECT MIN(salary),department_id
> FROM employees
> GROUP BY department_id
> HAVING MIN(salary)>(
> 	SELECT  MIN(salary)
> 	FROM employees
> 	WHERE department_id = 50
> );
> 
> #非法使用标量子查询
> 
> SELECT MIN(salary),department_id
> FROM employees
> GROUP BY department_id
> HAVING MIN(salary)>(
> 	SELECT  salary
> 	FROM employees
> 	WHERE department_id = 250
> 
> );
> 
> 
> 
> #2.列子查询（多行子查询）★
> #案例1：返回location_id是1400或1700的部门中的所有员工姓名
> 
> #①查询location_id是1400或1700的部门编号
> SELECT DISTINCT department_id
> FROM departments
> WHERE location_id IN(1400,1700)
> 
> #②查询员工姓名，要求部门号是①列表中的某一个
> 
> SELECT last_name
> FROM employees
> WHERE department_id  <>ALL(
> 	SELECT DISTINCT department_id
> 	FROM departments
> 	WHERE location_id IN(1400,1700)
> 
> 
> );
> 
> 
> #案例2：返回其它工种中比job_id为‘IT_PROG’工种任一工资低的员工的员工号、姓名、job_id 以及salary
> 
> #①查询job_id为‘IT_PROG’部门任一工资
> 
> SELECT DISTINCT salary
> FROM employees
> WHERE job_id = 'IT_PROG'
> 
> #②查询员工号、姓名、job_id 以及salary，salary<(①)的任意一个
> SELECT last_name,employee_id,job_id,salary
> FROM employees
> WHERE salary<ANY(
> 	SELECT DISTINCT salary
> 	FROM employees
> 	WHERE job_id = 'IT_PROG'
> 
> ) AND job_id<>'IT_PROG';
> 
> #或
> SELECT last_name,employee_id,job_id,salary
> FROM employees
> WHERE salary<(
> 	SELECT MAX(salary)
> 	FROM employees
> 	WHERE job_id = 'IT_PROG'
> 
> ) AND job_id<>'IT_PROG';
> 
> 
> #案例3：返回其它部门中比job_id为‘IT_PROG’部门所有工资都低的员工   的员工号、姓名、job_id 以及salary
> 
> SELECT last_name,employee_id,job_id,salary
> FROM employees
> WHERE salary<ALL(
> 	SELECT DISTINCT salary
> 	FROM employees
> 	WHERE job_id = 'IT_PROG'
> 
> ) AND job_id<>'IT_PROG';
> 
> #或
> 
> SELECT last_name,employee_id,job_id,salary
> FROM employees
> WHERE salary<(
> 	SELECT MIN( salary)
> 	FROM employees
> 	WHERE job_id = 'IT_PROG'
> 
> ) AND job_id<>'IT_PROG';
> 
> 
> 
> #3、行子查询（结果集一行多列或多行多列）
> 
> #案例：查询员工编号最小并且工资最高的员工信息
> 
> 
> SELECT * 
> FROM employees
> WHERE (employee_id,salary)=(
> 	SELECT MIN(employee_id),MAX(salary)
> 	FROM employees
> );
> 
> #①查询最小的员工编号
> SELECT MIN(employee_id)
> FROM employees
> 
> 
> #②查询最高工资
> SELECT MAX(salary)
> FROM employees
> 
> 
> #③查询员工信息
> SELECT *
> FROM employees
> WHERE employee_id=(
> 	SELECT MIN(employee_id)
> 	FROM employees
> 
> 
> )AND salary=(
> 	SELECT MAX(salary)
> 	FROM employees
> 
> );
> 
> 
> #二、select后面
> /*
> 仅仅支持标量子查询
> */
> 
> #案例：查询每个部门的员工个数
> 
> 
> SELECT d.*,(
> 
> 	SELECT COUNT(*)
> 	FROM employees e
> 	WHERE e.department_id = d.`department_id`
>  ) 个数
>  FROM departments d;
>  
>  
>  #案例2：查询员工号=102的部门名
>  
> SELECT (
> 	SELECT department_name,e.department_id
> 	FROM departments d
> 	INNER JOIN employees e
> 	ON d.department_id=e.department_id
> 	WHERE e.employee_id=102
> 	
> ) 部门名;
> 
> 
> 
> #三、from后面
> /*
> 将子查询结果充当一张表，要求必须起别名
> */
> 
> #案例：查询每个部门的平均工资的工资等级
> #①查询每个部门的平均工资
> SELECT AVG(salary),department_id
> FROM employees
> GROUP BY department_id
> 
> 
> SELECT * FROM job_grades;
> 
> 
> #②连接①的结果集和job_grades表，筛选条件平均工资 between lowest_sal and highest_sal
> 
> SELECT  ag_dep.*,g.`grade_level`
> FROM (
> 	SELECT AVG(salary) ag,department_id
> 	FROM employees
> 	GROUP BY department_id
> ) ag_dep
> INNER JOIN job_grades g
> ON ag_dep.ag BETWEEN lowest_sal AND highest_sal;
> 
> 
> 
> #四、exists后面（相关子查询）
> 
> /*
> 语法：
> exists(完整的查询语句)
> 结果：
> 1或0
> 
> 
> 
> */
> 
> SELECT EXISTS(SELECT employee_id FROM employees WHERE salary=300000);
> 
> #案例1：查询有员工的部门名
> 
> #in
> SELECT department_name
> FROM departments d
> WHERE d.`department_id` IN(
> 	SELECT department_id
> 	FROM employees
> 
> )
> 
> #exists
> 
> SELECT department_name
> FROM departments d
> WHERE EXISTS(
> 	SELECT *
> 	FROM employees e
> 	WHERE d.`department_id`=e.`department_id`
> 
> 
> );
> 
> 
> #案例2：查询没有女朋友的男神信息
> 
> #in
> 
> SELECT bo.*
> FROM boys bo
> WHERE bo.id NOT IN(
> 	SELECT boyfriend_id
> 	FROM beauty
> )
> 
> #exists
> SELECT bo.*
> FROM boys bo
> WHERE NOT EXISTS(
> 	SELECT boyfriend_id
> 	FROM beauty b
> 	WHERE bo.`id`=b.`boyfriend_id`
> 
> );
> ```

# 18.数据类型

|  整数类型   | 字节 |                   范围                   |
| :---------: | :--: | :--------------------------------------: |
|   tinyint   |  1   |                 -128~127                 |
|  smallint   |  2   |               -32768~32767               |
|  mediumint  |  3   |             -8388608~8388607             |
| int,integer |  4   |          -2147583648~2147583647          |
|   bigint    |  5   | -9223372036654775808~9223372036654775807 |

| **浮点数类型** | **字节** |             **范围**             |
| :------------: | :------: | :------------------------------: |
|   **float**    |    4     | ±1.75494351E-38~±3.402823466E+38 |
|   **double**   |    8     | ±1.75494351E-38~±3.402823466E+38 |

|  **定点数类型**  | **字节** |                           **范围**                           |
| :--------------: | :------: | :----------------------------------------------------------: |
|   **DEC(M,D)**   | **M+2**  | 最大取值范围与double相同，给定decimal的有效取值范围由M和D决定 |
| **DECIMAL(M,D)** | **M+2**  | 最大取值范围与double相同，给定decimal的有效取值范围由M和D决定 |

| **位类型** | **字节** | **范围**          |
| ---------- | -------- | ----------------- |
| **Bit(M)** | **1~8**  | **Bit(1)~bit(8)** |

# 19.函数

```sql
//1.无参有返回 案例：返回公司的用户总数量
CREATE FUNCTION ceshi1111() RETURNS INT
BEGIN
	#定义局部变量
	DECLARE c INT DEFAULT 0;
	#赋值
	SELECT COUNT(*) INTO c
	FROM t_app_user;
	RETURN c;
END;
#1.1.调用函数
SELECT ceshi1111()

//=======================================================================

//2.有参有返回 案例1：根据员工名，返回它的id
CREATE FUNCTION ceshi2222(empName VARCHAR(20)) RETURNS INT
BEGIN
	SET @sal=0;    #定义用户变量 
	SELECT id INTO @sal   #赋值
	FROM t_app_user
	WHERE nick_name = empName
	LIMIT 1;
	RETURN @sal;
END
//2.1.调用函数
SELECT ceshi2222('愿你')

//=======================================================================

//3.创建函数，实现传入两个float，返回二者之和
CREATE FUNCTION test_fun1(num1 FLOAT,num2 FLOAT) RETURNS FLOAT
BEGIN
	DECLARE SUM FLOAT DEFAULT 0;
	SET SUM=num1+num2;
	RETURN SUM;
END
//3.1.调用函数
SELECT test_fun1(1,2)

//4.查看函数
SHOW CREATE FUNCTION ceshi2222;
//5.删除函数
DROP FUNCTION ceshi2222;
```

# 2.存储过程

```sql
#--------------------------------案例演示-----------------------------------
#1.空参列表
CREATE PROCEDURE myp1()
BEGIN
	SELECT * FROM t_app_user;
END

#调用
CALL myp1()

#2.创建带in模式参数的存储过程-> 根据用户名信息
CREATE PROCEDURE myp2(IN beautyName VARCHAR(20))
BEGIN
	SELECT * FROM t_app_user WHERE nick_name =beautyName;
END

#调用
CALL myp2('愿你')

#案例2 ：创建存储过程实现，用户是否登录成功
CREATE PROCEDURE myp4(IN username VARCHAR(20),IN PASSWORD VARCHAR(20))
BEGIN
	DECLARE result INT DEFAULT 0;#声明并初始化
	
	SELECT COUNT(*) INTO result#赋值
	FROM t_app_user
	WHERE phone = username
	AND password = PASSWORD;
	
	SELECT IF(result>0,'成功','失败');#使用
END

#调用
CALL myp4('18632300006','123456')


#3.创建out 模式参数的存储过程
#案例1：根据输入的女神名，返回对应的男神名

CREATE PROCEDURE myp6(IN beautyName VARCHAR(20),OUT boyName VARCHAR(20))
BEGIN
	SELECT bo.boyname INTO boyname
	FROM boys bo
	RIGHT JOIN
	beauty b ON b.boyfriend_id = bo.id
	WHERE b.name=beautyName ;
	
END


#案例2：根据输入的女神名，返回对应的男神名和魅力值

CREATE PROCEDURE myp7(IN beautyName VARCHAR(20),OUT boyName VARCHAR(20),OUT usercp INT) 
BEGIN
	SELECT boys.boyname ,boys.usercp INTO boyname,usercp
	FROM boys 
	RIGHT JOIN
	beauty b ON b.boyfriend_id = boys.id
	WHERE b.name=beautyName ;
	
END


#调用
CALL myp7('小昭',@name,@cp)$
SELECT @name,@cp$



#4.创建带inout模式参数的存储过程
#案例1：传入a和b两个值，最终a和b都翻倍并返回

CREATE PROCEDURE myp8(INOUT a INT ,INOUT b INT)
BEGIN
	SET a=a*2;
	SET b=b*2;
END $

#调用
SET @m=10$
SET @n=20$
CALL myp8(@m,@n)$
SELECT @m,@n$


#三、删除存储过程
#语法：drop procedure 存储过程名
DROP PROCEDURE p1;
DROP PROCEDURE p2,p3;#×

#四、查看存储过程的信息
DESC myp2;×
SHOW CREATE PROCEDURE  myp2;
```

# =======数据库相关======

## 1.数据库的好处

> 实现数据的持久化,有对应的管理系统,方便查询。
>
> 常见的时间有MySQL、Oracle、SqlServer等

## 2.什么是三大范式

> - 第一范式:1NF要求数据库表的每一列都是不可分割的原子数据项。
> - 第二范式:2NF确保数据库表中的每一列都和主键相关,而不能只与主键的某一部分相关。
> - 第三范式:3NF是在满足第二范式的前提下,确保数据表中的每一列数据都和主键直接相关,而不能间接相关  	  

## 3.说说什么是索引吗

> 答:索引其实是一种数据结构，能够帮助我们快速的检索数据库中的数据,可以提高MySQL的检索速度

## 4.索引的优缺点

> 优势:
>
> - 类似于书籍的目录索引，提高数据检索的效率，降低数据库的IO成本。
> - 通过索引列对数据进行排序，降低数据排序的成本，降低CPU的消耗。
>
> 劣势:
>
> -  索引是一张表,保存了主键与索引字段,索引列也是要占用空间的。
> - 虽然索引大大提高了查询效率，同时却也降低更新表的速度，如对表进行INSERT、UPDATE、DELETE。因为更新表时，MySQL 不仅要保存数据，还要保存一下索引文件每次更新添加了索引列的字段，都会调整因为更新所带来的键值变化后的索引信息。

## 5.索引的具体数据类型和数据结构

> InnoDB:
>
> - mysql索引类型: 
>   - 1） 普通索引[Normal] ：即一个索引只包含单个列，一个表可以有多个单列索引。
>   - 2） 唯一索引[Unique] ：索引列的值必须唯一，但允许有空值。
>   - 3） 复合索引 ：即一个索引包含多个列。
>   - 4）全文搜索的索引[Full Text]。
>   - 5）空间索引[SPATIAL]。
> - MySQL索引方式有两种:Hash,B+Tree(InnoDB引擎，默认的是B+树)    

## 6.索引基本操作

环境准备：

```sql
-- 创建数据库
create database demo_01 default charset=utf8mb4;
```

---

```sql
-- 创建表
CREATE TABLE `city` (
	`city_id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
	`city_name` VARCHAR ( 50 ) NOT NULL,
	`country_id` INT ( 11 ) NOT NULL,
	PRIMARY KEY ( `city_id` ) 
) ENGINE = INNODB DEFAULT CHARSET = utf8;


CREATE TABLE `country` ( 
	`country_id` INT ( 11 ) NOT NULL AUTO_INCREMENT, 
	`country_name` VARCHAR ( 100 ) NOT NULL, 
	PRIMARY KEY ( `country_id` ) 
) ENGINE = INNODB DEFAULT CHARSET = utf8;
```

---

```sql
-- 给城市表插入数据
insert into `city` (`city_id`, `city_name`, `country_id`) values(1,'西安',1); 
insert into `city` (`city_id`, `city_name`, `country_id`) values(2,'NewYork',2); 
insert into `city` (`city_id`, `city_name`, `country_id`) values(3,'北京',1); 
insert into `city` (`city_id`, `city_name`, `country_id`) values(4,'上海',1); 
-- 给国家表插入数据
insert into `country` (`country_id`, `country_name`) values(1,'China'); 
insert into `country` (`country_id`, `country_name`) values(2,'America'); 
insert into `country` (`country_id`, `country_name`) values(3,'Japan'); 
insert into `country` (`country_id`, `country_name`) values(4,'UK');
```

---

创建索引基本语法：

---

> ```sql
> --索引类型:
> CREATE [UNIQUE[唯一索引]|FULLTEXT[全文索引]|SPATIAL[空间索引]] INDEX index_name 
> --指定索引类别默认b+tree
> [USING index_type] 
> --要给那些字段添加索引
> ON tbl_name(index_col_name,...)  
> 
> index_col_name : column_name[(length)][ASC | DESC]
> --创建索引
> CREATE INDEX idx_city_name ON city(city_name)
> --查看索引
> show index from city;
> --删除索引
> DROP INDEX idx_city_name ON city;
> ```

## 7.索引设计原则

> - 对查询频次较高,且数据量比较大的表建立索引。
>
> - 索引字段的选择,最佳候选列应当从where子句的条件中提取,如果where子句中的组合比较多,那么应当挑选最常用、过滤效果最好的列相互组合。
> - 使用唯一索引,区分度越高,使用索引的效率越高。
> - 索引不易过多。
>   - 因为如过对表进行INSERT、UPDATE、DELETE操作时候。
>   - MySQL 不仅要保存数据，还要保存一下索引文件每次更新添加了索引列的字段。
>   - 调整因为更新所带来的键值变化后的索引信息。
>   - 另外索引过多的话，MySQL也会犯选择困难病。
>   - 虽然最终仍然会找到一个可用的索引，但无疑提高了选择的代价。
> - 使用短索引，索引创建之后也是使用硬盘来存储的，因此提升索引访问的I/O效率，也可以提升总体的访问效率。假如构成索引的字段总长度比较短，那么在给定大小的存储块内可以存储更多的索引值，相应的可以有效的提升MySQL访问索引的I/O效率。
> - 利用最左前缀，N个列组合而成的组合索引，那么相当于是创建了N个索引，如果查询时where子句中使用了组成该索引的前几个字段，那么这条查询SQL可以利用组合索引来提升查询效率。

## 8.ALTER命令

```sql
-- 参数说明
-- tb_name 数据表名称
-- index_name 索引的名称
-- column_list 字段的名称

-- --该语句添加一个主键，这意味着索引值必须是唯一的，且不能为NULL 2). 
alter table tb_name add primary key(column_list); 

-- 这条语句创建索引的值必须是唯一的（除了NULL外，NULL可能会出现多次） 3). 
alter table tb_name add unique index_name(column_list); 

-- 添加普通索引， 索引值可以出现多次。 4). 
alter table tb_name add index index_name(column_list); 

-- 该语句指定了索引为FULLTEXT， 用于全文索引
alter table tb_name add fulltext index_name(column_list); 

```

## 5.B+Tree索引和Hash索引区别？

> ①.哈希索引适合等值查询，但是无法进行范围查询
> ②.哈希索引没办法利用索引完成排序
> ③.哈希索引不支持多列联合索引的最左匹配规则
> ④.如果有大量重复键值的情况下，哈希索引的效率会很低，因为存在哈希碰撞问题
> ⑤.Hash索引底层是哈希表,哈希表是一种以key-value存储数据的结构。
>   所以多个数据在存储关系上是完全没有任何顺序关系的。
>   所以对于区间查询是无法直接通过索引查询的,就需要全表扫描。
>   所以哈希索引只适用于等值查询的场景。
> ⑥.而B+ 树是一种多路平衡查询树，所以他的节点是天然有序的（左子节点小于父节点、父节点小于右子节点），所以对于范围查询的时候不需要做全表扫描

## 6.联合索引的规则

> ①.组合索引使用最左前缀匹配原则,mysql会一直从左开始向右匹配直到遇到范围查询(>、<、between、like)就停止匹配。
> ②.比如[ a=1 AND b=2 AND c>3 AND d=4 ]
>    如果建立(a,b,c,d)顺序的索引,d是用不到索引的
>    如果建立(a,b,d,c)的索引则都可以用到,a,b,d的顺序可以任意调整
> ③.在创建多列索引时,根据业务需求,where子句中使用最频繁的一列放在最左边,因为MySQL索引查询会遵循最左前缀匹配的原则,即最左优先。

## 7.查看索引是否生效

> 答:可以通过explain查看sql语句的执行计划,通过执行计划来分析索引使用情况,一般遇到慢查询的时候去排查。

## 8.什么情况下会发生明明创建了索引，但是执行的时候并没有通过索引呢？

> 答:①.一条SQL语句的查询,可以有不同的执行方案,至于最终选择哪种方案,需要通过优化器进行选择,选择执行成本最低的方案。
>   ②.在一条单表查询语句真正执行之前,MySQL的查询优化器会找出执行该语句所有可能使用的方案,对比之后找出成本最低的方案   

## 视图

> - 视图（View）是一种虚拟存在的表。
>
> - 视图并不在数据库中实际存在，行和列的数据来自定义视图的查询中使用的表，
>
> - 意思就是SELECT语句执行后返回的结果集使用视图时动态生成
>
> - 好处：简单，安全，数据独立
>
> - 作用：
>
>   - 简化用户的操作
>   - 使用户能以多种角度看待同一数据
>   - 对重构数据库提供了一定程度的逻辑独立性
>   - 对机密数据提供安全保护
>   - 更清晰的表达查询
>
> - 创建视图
>
>   - ```sql
>     --其中属性列名全部指定或全部省略
>     create view <视图名> ([<列名>[,<列名>]…]) as 子查询 [with check option];
>     -----------------------city_country_view-----------------------------
>     create view city_country_view (city_id,city_name,country_id,country_name)
>     as
>     select t.*,c.country_name from country c , city t;
>     ------------------------city_country_view2----------------------------
>     create view city_country_view2 
>     as
>     select t.*,c.country_name from country c , city t;
>     ```
>
> - 删除视图
>
>   - ```sql
>     DROP VIEW city_country_view ; 
>     ```
>
> - 查看视图
>
>   - ```sql
>     SELECT * FROM city_country_view2
>     ```
>
> - 视图和表的区别
>
>   - 视图是已经编译好了的sql，表不是
>   - 视图没有实际的物理存储记录，表有
>   - 视图是逻辑概念，表可以进行修改
>   - 视图是我们查看表的方法，视图不让用户接触数据表，用户也就不知道表结构
>   - 视图建立、删除只影响视图本身，不影响表

## 9.什么是存储过程？用什么来调用？

> - 存储过程是一个预编译并存储在数据库的SQL语句。
>
> - 就是我只需创建一次，以后在该程序中就可以调用多次。
>
> - ```sql
>   --创建
>   CREATE PROCEDURE 存储过程名(参数列表)
>   BEGIN
>   	存储过程体（一组合法的SQL语句）;
>   END;
>   
>   --调用
>   CALL 存储过程名(参数列表);
>   
>   -- 查询db_name数据库中的所有的存储过程
>   select name from mysql.proc where db='db_name';
>   
>   -- 查询存储过程的状态信息 
>   show procedure status; 
>   
>   -- 查询某个存储过程的定义 
>   show create procedure test.pro_test1;
>   
>   --删除存储过程
>   DROP PROCEDURE [IF EXISTS] sp_name ；
>   ```

## 10.存储过程的优缺点？

> -  优点：
>   - 存储过程是预编译过的，执行效率高。
>   - 存储过程直接存放于数据库中，通过存储过程名直接调用，减少网络通讯。 
>   - 安全性高，执行存储过程需要有一定权限的用户。
>   - 存储过程可以重复使用，可减少数据库开发人员的工作量。
> - 缺点：移植性差   

## 11.存储过程与函数的区别

> - 存储过程可以有1个返回值或多个返回值,也可以没有返回值函数只能有1个返回。	
> - 存储过程声明用procedure，函数用function。
> - 存储过程通过in/out来返回值，函数可以in/out/return来返回值。
> - 如果只有一个返回值，用存储函数，否则，一般用存储过程。

## 12.传递参数in/out/inout

> - IN : 该参数可以作为输入，也就是需要调用方传入值 , 默认 
> - OUT: 该参数作为输出，也就是该参数可以作为返回值 
> - INOUT: 既可以作为输入参数，也可以作为输出参数 

## 13.mysql变量

> - 系统变量:
>
>   - 全局变量前边加个@,会话变量
>
> - 自定义变量:
>
>   - 用户变量,局部变量  
>
> - 扩展:
>
>   - 局部变量DECLARE :该变量的作用范围只能在 BEGIN…END 块中
>
>   - ```sql
>     -- DELIMITER  关键字用来声明SQL语句的分隔符。
>     -- 告诉MySQL解释器，该段命令是否已经结束了，mysql是否可以执行了。
>     -- 默认情况下，delimiter是分号;。
>     -- 在命令行客户端中，如果有一行命令以分号结束，那么回车后，mysql将会执行该命令
>     DELIMITER $
>     CREATE PROCEDURE pro_test3 () BEGIN
>     -- 	创建局部变量
>     	DECLARE NAME VARCHAR ( 20 );
>     -- 	赋值
>     	SET NAME = 'MYSQL';
>     	SELECT NAME;
>     END $
>     DELIMITER;
>     
>     -- 也可以通过select ... into 方式进行赋值操作 :
>     DELIMITER $
>     CREATE PROCEDURE pro_test5 () BEGIN
>     -- 	创建局部变量
>     	declare countnum int;
>     -- 	赋值
>     	select count(*) into countnum from city;
>     	select countnum;
>     END $
>     DELIMITER;
>     
>     
>     -- --调用
>     CALL pro_test3()
>     CALL pro_test5()
>     ```
>
>     

## 14.触发器的作用？

> 答:触发器是与表有关的数据库对象在 insert/update/delete 之前或之后,
>   触发并执行触发器中定义的SQL语句集合
>   触发器还只支持行级触发，不支持语句级触发

## 15.datetime和timestamp的区别

> 1、Timestamp支持的时间范围较小，取值范围：19700101080001——2038年的某个时间
>
> 2、Datetime的取值范围：1000-1-1 ——9999—12-31
>
> 3、timestamp和实际时区有关，更能反映实际的日期，而datetime则只能反映出插入时的当地时区
>
> 4、timestamp的属性受Mysql版本和SQLMode的影响很大

## 16.存储引擎

> InnoD,MyISAM,MEMORY,MERGE,NDB
>
> - 支持事务和外键选择innodb，不需要事务和外键可以考虑MyISAM；
> - innodb支持外键
> - 如果以select和insert操作为主,只有很少的更新和删除操作，并且对事务的完整性、并发
>   性要求不是很高，可以考虑MyISAM，
> - 除了插入和查询意外，还包含很多的更新、删除操作，请使用InnoDB。
> - MyISAM恢复困难；
> - MySQL5.5默认Innodb引擎，如果你不知道用什么，那就用InnoDB，至少不会差。

## 17.什么是 内连接、外连接、交叉连接、笛卡尔积等?

> 答:内连接: 匹配的就链接
>   左外连接: 包含左边表的全部行，以及右边表中全部匹配的行（不管右边的表中是否存在与它们匹配的行）
>   右外连接: 包含右边表的全部行，以及左边表中全部匹配的行（不管左边的表中是否存在与它们匹配的行）
>   全外连接: 包含左、右两个表的全部行，不管另外一边的表中是否存在与它们匹配的行。
>   交叉连接: 生成笛卡尔积－它不使用任何匹配或者选取条件，而是直接将一个数据源中的每个行与另一个数据源的每个行都一一匹配

## 18.什么是事务

> 答:一个或一组sql语句组成一个执行单元，这个执行单元要么全部执行，要么全部不执行。
>   事务又分 隐式事务(如insert、update、delete语句),显式事务(SET autocommit=0)

## 19.事务的四个特性(ACID)

> - 原子性(atomicity)   事务中所有操作,要么全成功;要么撤回到执行事务之前的状态
> - 一致性(consistency) 一个事务中不管涉及到多少个操作,都必须保证事务执行之前和之后数据都是正确的。如果一个事务在执行的过程中，其中某一个或某几个操作失败了，则必须进行回滚。
> - 隔离性(isolation)   事务操作之间彼此独立和透明互不影响。事务独立运行。这通常使用锁来实现。一个事务处理后的结果，影响了其他事务，那么其他事务会撤回。事务的100%隔离，需要牺牲速度。
> - 持久性(durability)  事务一旦提交，其结果就是永久的。即便发生系统故障，也能恢复

## 20.数据库的并发问题

> - 同时运行的多个事务, 当这些事务访问数据库中相同的数据时, 如果没有采取必要的隔离机制, 就会导致各种并发问题:
>   - **脏读**: 一个事务可以读取另一个事务未提交的数据.
>   - **不可重复读**: 一个事务可以读取另一个事务已提交的数据,单条记录前后不匹配.
>   - 虚读（幻读） 一个事务可以读取另一个事务已提交的数据,读取的数据前后多了点或者少了点.
> - 解决读问题： 设置事务隔离级别
> - **数据库事务的隔离性**: 数据库系统必须具有隔离并发运行各个事务的能力, 使它们不会相互影响, 避免各种并发问题。
> - 一个事务与其他事务隔离的程度称为隔离级别。数据库规定了多种事务隔离级别, 不同隔离级别对应不同的干扰程度, **隔离级别越高, 数据一致性就越好, 但并发性越弱。**

## 21.事务隔离级别

> - 未提交读(Read Uncommitted)：
>   - 允许脏读，其他事务只要修改了数据，即使未提交，本事务也能看到修改后的数据值。
>         也就是可能读取到其他会话中未提交事务修改的数据
> - 提交读(Read Committed)：
>   - 只能读取到已经提交的数据。Oracle等多数数据库默认都是该级别 (不重复读)。    
> - 可重复读(Repeated Read)：
>   - 可重复读。无论其他事务是否修改并提交了数据，在这个事务中看到的数据值始终不受其他事务影响。
> - 串行读(Serializable)：完全串行化的读，每次读都需要获得表级共享锁，
> - 读写相互都会阻塞
> - MySQL数据库(InnoDB引擎)默认使用可重复读（ Repeatable read) 隔离级别越高，性能越低。
> - 一般情况下：脏读是不可允许的，不可重复读和幻读是可以被适当允许的。
>   - 查看隔离级别 **select @@tx_isolation**;
>   - 设置隔离级别 **set session|global transaction isolation level** {隔离级别};

## 22.sql优化



# =======JDBC相关======

# Java与SQL对应数据类型转换表

| Java类型           | SQL类型                  |
| ------------------ | ------------------------ |
| boolean            | BIT                      |
| byte               | TINYINT                  |
| short              | SMALLINT                 |
| int                | INTEGER                  |
| long               | BIGINT                   |
| String             | CHAR,VARCHAR,LONGVARCHAR |
| byte   array       | BINARY  ,    VAR BINARY  |
| java.sql.Date      | DATE                     |
| java.sql.Time      | TIME                     |
| java.sql.Timestamp | TIMESTAMP                |

# 使用Statement操作数据表的弊端

> - 存在的问题
>
>   - 存在拼串操作，繁琐
>   - 存在SQL注入问题
>
>   
>
> - 问题案例
>
>   - ```java
>     //注入的sql样本【SELECT user,password FROM user_table WHERE user = '1' or ' AND password = '=1 or '1' = '1'】
>     String user = "1' or ";
>     String password = "=1 or '1' = '1";
>     String sql = "SELECT user,password FROM user_table WHERE user = '"+ user +"' AND password = '"+ password +"'";
>     ```
>
> - 解决案例
>
>   - 要防范 SQL 注入，只要用 PreparedStatement(从Statement扩展而来) 取代 Statement 就可以了。
>   - 使用PreparedStatement替换Statement实现的查询操作，解决Statement拼串和SQL注入问题

# PreparedStatement与Statement对比

> - 代码的可读性和可维护性高。
> - **PreparedStatement 能最大可能提高性能：**
>   - DBServer会对**预编译**语句提供性能优化。因为预编译语句有可能被重复调用，所以<u>语句在被DBServer的编译器编译后的执行代码被缓存下来，那么下次调用时只要是相同的预编译语句就不需要编译，只要将参数直接传入编译过的语句执行代码中就会得到执行。</u>
>   - 在statement语句中,即使是相同操作但因为数据内容不一样,所以整个语句本身不能匹配,没有缓存语句的意义.事实是没有数据库会对普通语句编译后的执行代码缓存。这样<u>每执行一次都要对传入的语句编译一次。</u>
>   - (语法检查，语义检查，翻译成二进制命令，缓存)
> - PreparedStatement 可以防止 SQL 注入 