package com.alpha.DLINK;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DlinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(DlinkApplication.class, args);
	}

}
