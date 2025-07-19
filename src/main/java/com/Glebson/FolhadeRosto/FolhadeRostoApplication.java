package com.Glebson.FolhadeRosto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FolhadeRostoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FolhadeRostoApplication.class, args);
	}

}
