

# Spring-MVC
Spring-MVC学习

## 1.入门

### 1.1创建maven工程

**① 添加web模块**

**②打包方式：war**

**③引入依赖**

```xml
<!--Spring MVC-->
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-webmvc</artifactId>
  <version>5.3.22</version>
</dependency>

<!--日志-->
<!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-classic -->
<dependency>
  <groupId>ch.qos.logback</groupId>
  <artifactId>logback-classic</artifactId>
  <version>1.2.11</version>
  <scope>test</scope>
</dependency>

<!--servletAPI-->
<!-- https://mvnrepository.com/artifact/javax/servlet/javax.servlet-api -->
<dependency>
  <groupId>javax.servlet</groupId>
  <artifactId>javax.servlet-api</artifactId>
  <version>4.0.1</version>
  <scope>provided</scope>
</dependency>

<!--Spring5和Thymeleaf整合包-->
<!-- https://mvnrepository.com/artifact/org.thymeleaf/thymeleaf-spring5 -->
<dependency>
  <groupId>org.thymeleaf</groupId>
  <artifactId>thymeleaf-spring5</artifactId>
  <version>3.0.15.RELEASE</version>
</dependency>
```

---

### 1.2配置web.xml

```xml
<servlet>
    <servlet-name>SpringMVC</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>SpringMVC</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

---

### 1.3创建请求控制器

由于前端控制器对浏览器发送的请求进行了统一的处理，但是具体的请求有不同的处理过程，因此需要创建处理具体请求的类，即请求控制器

请求控制器中每一个处理请求的方法成为控制器方法

因为SpringMVC的控制器由一个POJO（普通的Java类）担任，因此需要通过@Controller注解将其标识为一个控制层组件，交给Spring的IoC容器管理，此时SpringMVC才能够识别控制器的存在

```java
@Controller
public class HelloController {

}
```

---

### 1.4创建SpringMVC的配置文件

```xml
<!-- 配置Thymeleaf视图解析器 -->

<bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
    <property name="order" value="1"/>
    <property name="characterEncoding" value="UTF-8"/>
    <property name="templateEngine">
        <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
            <property name="templateResolver">
                <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                    <!-- 视图前缀 -->
                    <property name="prefix" value="/WEB-INF/templates/"/>
                    <!-- 视图后缀 -->
                    <property name="suffix" value=".html"/>
                    <property name="templateMode" value="HTML5"/>
                    <property name="characterEncoding" value="UTF-8" />
                </bean>
            </property>
        </bean>
    </property>
</bean>
```

---

### 1.5测试HelloWorld

1.**实现对首页的访问**

在请求控制器中创建处理请求的方法

```java
// @RequestMapping注解：处理请求和控制器方法之间的映射关系 
// @RequestMapping注解的value属性可以通过请求地址匹配请求，/表示的当前工程的上下文路径 
// localhost:8080/springMVC/
@RequestMapping("/")
public String protal(){
    //将逻辑视图返回
    return "index";
}
```

2.**通过超链接跳转到指定页面**

在主页index.html中设置超链接

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>index.html</h1>
    <a th:href="@{/hello}">测试SpringMVC</a>
    <a href="/hello">测试绝对路径</a>
</body>
</html>
```

在请求控制器中创建处理请求的方法

```java
@RequestMapping("/hello")
public String hello(){
    return "success";
}
```

### 1.6总结

浏览器发送请求，若请求地址符合前端控制器的url-pattern，该请求就会被前端控制器DispatcherServlet处理。前端控制器会读取SpringMVC的核心配置文件，通过扫描组件找到控制器，将请求地址和控制器中@RequestMapping注解的value属性值进行匹配，若匹配成功，该注解所标识的控制器方法就是处理请求的方法。处理请求的方法需要返回一个字符串类型的视图名称，该视图名称会被视图解析器解析，加上前缀和后缀组成视图的路径，通过Thymeleaf对视图进行渲染，最终转发到视图所对应页面

---

## 2.@RequestMapping注解

### 2.1@RequestMapping注解的功能

从注解名称上我们可以看到，@RequestMapping注解的作用就是将请求和处理请求的控制器方法关联起来，建立映射关系。

SpringMVC 接收到指定的请求，就会来找到在映射关系中对应的控制器方法来处理这个请求。

---

### 2.2@RequestMapping注解的位置

```java
@Controller 
@RequestMapping("/test") 
public class RequestMappingController { 

//此时请求映射所映射的请求的请求路径为：/test/testRequestMapping 
	@RequestMapping("/testRequestMapping") 
	public String testRequestMapping(){ 
		return "success"; 
	} 
}
```

---

### 2.3@RequestMapping注解的value属性

@RequestMapping注解的value属性通过请求的请求地址匹配请求映射

@RequestMapping注解的value属性是一个字符串类型的数组，表示该请求映射能够匹配多个请求地址所对应的请求

@RequestMapping注解的value属性必须设置，至少通过请求地址匹配请求映射

```html
<a th:href="@{/hello}">测试@RequestMapping注解所标识的位置</a><br/>
<a th:href="@{/abc}">测试@RequestMapping注解的value属性</a><br/>
```

```java
@RequestMapping({"/hello","/abc"})
public String hello(){
    return "success";
}
```

---

### 2.4@RequestMapping注解的method属性

@RequestMapping注解的method属性通过请求的请求方式（get或post）匹配请求映射

@RequestMapping注解的method属性是一个RequestMethod类型的数组，表示该请求映射能够匹配多种请求方式的请求

若当前请求的请求地址满足请求映射的value属性，但是请求方式不满足method属性，则浏览器报错405：Request method 'POST' not supported

