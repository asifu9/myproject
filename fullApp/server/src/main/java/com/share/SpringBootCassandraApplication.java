package com.share;

import javax.servlet.ServletContextListener;
import javax.validation.constraints.NotNull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.share.util.UserCache;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.share")
public class SpringBootCassandraApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootCassandraApplication.class, args);
		
	}
	
//   @NotNull
//    @Bean
//    ServletListenerRegistrationBean<ServletContextListener> myServletListener() {
//        ServletListenerRegistrationBean<ServletContextListener> srb =
//                new ServletListenerRegistrationBean<>();
//        srb.setListener(new ExampleServletContextListener());
//        return srb;
//    }
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
	
	@Bean(name = "userCache")
	public UserCache customerAccount(){
		return new UserCache();
	}

}
