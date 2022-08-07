package com.Xie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description:
 * 1.@RequestMapping注解标识的位置
 * @RequestMapping标识一个类：设置映射请求的请求路径的初始信息
 * @RequestMapping标识一个方法：设置映射请求请求路径的具体信息
 * 2.@RequestMapping注解的value属性：
 * 作用：通过请求的请求路径匹配请求
 * value属性是数组类型，即当前浏览器所发送请求的请求路径匹配value属性中的任何一个值
 * 则的当前请求就会被注解所表示的方法进行处理
 *3.@RequestMapping注解的method属性：
 * 作用：通过请求的请求方式匹配请求
 * method属性是RequestMethod类型的数组，即当前浏览器所发送请求的请求方式匹配method属性中的任何一个值
 * 若浏览器所发送请求的请求路径和@RequestMapping注解的value属性匹配，但是请求方式不匹配
 * 此时页面报错：405 - Request Method ‘XXX’ not supported
 * 在@RequestMapping的基础上，结合请求方式的一些派生注解：
 * @GetMapping，@PostMapping，@PutMapping，@DeleteMapping，@PatchMapping，@HeadMapping，@OptionsMapping
 * 4.@RequestMapping注解的params属性：
 * 作用：通过请求的请求参数匹配请求
 * params属性是数组类型，即当前浏览器所匹配请求的请求参数匹配params属性中的任何一个值
 * params可以用四种表达式：
 * "param":表示当前所匹配请求的请求参数中必须携带param参数
 * "!param":表示当前所匹配请求的请求参数中必须不携带param参数
 * "param=value":表示当前所匹配请求的请求参数中必须携带param参数，且该参数的值必须为value
 * "param!=value":表示当前所匹配请求的请求参数中可以不携带param参数，若携带则该参数的值必须不为value
 * 若浏览器所发送的请求路径和@RequestMapping注解的value属性匹配，但是请求参数不匹配：
 * 此时页面报错：400 - parameter condition "xxx" not met for actual request parameters:
 * 5.@RequestMapping注解的headers属性：
 * 作用：通过请求的请求头匹配请求，即当前浏览器所发送请求的请求头匹配headers属性中的任何一个值
 * 若浏览器所发送的请求路径和@RequestMapping注解的value属性匹配，但是请求头不匹配：
 * 此时页面报错：404
 * 6.SpringMVC支持ant风格的路径
 * 在@RequestMapping注解的value属性值中设置一些特殊字符
 * ?:任意的单个字符(不包括?和/)
 * *:任意个数的任意字符(不包括?和/)
 * **:任意层数的任意目录，注意使用方式只能将**写在双斜线中，前后不能有任何其他字符
 * 7.@RequestMapping注解使用路径中的占位符
 * 传统：/deleteUser?id=1
 * rest:/user/delete/1
 * 需要在@RequestMapping注解的value属性中所设置的路径中，使用{xxx}的方式表示路径中的数据
 * 再通过PathVariable注解，将占位符所标识的值和控制器方法的形参进行绑定
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/8/6 16:23
 */
@Controller
//@RequestMapping("/test")
public class TestRequestMapping {

    //此时控制器方法所匹配的请求的路径是/test/hello
    @RequestMapping(
            value = {"/hello","/abc"},
            method = {RequestMethod.GET, RequestMethod.POST}
            /*params = {"username", "!password", "age=20", "gender!=女"}*/
    )
    public String hello(){
        return "success";
    }

    @RequestMapping("/**/test/ant")
    public String testAnt(){
        return "success";
    }

    @RequestMapping("/test/rest/{username}/{id}")
    public String testRest(@PathVariable("id") Integer id, @PathVariable("username") String username){
        System.out.println("id:"+id+",username:"+username);
        return "success";
    }
}
