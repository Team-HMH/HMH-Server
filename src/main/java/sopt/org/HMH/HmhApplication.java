package sopt.org.HMH;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class HmhApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmhApplication.class, args);
    }

}