```html
<a th:href="@{/abc}">测试@RequestMapping注解的value属性</a><br/>
<form th:action="@{/hello}" method="post">
    <input type="submit" value="测试@RequestMapping注解的method属性"/>
</form>
```

```java
@RequestMapping(
        value={"/hello","/abc"},
        method = {RequestMethod.GET, RequestMethod.POST}
)
public String hello(){
    return "success";
}
```

> 注：
>
> 1、对于处理指定请求方式的控制器方法，SpringMVC中提供了@RequestMapping的派生注解
>
> 处理get请求的映射-->@GetMapping
>
> 处理post请求的映射-->@PostMapping
>
> 处理put请求的映射-->@PutMapping
>
> 处理delete请求的映射-->@DeleteMapping
>
> 2、常用的请求方式有get，post，put，delete
>
> 但是目前浏览器只支持get和post，若在form表单提交时，为method设置了其他请求方式的字符串（put或delete），则按照默认的请求方式get处理
>
> 若要发送put和delete请求，则需要通过spring提供的过滤器HiddenHttpMethodFilter

---

### 2.5@RequestMapping注解的params属性

@RequestMapping注解的params属性通过请求的请求参数匹配请求映射

@RequestMapping注解的params属性是一个字符串类型的数组，可以通过四种表达式设置请求参数和请求映射的匹配关系

"param"：要求请求映射所匹配的请求必须携带param请求参数

"!param"：要求请求映射所匹配的请求必须不能携带param请求参数

"param=value"：要求请求映射所匹配的请求必须携带param请求参数且param=value

"param!=value"：要求请求映射所匹配的请求必须携带param请求参数但是param!=value

```html
<a th:href="@{/hello(username='admin',password=123456)}">测试@RequestMapping注解的params属性</a><br/>
```

```java
@RequestMapping(
        value = {"/hello","/abc"},
        method = {RequestMethod.GET, RequestMethod.POST},
        params = {"username", "!password", "age=20", "gender!=女"}
)
public String hello(){
    return "success";
}
```

> 注：
>
> 若当前请求满足@RequestMapping注解的value和method属性，但是不满足params属性，此时页面回报错400：Parameter conditions "username, 
>
> password!=123456" not met for actualrequest parameters: username={admin}, password={123456}

---

### 2.6@RequestMapping注解的headers属性

@RequestMapping注解的headers属性通过请求的请求头信息匹配请求映射

@RequestMapping注解的headers属性是一个字符串类型的数组，可以通过四种表达式设置请求头信息和请求映射的匹配关系

"header"：要求请求映射所匹配的请求必须携带header请求头信息

"!header"：要求请求映射所匹配的请求必须不能携带header请求头信息

"header=value"：要求请求映射所匹配的请求必须携带header请求头信息且header=value

"header!=value"：要求请求映射所匹配的请求必须携带header请求头信息且header!=value

若当前请求满足@RequestMapping注解的value和method属性，但是不满足headers属性，此时页面显示404错误，即资源未找到

---

### 2.7SpringMVC支持ant风格的路径

？：表示任意的单个字符

*：表示任意的0个或多个字符

**：表示任意层数的任意目录

注意：在使用******时，只能使用/******/xxx的方式

---

### 2.8SpringMVC支持路径中的占位符

原始方式：/deleteUser?id=1

rest方式：/user/delete/1

SpringMVC路径中的占位符常用于RESTful风格中，当请求路径中将某些数据通过路径的方式传输到服务器中，就可以在相应的@RequestMapping注解的value属性中通过占位符{xxx}表示传输的数据，在通过@PathVariable注解，将占位符所表示的数据赋值给控制器方法的形参

```html
<a th:href="@{/test/rest/admin/1}">测试@RequestMapping注解的value属性中的占位符</a><br/>
```

```java
@RequestMapping("/test/rest/{username}/{id}")
public String testRest(@PathVariable("id") Integer id, @PathVariable("username") String username){
    System.out.println("id:"+id+",username:"+username);
    return "success";
}
```

---

## 3.SpringMVC获取请求参数

### 3.1 通过ServletAPI获取

将HttpServletRequest作为控制器方法的形参，此时HttpServletRequest类型的参数表示封装了当前请求的请求报文的对象

```java
@RequestMapping("/param/servletAPI")
public String getParamByServletAPI(HttpServletRequest request) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    System.out.println("username: " + username + ", password: " + password);
    return "success";
}
```

---

### 3.2 通过控制器方法的形参获取请求参数

在控制器方法的形参位置，设置和请求参数同名的形参，当浏览器发送请求，匹配到请求映射时，在DispatcherServlet中就会将请求参数赋值给相应的形参

```html
<form th:action="@{param}" method="post">
    用户名:<input type="text" name="username"/><br/>
    密码:<input type="password" name="password"/><br/>
    <input type="submit" value="登录"/>
</form>
```

```java
@RequestMapping("/param")
public String getParam(String username, String password) {
    System.out.println("username: " + username + ", password: " + password);
    return "success";
}
```

> 注：
>
> 若请求所传输的请求参数中有多个同名的请求参数，此时可以在控制器方法的形参中设置字符串数组或者字符串类型的形参接收此请求参数
>
> 若使用字符串数组类型的形参，此参数的数组中包含了每一个数据
>
> 若使用字符串类型的形参，此参数的值为每个数据中间使用逗号拼接的结果

---

### 3.3 @RequestParam

@RequestParam是将请求参数和控制器方法的形参创建映射关系

@RequestParam注解一共有三个属性：

value：指定为形参赋值的请求参数的参数名

required：设置是否必须传输此请求参数，默认值为true

若设置为true时，则当前请求必须传输value所指定的请求参数，若没有传输该请求参数，且没有设置defaultValue属性，则页面报错400：Required String parameter 'xxx' is not present；若设置为false，则当前请求不是必须传输value所指定的请求参数，若没有传输，则注解所标识的形参的值为null

