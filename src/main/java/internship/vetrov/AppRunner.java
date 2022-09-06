package internship.vetrov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class AppRunner {

    private static final Logger log = Logger.getLogger(AppRunner.class.getName());

    public static void main(String[] args) {
        log.info("Start main...");
        SpringApplication.run(AppRunner.class, args);
        log.info("Finish main...");
    }
}
