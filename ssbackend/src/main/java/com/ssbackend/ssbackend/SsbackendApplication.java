package com.ssbackend.ssbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SsbackendApplication {

	public static void main(String[] args) {
		// Ensure default JVM timezone is valid for Postgres if needed. Postgres rejects 'Asia/Calcutta'.
		// java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("Asia/Kolkata"));
		SpringApplication.run(SsbackendApplication.class, args);
	}

}