defaultValue：不管required属性值为true或false，当value所指定的请求参数没有传输或传输的值为""(空)时，则使用默认值为形参赋值

---

### 3.4 @RequestHeader

@RequestHeader是将请求头信息和控制器方法的形参创建映射关系

@RequestHeader注解一共有三个属性：value、required、defaultValue，用法同@RequestParam

---

### 3.5  @CookieValue

@CookieValue是将cookie数据和控制器方法的形参创建映射关系

@CookieValue注解一共有三个属性：value、required、defaultValue，用法同@RequestParam

---

### 3.6 通过POJO获取请求参数

可以在控制器方法的形参位置设置一个实体类类型的形参，此时若浏览器传输的请求参数的参数名和实体类中的属性名一致，那么请求参数就会为此属性赋值

```html
<form th:action="@{param/pojo}" method="post">
    用户名:<input type="text" name="username"/><br/>
    密码:<input type="password" name="password"/><br/>
    <input type="submit" value="登录"/>
</form>
```

```java
@RequestMapping("/param/pojo")
public String getParamByPojo(User user) {
    System.out.println(user);
    return "success";
}
```

---

### 3.7 解决获取请求参数的乱码问题

解决获取请求参数的乱码问题，可以使用SpringMVC提供的编码过滤器CharacterEncodingFilter，但是必须在web.xml中进行注册

```xml
<!--配置Spring的编码过滤器-->
<filter>
  <filter-name>CharacterEncodingFilter</filter-name>
  <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
  <init-param>
    <param-name>encoding</param-name>
    <param-value>UTF-8</param-value>
  </init-param>
  <init-param>
    <param-name>forceEncoding</param-name>
    <param-value>true</param-value>
  </init-param>
</filter>
<filter-mapping>
  <filter-name>CharacterEncodingFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>
```

> 注：
>
> SpringMVC中处理编码的过滤器一定要配置到其他过滤器之前，否则无效

---

## 4.域对象共享数据

### 4.1  使用ServletAPI向request域对象共享数据

```java
@RequestMapping("/testServletAPI") 

public String testServletAPI(HttpServletRequest request){ 

	request.setAttribute("testScope", "hello,servletAPI"); 

	return "success"; 

}
```

---

### 4.2 使用ModelAndView向request域对象共享数据

```java
@RequestMapping("/test/mav")
public ModelAndView testMAV() {
    /**
     * ModelAndView包含Model和View的功能
     * Model:向请求域中共享数据
     * view:设置逻辑视图实现页面跳转
     */
    ModelAndView mav = new ModelAndView();
    //设置逻辑视图实现页面跳转
    mav.setViewName("success");
    // 向请求域中共享数据
    mav.addObject("testRequestScope", "Hello,ModelAndView");
    return mav;
}
```

---

### 4.3 使用Model向request域对象共享数据

```java
@RequestMapping("/test/model")
public String testModel(Model model){
    model.addAttribute("testRequestScope", "Hello,Model");
    return "success";
}
```

---

### 4.4 使用map向request域对象共享数据

```java
@RequestMapping("/test/modelMap")
public String testModelMap(ModelMap modelMap){
    modelMap.addAttribute("testRequestScope", "Hello,ModelMap");
    return "success";
}
```

---

### 4.5  使用ModelMap向request域对象共享数据

```java
@RequestMapping("/test/map")
public String testMap(Map<String, Object> map){
    map.put("testRequestScope", "Hello,Map");
    return "success";
}
```

---

### 4.6 Model、ModelMap、Map的关系

Model、ModelMap、Map类型的参数其实本质上都是 BindingAwareModelMap 类型的

```java
public class BindingAwareModelMap extends ExtendedModelMap{}
```

```java
public class ExtendedModelMap extends ModelMap implements Model{}
```

```java
public class ModelMap extends LinkedHashMap<String,Object>{}
```

```java
public class LinkedHashMap<K,V> extends HashMap<K,V> implements Map<K,V>{}
```

---

### 4.7 向session域共享数据

```java
@RequestMapping("/test/session")
public String testSession(HttpSession session){
    session.setAttribute("testSessionScope", "Hello,Session");
    return "success";
}
```

---

### 4.8 向application域共享数据

```java
@RequestMapping("/test/application")
public String testApplication(HttpSession session){
    ServletContext servletContext = session.getServletContext();
    servletContext.setAttribute("testApplicationScope", "Hello,Application");
    return "success";
}
```

---

## 5.SpringMVC的视图

SpringMVC中的视图是View接口，视图的作用渲染数据，将模型Model中的数据展示给用户

SpringMVC视图的种类很多，默认有转发视图和重定向视图

当工程引入jstl的依赖，转发视图会自动转换为JstlView

若使用的视图技术为Thymeleaf，在SpringMVC的配置文件中配置了Thymeleaf的视图解析器，由此视图解析器解析之后所得到的是ThymeleafView

----

### 5.1ThymeleafView

当控制器方法中所设置的视图名称没有任何前缀时，此时的视图名称会被SpringMVC配置文件中所配置的视图解析器解析，视图名称拼接视图前缀和视图后缀所得到的最终路径，会通过转发的方式实现跳转

```java
@RequestMapping("/test/view/thymeleaf")
public String testThymeleafView(){
    return "success";
}
```

---

### 5.2 转发视图

SpringMVC中默认的转发视图是InternalResourceView

SpringMVC中创建转发视图的情况：

当控制器方法中所设置的视图名称以"forward:"为前缀时，创建InternalResourceView视图，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"forward:"去掉，剩余部分作为最终路径通过转发的方式实现跳转

例如"forward:/"，"forward:/employee"

