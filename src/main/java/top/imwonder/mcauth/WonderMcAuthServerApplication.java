package top.imwonder.mcauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@EnableCaching
@Configuration
@SpringBootApplication(exclude = QuartzAutoConfiguration.class)
public class WonderMcAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WonderMcAuthServerApplication.class, args);
	}

}
