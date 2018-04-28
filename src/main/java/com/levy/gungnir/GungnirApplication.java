package com.levy.gungnir;

import com.levy.gungnir.flink.streaming.FlinkWordCountDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GungnirApplication {

	public static void main(String[] args) {
		SpringApplication.run(GungnirApplication.class, args);
	}
}