```java
@RequestMapping("/test/view/forward")
public String testInternalResourceView(){
    return "forward:/test/model";
}
```

---

### 5.3 重定向视图

SpringMVC中默认的重定向视图是RedirectView

当控制器方法中所设置的视图名称以"redirect:"为前缀时，创建RedirectView视图，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"redirect:"去掉，剩余部分作为最终路径通过重定向的方式实现跳转

例如"redirect:/"，"redirect:/employee"

```java
@RequestMapping("/test/view/redirect")
public String testRedirectView(){
    return "redirect:/test/model";
}
```

---

### 5.4 视图控制器view-controller

当控制器方法中，仅仅用来实现页面跳转，即只需要设置视图名称时，可以将处理器方法使用view-controller标签进行表示

```xml
<mvc:view-controller path="/" view-name="index" ></mvc:view-controller>
```

> 注：
>
> 当SpringMVC中设置任何一个view-controller时，其他控制器中的请求映射将全部失效，此时需要在SpringMVC的核心配置文件中设置开启mvc注解驱动的标签：

----

## 6. RESTful

### 6.1 RESTful简介

REST：Representational State Transfer，表现层资源状态转移。

①资源

资源是一种看待服务器的方式，即，将服务器看作是由很多离散的资源组成。每个资源是服务器上一个可命名的抽象概念。因为资源是一个抽象的概念，所以它不仅仅能代表服务器文件系统中的一个文件、数据库中的一张表等等具体的东西，可以将资源设计的要多抽象有多抽象，只要想象力允许而且客户端应用开发者能够理解。与面向对象设计类似，资源是以名词为核心来组织的，首先关注的是名词。一个资源可以由一个或多个URI来标识。URI既是资源的名称，也是资源在Web上的地址。对某个资源感兴趣的客户端应用，可以通过资源的URI与其进行交互。

②资源的表述

资源的表述是一段对于资源在某个特定时刻的状态的描述。可以在客户端-服务器端之间转移（交换）。资源的表述可以有多种格式，例如HTML/XML/JSON/纯文本/图片/视频/音频等等。资源的表述格式可以通过协商机制来确定。请求-响应方向的表述通常使用不同的格式。

③状态转移

状态转移说的是：在客户端和服务器端之间转移（transfer）代表资源状态的表述。通过转移和操作资源的表述，来间接实现操作资源的目的。

---

### 6.2 RESTful的实现

具体说，就是 HTTP 协议里面，四个表示操作方式的动词：GET、POST、PUT、DELETE。

它们分别对应四种基本操作：GET 用来获取资源，POST 用来新建资源，PUT 用来更新资源，DELETE 用来删除资源。

REST 风格提倡 URL 地址使用统一的风格设计，从前到后各个单词使用斜杠分开，不使用问号键值对方式携带请求参数，而是将要发送给服务器的数据作为 URL 地址的一部分，以保证整体风格的一致性。

| 操作     | 传统方式         | REST风格                |
| -------- | ---------------- | ----------------------- |
| 查询操作 | getUserById?id=1 | user/1-->get请求方式    |
| 保存操作 | saveUser         | user-->post请求方式     |
| 删除操作 | deleteUser?id=1  | user/1-->delete请求方式 |
| 更新操作 | updateUser       | user-->put请求方式      |

---

### 6.3 HiddenHttpMethodFilter

由于浏览器只支持发送get和post方式的请求，那么该如何发送put和delete请求呢？

SpringMVC 提供了HiddenHttpMethodFilter帮助我们将 POST 请求转换为 DELETE 或 PUT 请求

HiddenHttpMethodFilter处理put和delete请求的条件：

a>当前请求的请求方式必须为post

b>当前请求必须传输请求参数_method

满足以上条件，HiddenHttpMethodFilter过滤器就会将当前请求的请求方式转换为请求参数_method的值，因此请求参数_method的值才是最终的请求方式

+ 在web.xml中注册HiddenHttpMethodFilter

```xml
<!--设置处理请求方式过滤器-->
<filter>
  <filter-name>HiddenHttpMethodFilter</filter-name>
  <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
</filter>
<filter-mapping>
  <filter-name>HiddenHttpMethodFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>
```

> 注：
>
> 目前为止，SpringMVC中提供了两个过滤器：CharacterEncodingFilter和HiddenHttpMethodFilter
>
> 在web.xml中注册时，必须先注册CharacterEncodingFilter，再注册HiddenHttpMethodFilter
>
> 原因：
>
> + 在 CharacterEncodingFilter 中通过 request.setCharacterEncoding(encoding) 方法设置字符集的
>
> + request.setCharacterEncoding(encoding) 方法要求前面不能有任何获取请求参数的操作
>
> + 而 HiddenHttpMethodFilter 恰恰有一个获取请求方式的操作：
>
> ```java
> String paramValue = request.getParameter(this.methodParam);
> ```

---

## 7. RESTful案例

### 7.1 准备工作

和传统 CRUD 一样，实现对员工信息的增删改查。

+ 搭建环境

+ 准备实体类

```java
package com.Xie.pojo;

/**
 * @Description:
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/8/7 19:36
 */
public class Employee {
    private Integer id;
    private String lastName;
    private String email;
    // 1 male, 0 female
    private Integer gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Employee() {
    }

    public Employee(Integer id, String lastName, String email, Integer gender) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }
}
```

