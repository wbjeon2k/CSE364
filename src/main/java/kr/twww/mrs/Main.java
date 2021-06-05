package kr.twww.mrs;

import kr.twww.mrs.preprocess.Preprocessor;
import kr.twww.mrs.preprocess.PreprocessorImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sun.misc.Unsafe;

@SpringBootApplication
public class Main
{
    public static void main( String[] args )
    {
        DisableWarning();
        //serverInit();
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
