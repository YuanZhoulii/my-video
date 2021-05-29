//package edu.wbu.video.video.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.session.web.http.CookieSerializer;
//import org.springframework.session.web.http.DefaultCookieSerializer;
//
///**
// * @author yuanzhouli
// * @date 2020/12/10 - 9:20
// */
//@Configuration
//public class CookieConfig {
//    @Bean
//    public CookieSerializer httpSessionIdResolver(){
//        System.out.println("cookie设置");
//        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//        serializer.setCookiePath("/");
//        serializer.setCookieMaxAge(3600);
//        serializer.setUseHttpOnlyCookie(false);
//        serializer.setUseSecureCookie(true);
//        serializer.setSameSite("None");
//        return serializer;
//    }
//}