```java
package com.Xie.dao;

import com.Xie.pojo.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/8/7 19:49
 */
@Repository
public class EmployeeDao {
    private static Map<Integer, Employee> employees = null;
    static{
        employees = new HashMap<Integer, Employee>();
        employees.put( 1001 , new Employee( 1001 , "E-AA", "aa@163.com", 1 ));
        employees.put( 1002 , new Employee( 1002 , "E-BB", "bb@163.com", 1 ));
        employees.put( 1003 , new Employee( 1003 , "E-CC", "cc@163.com", 0 ));
        employees.put( 1004 , new Employee( 1004 , "E-DD", "dd@163.com", 0 ));
        employees.put( 1005 , new Employee( 1005 , "E-EE", "ee@163.com", 1 ));
    }
    private static Integer initId = 1006 ;
    public void save(Employee employee){
        if(employee.getId() == null){
            employee.setId(initId++);
        }
        employees.put(employee.getId(), employee);
    }

        public Collection<Employee> getAll() {
        return employees.values();
}

        public Employee get(Integer id){
        return employees.get(id);
}

        public void delete(Integer id){
        employees.remove(id);
}
}
```

---

### 7.2 功能清单

| 功能                | URL 地址    | 请求方式 |
| ------------------- | ----------- | -------- |
| 访问首页√           | /           | GET      |
| 查询全部数据√       | /employee   | GET      |
| 删除√               | /employee/2 | DELETE   |
| 跳转到添加数据页面√ | /toAdd      | GET      |
| 执行保存√           | /employee   | POST     |
| 跳转到更新数据页面√ | /employee/2 | GET      |
| 执行更新√           | /employee   | PUT      |

---

### 7.3 具体功能：访问首页

①配置view-controller

```xml
<mvc:view-controller path="/" view-name="index"/>
```

②创建页面

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>首页</title>
    </head>
    <body>
        <h1>index.html</h1>
        <a th:href="@{/employee}">查询所有的员工信息</a>
    </body>
</html>
```

---

### 7.4 具体功能：查询所有员工数据

①控制器方法

```java
@RequestMapping(value = "/employee", method = RequestMethod.GET)
public String getAllEmployee(Model model) {
    //获取所有的员工信息
    Collection<Employee> allEmployee = employeeDao.getAll();
    //将所有的员工信息在请求域中共享
    model.addAttribute("allEmployee", allEmployee);
    //跳转到列表页面
    return "employee_list";
}
```

②创建employee_list.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>employee list</title>
        <link rel="stylesheet" th:href="@{/static/css/index_work.css}">
    </head>
    <body>
            <table >
                <tr>
                    <th colspan="5">employee list</th>
                </tr>
                <tr>
                    <th>id</th>
                    <th>lastName</th>
                    <th>email</th>
                    <th>gender</th>
                    <th>options(<a th:href="@{/to/add}">add</a> )</th>
                </tr>
                <tr th:each="employee : ${allEmployee}">
                    <td th:text="${employee.id}"></td>
                    <td th:text="${employee.lastName}"></td>
                    <td th:text="${employee.email}"></td>
                    <td th:text="${employee.gender}"></td>
                    <td>
                        <a th:href="@{'/employee/'+${employee.id}}">delete</a>
                        <a th:href="@{'/employee/'+${employee.id}}">update</a>
                    </td>
                </tr>
            </table>
            <form method="post">
                <input type="hidden" name="_method" value="delete">
            </form>
```

---

### 7.5 具体功能：删除

①创建处理delete请求方式的表单

```html
<form method="post">
    <input type="hidden" name="_method" value="delete">
</form>
```

②删除超链接绑定点击事件

+ 引入vue.js

```html
<script type="text/javascript" th:src="@{/static/js/vue.js}"></script>
```

+ 删除超链接

```html
<a @click="deleteEmployee()" th:href="@{'/employee/'+${employee.id}}">delete</a>
```

+ 通过vue处理点击事件

```html
<script type="text/javascript">
    var vue = new Vue({
        el:"#app",
        methods:{
            deleteEmployee(){
                //获取form表单
                var form = document.getElementsByTagName("form")[0];
                //将超链接的href属性值赋值给form表单的acton属性
                //event.target表示当前触发事件的标签
                form.action = event.target.href;
                //提交表单
                form.submit();
                //阻止超链接的默认行为
                event.preventDefault();
            }
        }
    });
</script>
```

③控制器方法

```java
@RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
public String deleteEmployee(@PathVariable("id") Integer id) {
    //根据id删除员工信息
    employeeDao.delete(id);
    //重定向到列表功能：/employee
    return "redirect:/employee";
}
```

---

### 7.6 具体功能：跳转到添加数据页面

①配置view-controller

```xml
<mvc:view-controller path="/to/add" view-name="employee_add"/>
```

②创建employee_add.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>add employee</title>
        <link rel="stylesheet" th:href="@{/static/css/index_work.css}">
    </head>
    <body>
        <form th:action = "@{/employee}" method="post">
            <table>
                <tr>
                    <th colspan="2">add employee</th>
                    </tr>
                <tr>
                    <td>lastName</td>
                    <td><input type="text" name="lastName"></td>
                </tr>
                <tr>
                    <td>email</td>
                    <td><input type="text" name="email"></td>
                </tr>
                <tr>
                    <td>gender</td>
                    <td>
                        <input type="radio" name="gender" value="1">male
                        <input type="radio" name="gender" value="0">female
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="add">
                    </td>
                </tr>
                <tr>
                </tr>
            </table>
        </form>

    </body>
