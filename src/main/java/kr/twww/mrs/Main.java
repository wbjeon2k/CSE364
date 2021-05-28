package kr.twww.mrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sun.misc.Unsafe;

@SpringBootApplication
public class Main
{
    public static void main( String[] args )
    {
        DisableWarning();

        SpringApplication.run(Main.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:8080");
            }
        };
    }

    private static void DisableWarning()
    {
        try
        {
            var theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);

            var unsafe = (Unsafe)theUnsafe.get(null);
            var cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            var logger = cls.getDeclaredField("logger");

            unsafe.putObjectVolatile(
                    cls,
                    unsafe.staticFieldOffset(logger),
                    null
            );
        }
        catch ( Exception exception )
        {
            exception.printStackTrace();
        }
    }
}
