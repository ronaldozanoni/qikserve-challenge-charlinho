package com.qikserve.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The type Qik serve challenge application.
 */
@EnableWebMvc
@SpringBootApplication
public class QikServeChallengeApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(QikServeChallengeApplication.class, args);
	}

}