</html>
```

---

### 7.7 具体功能：执行保存

①控制器方法

```java
@RequestMapping(value = "/employee", method = RequestMethod.POST)
public String addEmployee(Employee employee) {
    //保存员工信息
    employeeDao.save(employee);
    //重定向到列表功能：/employee
    return "redirect:/employee";
}
```

---

### 7.8 具体功能：跳转到更新数据页面

①修改超链接

```html
<a th:href="@{'/employee/'+${employee.id}}">update</a>
```

②控制器方法

```java
public String toUpdate(@PathVariable("id") Integer id, Model model) {
    //根据id查询员工信息
    Employee employee = employeeDao.get(id);
    //将员工信息放在请求域中
    model.addAttribute("employee", employee);
    //跳转到修改页面employee_update
    return "employee_update";
}
```

③创建employee_update.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>update employee</title>
        <link rel="stylesheet" th:href="@{/static/css/index_work.css}">
    </head>
    <body>

        <form th:action = "@{/employee}" method="post">
            <input type="hidden" name="_method" value="put">
            <input type="hidden" name="id" th:value="${employee.id}">
            <table>
                <tr>
                    <th colspan="2">update employee</th>
                    </tr>
                <tr>
                    <td>lastName</td>
                    <td><input type="text" name="lastName" th:value="${employee.lastName}"></td>
                </tr>
                <tr>
                    <td>email</td>
                    <td><input type="text" name="email" th:value="${employee.email}"></td>
                </tr>
                <tr>
                    <td>gender</td>
                    <td>
                        <input type="radio" name="gender" value="1" th:field="${employee.gender}">male
                        <input type="radio" name="gender" value="0" th:field="${employee.gender}">female
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="update">
                    </td>
                </tr>
                <tr>
                </tr>
            </table>
        </form>

    </body>
</html>
```

---

### 7.9、具体功能：执行更新

①控制器方法

```java
@RequestMapping(value = "/employee", method = RequestMethod.PUT)
public String updateEmployee(Employee employee) {
    //修改员工信息
    employeeDao.save(employee);
    //重定向到列表功能：/employee
    return "redirect:/employee";
}
```

---

## 8. SpringMVC处理ajax请求

### 8.1 @RequestBody

@RequestBody可以获取请求体信息，使用@RequestBody注解标识控制器方法的形参，当前请求的请求体就会为当前注解所标识的形参赋值

```html
<div id="app">
            <h1>index.html</h1>
            <input type="button" value="测试SpringMVC处理ajax" @click="testAjax()"><br/>
        </div>
<script type="text/javascript">
            var vue = new Vue({
                el:"#app",
                methods:{
                    testAjax(){
                        axios.post(
                                "/spring_mvc_ajax/test/ajax?id=1001",
                                {username:"admin", password:"123456"}
                        ).then(Response=>{
                            console.log(Response.data)
                        });
                    }
                }
            });
        </script>
```

```java
@RequestMapping(value = "/test/ajax")
public void testAjax(Integer id, @RequestBody String requestBody, HttpServletResponse response) throws IOException {

    System.out.println("requestBody = " + requestBody);
    System.out.println("id:"+id);
    response.getWriter().write("hello,axios");
}
```

---

### 8.2 @RequestBody获取json格式的请求参数

> 在使用了axios发送ajax请求之后，浏览器发送到服务器的请求参数有两种格式：
>
> 1. name=value&name=value...，此时的请求参数可以通过request.getParameter()获取，对应SpringMVC中，可以直接通过控制器方法的形参获取此类请求参数
>
> 2. {key:value,key:value,...}，此时无法通过request.getParameter()获取，之前我们使用操作json的相关jar包gson或jackson处理此类请求参数，可以将其转换为指定的实体类对象或map集合。在SpringMVC中，直接使用@RequestBody注解标识控制器方法的形参即可将此类请求参数转换为java对象

使用@RequestBody获取json格式的请求参数的条件：

1. 导入jackson的依赖

```xml
<!--jackson-->
<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
  <version>2.13.3</version>
</dependency>
```

2. SpringMVC的配置文件中设置开启mvc的注解驱动

```xml
<mvc:annotation-driven/>
```

3. 在控制器方法的形参位置，设置json格式的请求参数要转换成的java类型（实体类或map）的参数，并使用@RequestBody注解标识

```html
<input type="button" value="使用@RequestBody注解处理json格式的请求参数" @click="testRequestBody()">
```

```html
<script type="text/javascript" th:src="@{/static/js/vue.js}"></script>
<script type="text/javascript" th:src="@{/static/js/axios.min.js}"></script>
<script type="text/javascript">
    var vue = new Vue({
        el:"#app",
        methods:{
            testRequestBody(){
                axios.post(
                        "/spring_mvc_ajax/test/RequestBody/json",
                        {username:"admin", password:"123456", age:23, gender:"男"}
                ).then(Response=>{
                    console.log(Response.data)
                });
            }
        }
    });
</script>
```

```java
//将json转换为map集合
@RequestMapping("/test/RequestBody/json")
public void testRequestBody(@RequestBody Map<String, Object> map, HttpServletResponse response) throws IOException {
    System.out.println(map);
    response.getWriter().write("hello,RequestBody");
}

//将json转换为实体类对象
@RequestMapping("/test/RequestBody/json")
public void testRequestBody(@RequestBody User user, HttpServletResponse response) throws IOException {
    System.out.println(user);
    response.getWriter().write("hello,RequestBody");
}
```

---

### 8.3 @ResponseBody

@ResponseBody用于标识一个控制器方法，可以将该方法的返回值直接作为响应报文的响应体响应到浏览器

```java
@RequestMapping("/test/ResponseBody")
public String testResponseBody() {
	//此时会跳转到逻辑视图success所对应的页面
    return "success";
}
```

```java
@RequestMapping("/test/ResponseBody")
@ResponseBody
public String testResponseBody() {
    //此时响应浏览器数据success
    return "success";
}
```

---

### 8.4 @ResponseBody响应浏览器json数据

服务器处理ajax请求之后，大多数情况都需要向浏览器响应一个java对象，此时必须将java对象转换为json字符串才可以响应到浏览器，之前我们使用操作json数据的jar包gson或jackson将java对象转换为json字符串。在SpringMVC中，我们可以直接使用@ResponseBody注解实现此功能

