package kr.twww.mrs;

import kr.twww.mrs.data.MongoTest;
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
        MongoTest mt = new MongoTest();
        mt.mockmethod();
        //SpringApplication.run(Main.class, args);
        System.out.println("end");
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
