package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RestController
	static class SSEController {

		@GetMapping(value = "/events",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        Flux<ServerSentEvent<String>> listenForEvents() {
			ServerSentEvent.Builder<String> sseBuilder = ServerSentEvent.builder();

			return Flux.interval(Duration.ofSeconds(10))
					.map(l -> sseBuilder.id(String.valueOf(l)).data("Message "+l).build());
		}
	}
}
