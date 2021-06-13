package kr.twww.mrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import sun.misc.Unsafe;

@SpringBootApplication
public class Main //extends SpringBootServletInitializer
{
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(Main.class);
//    }

    public static void main(String[] args )
    {
        DisableWarning();
        //serverInit();
        System.out.println("Info: Spring server ready to initialize.");
        SpringApplication.run(Main.class, args);
    }

    /*
    @Bean(initMethod="PreprocessorImplInit")
    public Preprocessor initTest() {
        return new PreprocessorImpl();
    }
    */


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
