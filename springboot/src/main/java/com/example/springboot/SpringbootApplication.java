package com.example.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement//开启事务管理
@MapperScan("com.example.springboot.dao")//与dao层的@Mapper二选一写上即可(主要作用是扫包)
public class SpringbootApplication  {



    public static void main(String[] args) {

        SpringApplication.run(SpringbootApplication.class, args);
    }
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:9528");
        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedHeader("sessionToken");
        //List<String> head=new ArrayList<>();
//        head.add("sessionToken");
//        corsConfiguration.setAllowedHeaders(head);
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    /**
     * 跨域过滤器
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);

    }

}