@ResponseBody响应浏览器json数据的条件：

1. 导入jackson的依赖

```xml
<!--jackson-->
<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
  <version>2.13.3</version>
</dependency>
```

2. SpringMVC的配置文件中设置开启mvc的注解驱动

```xml
<mvc:annotation-driven/>
```

3. 使用@ResponseBody注解标识控制器方法，在方法中，将需要转换为json字符串并响应到浏览器的java对象作为控制器方法的返回值，此时SpringMVC就可以将此对象直接转换为json字符串并响应到浏览器

```html
<input type="button" value="测试@ResponseBody注解响应json格式的数据" @click="testResponseBody()"><br/>
```

```html
<script type="text/javascript" th:src="@{/static/js/vue.js}"></script>
<script type="text/javascript" th:src="@{/static/js/axios.min.js}"></script>
<script type="text/javascript">
    var vue = new Vue({
        el:"#app",
        methods:{
            
            testResponseBody(){
                axios.post("/spring_mvc_ajax/test/ResponseBody/json").then(Response=>{
                    console.log(Response.data)
                });
            },
        }
    });
</script>
```

```java
//响应浏览器list集合
@RequestMapping("/test/ResponseBody/json")
@ResponseBody
public List<User> testResponseBodyJson() {
    User user1 = new User(1001, "admin1", "123456", 20, "男");
    User user2 = new User(1002, "admin2", "123456", 20, "男");
    User user3 = new User(1003, "admin3", "123456", 20, "男");
    List<User> list = Arrays.asList(user1, user2, user3);
    return list;
}
```

```java
//响应浏览器map集合
@RequestMapping("/test/ResponseBody/json")
@ResponseBody
public Map<String, Object> testResponseBodyJson() {
    User user1 = new User(1001, "admin1", "123456", 20, "男");
    User user2 = new User(1002, "admin2", "123456", 20, "男");
    User user3 = new User(1003, "admin3", "123456", 20, "男");
    Map<String, Object> map = new HashMap<>();
    map.put("1001", user1);
    map.put("1002", user2);
    map.put("1003", user3);
    return map;
}
```

```java
//响应浏览器实体类对象
@RequestMapping("/test/ResponseBody/json")
@ResponseBody
public User testResponseBodyJson() {
    User user = new User(1001, "admin", "123456", 20, "男");
    return user;
}
```

---

### 8.5 @RestController注解

@RestController注解是springMVC提供的一个复合注解，标识在控制器的类上，就相当于为类添加了@Controller注解，并且为其中的每个方法添加了@ResponseBody注解

---

## 9.文件上传和下载

### 9.1 文件下载

ResponseEntity用于控制器方法的返回值类型，该控制器方法的返回值就是响应到浏览器的响应报文使用ResponseEntity实现下载文件的功能

```java
@RequestMapping("/test/down")
public ResponseEntity<byte[]>testResponseEntity(HttpSession session)throws IOException {
    //获取ServletContext对象
    ServletContext servletContext=session.getServletContext();
    //获取服务器中文件的真实路径
    String realPath = servletContext.getRealPath("img");
    realPath = realPath + File.separator + "demo.png";
    //创建输入流
    InputStream is = new FileInputStream(realPath);
    //创建字节数组,is.available()获取输入流所对应文件字节数
    byte[] bytes = new byte[is.available()];
    //将流读到字节数组中
    is.read(bytes);
    //创建HttpHeaders对象设置响应头信息
    MultiValueMap<String,String> headers=new HttpHeaders();
    //设置要下载方式以及下载文件的名字
    headers.add("Content-Disposition","attachment;filename=demo.png");
    //设置响应状态码
    HttpStatus statusCode= HttpStatus.OK;
    //创建ResponseEntity对象
    ResponseEntity<byte[]> responseEntity=new ResponseEntity<>(bytes,headers,statusCode);
    //关闭输入流
    is.close();
    return responseEntity;
}
```

---

### 9.2 文件上传

文件上传要求form表单的请求方式必须为post，并且添加属性enctype="multipart/form-data"SpringMVC中将上传的文件封装到MultipartFile对象中，通过此对象可以获取文件相关信息

上传步骤：

①添加依赖：

```xml
<!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
<dependency>
  <groupId>commons-fileupload</groupId>
  <artifactId>commons-fileupload</artifactId>
  <version>1.4</version>
</dependency>
```

②在SpringMVC的配置文件中添加配置：

```XML
<!--必须通过文件解析器的解析才能将文件转换为MultipartFile对象-->
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"/>
```

③控制器方法：

```JAVA
    @RequestMapping("/test/up")
    public String testUp(MultipartFile photo, HttpSession session) throws IOException {

        //获取上传的文件的名称
        String fileName = photo.getOriginalFilename();
        //获取上传的文件的后缀名
        String hzName = fileName.substring(fileName.lastIndexOf("."));
        //获取uuid
        String uuid = UUID.randomUUID().toString();
        //拼接一个新的文件名
        fileName = uuid + hzName;
        //获取ServletContext对象
        ServletContext servletContext = session.getServletContext();
        //获取当前工程下photo目录的真实路径
        String photoPath = servletContext.getRealPath("photo");
        //创建photoPath所对应的File对象
        File file = new File(photoPath);
        //判断photoPath所对应的File对象是否存在
        if (!file.exists()) {
            //如果不存在，则创建photoPath所对应的File对象
            file.mkdirs();
        }
        String finalPath = photoPath + File.separator + fileName;
        //将上传的文件保存到服务器的photo目录下
        photo.transferTo(new File(finalPath));
        return "success";
    }
```

---

## 10.拦截器

### 10.1 拦截器的配置

