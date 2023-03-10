package gr.codehub.sacchon.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SacchonApplication {

    public final static boolean DEBUG_MODE = false;
    public final static String SCHEMA = DEBUG_MODE ? "DEVELOP" : "PRODUCTION";

    public static void main(String[] args) {

        SpringApplication.run(SacchonApplication.class);

    }
}
