package in.wynk.phoenix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 20/05/17.
 */


@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {

        // set environment property to pick the corresponding properties file
        SpringApplication.run(Application.class, args);

        System.out.println("server started! ------>");
    }
}