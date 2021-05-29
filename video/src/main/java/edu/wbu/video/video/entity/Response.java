package edu.wbu.video.video.entity;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class Response extends HashMap<String,Object> {
    public Response(){
        put("flag",true);
        put("code",200);
        put("msg","success");
    }
    public static Response error(){
        return error(HttpStatus.INTERNAL_SERVER_ERROR,"服务器出错，请联系管理员");
    }
    public static Response error(String msg){
        return error(HttpStatus.INTERNAL_SERVER_ERROR,msg);
    }

    public static Response error(HttpStatus status, String msg) {
        Response response=new Response();
        response.put("flag",false);
        response.put("code",status.value());
        response.put("msg",msg);
        return response;
    }
    public static Response ok(){
        return new Response();
    }
    public static Response ok(String msg){
        Response response=new Response();
        response.put("msg",msg);
        return response;
    }
    public static Response ok(Map<String,Object> map){
        Response response=new Response();
        //map中的所有键值对放进response
        response.putAll(map);
        return response;
    }
    @Override
    public Response put(String key,Object value){
        super.put(key,value);
        return this;
    }

}