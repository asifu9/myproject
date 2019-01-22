package com.attach.attachment;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan
public class ResourceConfig implements WebMvcConfigurer  {
	 private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
	            "classpath:/META-INF/resources/", "classpath:/images/",
	            "classpath:/static/", "classpath:/public/","classpath:Users/i340632/attachments/uploads/"
	            ,"classpath:/Users/i340632/attachments/uploads","classpath:/Users/i340632/attachments/profileimages/"
	 };
	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	        if (!registry.hasMappingForPattern("/webjars/**")) {
//	            registry.addResourceHandler("/webjars/**").addResourceLocations(
//	                    "classpath:/META-INF/resources/webjars/");
//	        }
//	        if (!registry.hasMappingForPattern("/**")) {
//	            registry.addResourceHandler("/**").addResourceLocations(
//	                    CLASSPATH_RESOURCE_LOCATIONS);
//	        }
		  String myExternalFilePath = "file:/Users/i340632/attachments";

		    registry.addResourceHandler("/**").addResourceLocations(myExternalFilePath);

	    }
}
