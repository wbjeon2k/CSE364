package kr.twww.mrs;

import kr.twww.mrs.preprocess.Preprocessor;
import kr.twww.mrs.preprocess.PreprocessorImpl;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main
{
    public static void main( String[] args )
    {
        SpringApplication.run(Main.class, args);
        System.out.println("Spring Application Running.");
    }
}
