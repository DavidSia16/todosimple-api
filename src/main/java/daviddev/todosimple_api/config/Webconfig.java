package daviddev.todosimple_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class Webconfig implements WebMvcConfigurer {
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedHeaders("*")
        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
        
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redireciona a raiz e o login diretamente para os ficheiros estáticos correspondentes
        registry.addViewController("/").setViewName("forward:/login.html");
        registry.addViewController("/login").setViewName("forward:/login.html");
    }
    
}
