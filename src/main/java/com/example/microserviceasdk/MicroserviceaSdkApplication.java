package com.example.microserviceasdk;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("com.example.microserviceasdk.mapper")
@EnableAutoConfiguration
public class MicroserviceaSdkApplication {
	
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUserName;

	@Value("${spring.datasource.password}")
	private String dbPwd;
	
	@Value("${salt}")
	private String salt;
	
	@Value("${secretkey}")
	private String secretkey;


	public static void main(String[] args) {
		SpringApplication.run(MicroserviceaSdkApplication.class, args);
	}

	@Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
	
	@Bean
	public DataSource dataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		System.out.println(driverClassName + " - " + dbUrl + " - " + dbUserName + " - " + dbPwd);
		dataSourceBuilder.driverClassName(driverClassName);
		dataSourceBuilder.url(dbUrl);
		dataSourceBuilder.username(dbUserName);
		AES AESEncryptor = new AES(secretkey, salt);
		dbPwd = AESEncryptor.decrypt(dbPwd);
		System.out.println(driverClassName + " - " + dbUrl + " - " + dbUserName + " - " + dbPwd);
		dataSourceBuilder.password(dbPwd);
		return dataSourceBuilder.build();
	}

}
