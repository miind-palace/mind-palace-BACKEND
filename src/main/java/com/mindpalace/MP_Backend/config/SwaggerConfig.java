package com.mindpalace.MP_Backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Server;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        Server serverLocal = new Server("local", "http://localhost:8081", "for local usages", Collections.emptyList(), Collections.emptyList());
        Server productionServer = new Server("real", "https://www.mind-palace.co.kr/", "for realServer", Collections.emptyList(), Collections.emptyList());

        return new Docket(DocumentationType.OAS_30)
                .servers(serverLocal, productionServer)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mindpalace.MP_Backend")) //특정 패키지 경로를 API 문서화 함 : 1차 필터
                .paths(PathSelectors.any())// apis 중 특정 path조건 API만 문서화함 : 2차 필터
                .build()
                .groupName("API 1.0.0")//group별 명칭 부여
                .useDefaultResponseMessages(false); // 400,404,500 .. 표기를 ui에서 삭제한다.


    }

    private ApiInfo apiInfo() {
        String title = "MindPalace API";
        String version = "1.0.0";
        String description = "MindPalace API Documents";
        return new ApiInfoBuilder()
                .title(title)
                .version(version)
                .description(description)
                .build();
    }
}
