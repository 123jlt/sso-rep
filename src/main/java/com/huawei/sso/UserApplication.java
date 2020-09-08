package com.huawei.sso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@MapperScan({"com.huawei.sso.mapper"})
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
        }

    /*@Bean //必须new 一个RestTemplate并放入spring容器当中,否则启动时报错
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(30 * 1000);
        httpRequestFactory.setConnectTimeout(30 * 3000);
        httpRequestFactory.setReadTimeout(30 * 3000);
        return new RestTemplate(httpRequestFactory);

    }*/
}
