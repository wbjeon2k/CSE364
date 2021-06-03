package kr.twww.mrs;

import kr.twww.mrs.preprocess.Preprocessor;
import kr.twww.mrs.preprocess.PreprocessorImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

    private static void serverInit() {
        System.out.println("Server resource initializing. Please wait. Do not turn off the server.");
        Preprocessor tmp = new PreprocessorImpl();
        try {
            tmp.GetRecommendList("","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server ready to run.");
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
