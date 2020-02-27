package springBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class IdeaspringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdeaspringbootApplication.class, args);
	}

}