SpringMVC中的拦截器用于拦截控制器方法的执行

SpringMVC中的拦截器需要实现HandlerInterceptor

SpringMVC的拦截器必须在SpringMVC的配置文件中进行配置：

```xml
    <!--拦截器-->
    <mvc:interceptors>
        <!--bean和ref标签配置的拦截器默认对DispatcherServlet处理的所有请求进行拦截-->
        <!--<bean class="com.Xie.interceptor.FirstInterceptor"/>-->
        <!--<ref bean="firstInterceptor"/>-->
        <mvc:interceptor>
            <!--配置需要拦截的请求路径，/**表示所有请求-->
            <mvc:mapping path="/*"/>
            <!--配置需要排除拦截的请求的请求路径-->
            <mvc:exclude-mapping path="/abc"/>
            <!--配置拦截器-->
            <ref bean="firstInterceptor"/>
        </mvc:interceptor>
    </mvc:interceptors>
```

---

### 10.2 拦截器的三个抽象方法

SpringMVC中的拦截器有三个抽象方法：

preHandle：控制器方法执行之前执行preHandle()，其boolean类型的返回值表示是否拦截或放行，返回true为放行，即调用控制器方法；返回false表示拦截，即不调用控制器方法

postHandle：控制器方法执行之后执行postHandle()

afterCompletion：处理完视图和模型数据，渲染视图完毕之后执行afterCompletion()

---

### 10.3 多个拦截器的执行顺序

① 若每个拦截器的preHandle()都返回true

此时多个拦截器的执行顺序和拦截器在SpringMVC的配置文件的配置顺序有关：

preHandle()会按照配置的顺序执行，而postHandle()和afterCompletion()会按照配置的反序执行

② 若某个拦截器的preHandle()返回了false

preHandle()返回false和它之前的拦截器的preHandle()都会执行，postHandle()都不执行，返回false 的拦截器之前的拦截器的afterCompletion()会执行

---

## 11.异常处理器

### 11.1 基于配置的异常处理

SpringMVC提供了一个处理控制器方法执行过程中所出现的异常的接口：HandlerExceptionResolver 

HandlerExceptionResolver接口的实现类有：DefaultHandlerExceptionResolver和

SimpleMappingExceptionResolver

SpringMVC提供了自定义的异常处理器SimpleMappingExceptionResolver，使用方式：

```xml
<!--异常解析器-->
<bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
    <property name="exceptionMappings">
        <props>
            <!--key设置要处理的异常，value设置出现该异常时要跳转的页面所对应的逻辑视图-->
            <prop key="java.lang.ArithmeticException">error</prop>
        </props>
    </property>
    <!--设置共享在请求域中的异常信息的属性名-->
    <property name="exceptionAttribute" value="ex"/>
</bean>
```

---

### 11.2 基于注解的异常处理

```java
//将当前类标识位异常处理的组件
@ControllerAdvice
public class ExceptionController {
	//设置要处理的异常信息
    @ExceptionHandler(value = ArithmeticException.class)
    public String handleException(Throwable ex, Model model){
        //ex表示控制器方法所处闲的异常
        model.addAttribute("ex",ex);
        return "error";
    }
}
```

---

## 12.注解配置SpringMVC

使用配置类和注解代替web.xml和SpringMVC配置文件的功能

### 12.1 创建初始化类，代替web.xml

在Servlet3.0环境中，容器会在类路径中查找实现javax.servlet.ServletContainerInitializer接口的类，如果找到的话就用它来配置Servlet容器。

Spring提供了这个接口的实现，名为SpringServletContainerInitializer，这个类反过来又会查找实现WebApplicationInitializer的类并将配置的任务交给它们来完成。Spring3.2引入了一个便利的WebApplicationInitializer基础实现，名为AbstractAnnotationConfigDispatcherServletInitializer，当我们的类扩展了AbstractAnnotationConfigDispatcherServletInitializer并将其部署到Servlet3.0容器的时候，容器会自动发现它，并用它来配置Servlet上下文。

```java
public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    //设置一个配置类代替spring的配置文件
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    @Override
    //设置一个配置类代替SpringMVC的配置文件
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    //设置SpringMVC的前端控制器DispatcherServlet的url-pattern
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    //设置当前的过滤器
    protected Filter[] getServletFilters() {
        //创建编码过滤器
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        //创建处理请求方式的过滤器
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return new Filter[]{characterEncodingFilter, hiddenHttpMethodFilter};
    }
}
```

---

### 12.2 创建SpringConfig配置类，代替Spring的配置文件

```java
@Configuration
public class SpringConfig {
	//ssm整合之后，spring的配置信息写在此类中}
}
```

---

### 12.3 创建WebConfig配置类，代替SpringMVC的配置文件

```java
@Configuration
// 扫描组件
@ComponentScan("com.Xie.controller")
// 开启mvc注解驱动
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    // 配置默认的servlet处理静态资源
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    // 配置视图控制器
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    //@Bean标识的方法的返回值作为bean进行管理，bean的id为方法的名称
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    // 配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FirstInterceptor()).addPathPatterns("/**");
    }

    // 配置异常解析器
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        Properties prop = new Properties();
        prop.put(ArithmeticException.class.getName(), "error");
        exceptionResolver.setExceptionAttribute("ex");
        resolvers.add(exceptionResolver);
    }

    // 配置生成模板解析器
    @Bean
    public ITemplateResolver templateResolver() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        // ServletContextTemplateResolver需要一个ServletContext作为构造参数，可通过WebApplicationContext的方法获得
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(
                webApplicationContext.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        return templateResolver;
    }

    // 生成模板引擎并为模板引擎注入模板解析器
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    // 生成视图解析器并未解析器注入模板引擎
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }
}
```
