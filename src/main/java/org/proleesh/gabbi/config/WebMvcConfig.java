package org.proleesh.gabbi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * @author sung-hyuklee
 * @since JDK 21
 * date: 2024.6.26
 * update: 2024.7.4
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${uploadPath}")
    String uploadPath;

    @Value("${videoUploadPath}")
    String videoUploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/gabbi/images/**")
                .addResourceLocations(uploadPath)
                .setCachePeriod(0);
        registry.addResourceHandler("/videos/**")
                .addResourceLocations(videoUploadDir);
    }
}
