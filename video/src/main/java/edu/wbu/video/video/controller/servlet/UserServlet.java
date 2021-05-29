package edu.wbu.video.video.controller.servlet;

import edu.wbu.video.video.entity.Response;
import edu.wbu.video.video.entity.User;
import edu.wbu.video.video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yuanzhouli
 * @date 2020/12/6 - 23:25
 * @RestController=@ResponseBody+@Controller
 * @ResponseBody 将对象序列化为JSON返回
 */
@CrossOrigin(origins = "*",maxAge = 3600,allowCredentials = "true")
@RestController
@RequestMapping("/user")
public class UserServlet {
    @Autowired
    private UserService userService;
    @GetMapping("/list")
    public Response list(){
        //调用UserService完成查询
        System.out.println(userService);
        List<User> users = userService.findAll();
        return Response.ok().put("users",users);
    }

    /**
     * value：参数名，不设置默认为变量名
     * required：是否必须包含该参数，默认为true，此时不包含该参数报404错误，为false时不包含则对象为null，基本数据类型则报空指针异常
     * defaultValue：默认参数值，如果设置了该值，required=true将失效，自动为false,如果没有传该参数，就使用默认值
     * 使用@RequestBody
     * 适用请求类型：application/json
     * 使用注解@RequestBody可以将requestbody里面的json数据传到后端
     * 遇到的错误：@RequestBody对应的简单对象类中必须要有空构造函数，否则从JSON转换为对象会出错
     */
    @PostMapping(value ="/loginTest")
    public Response login2(@RequestBody User user){
        return null;
    }

    /**
     * 不使用@RequestBody
     * 适用请求类型：
     * multipart/form-data：既可以上传文件等二进制数据，也可以上传表单键值对，只是最后会转化为一条信息；
     * x-www-form-urlencoded：只能上传键值对，并且键值对都是间隔分开的。
     * @param user
     * @return
     */
    @PostMapping(value ="/login")
    public Response login(@RequestBody User user, HttpServletRequest request,HttpServletResponse response){
        System.out.println("/user/login:"+request.getSession().getId());
        if(request.getSession().getAttribute("user")!=null){
            return Response.error("您已登录");
        }
        System.out.println("登录");
        User selectUser=userService.login(user);
        System.out.println(selectUser);
        if(selectUser==null){
            return Response.error("登录失败，用户名或密码错误");
        }else{
            request.getSession().setAttribute("user",selectUser);
            return Response.ok("登录成功").put("user",selectUser);
        }
    }
    @PostMapping(value = "/regist")
    public Response regist(@RequestBody User user){
        System.out.println(user);
        System.out.println("注册");
        return userService.regist(user);
    }
    @PostMapping("/info")
    public Response info(HttpServletRequest request){
        System.out.println("/user/info:"+request.getSession().getId());
        System.out.println("获取信息");
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            return Response.error("您尚未登录");
        }
        return Response.ok("获取用户信息成功").put("user",user);
    }
    @PostMapping("/exit")
    public Response exit(HttpServletRequest request){
        //销毁session
        request.getSession().invalidate();
        return Response.ok("退出成功");
    }
    @PostMapping("/update")
    public Response update(@RequestBody User user,HttpServletRequest request){
        User currentUser= (User) request.getSession().getAttribute("user");
        if(currentUser==null){
            return Response.error("您尚未登录");
        }
        user.setId(currentUser.getId());
        System.out.println("/user/update:"+request.getSession().getId());
        if(userService.update(user)==1){
            request.getSession().removeAttribute("user");
            request.getSession().setAttribute("user",user);
            return Response.ok("更新用户信息成功").put("user",user);
        }
        return Response.error("更新用户信息失败");
    }

}
