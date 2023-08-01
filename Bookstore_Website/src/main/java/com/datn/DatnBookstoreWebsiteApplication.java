package com.datn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

/*import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
*/
@SpringBootApplication
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class DatnBookstoreWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatnBookstoreWebsiteApplication.class, args);
	}
}
