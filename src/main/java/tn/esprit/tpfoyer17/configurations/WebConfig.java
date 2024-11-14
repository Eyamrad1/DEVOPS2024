package tn.esprit.tpfoyer17.configurations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Applique CORS à toutes les routes sous /tpFoyer17/api/
                .allowedOrigins("http://192.168.1.12")  // Autoriser l'application Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE" , "OPTIONS")  // Méthodes autorisées
                .allowedHeaders("*")
                .allowCredentials(true);  // Autorise tous les headers
    }
}
