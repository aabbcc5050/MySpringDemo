package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@SpringBootApplication
public class DemoApplication {

	long count = 0;

	ThreadLocal<Long> local_count = new ThreadLocal<>();

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping
	public String index(HttpServletRequest request) {
		local_count.set(local_count.get() != null ? local_count.get() + 1 : 1);

		return "IP:" + request.getRemoteHost() + "<br/>" + "ThreadLocal:" + Thread.currentThread().getName() + "=>"
				+ local_count.get() + "<br/>" + "count:" + (++count);
	}
}
