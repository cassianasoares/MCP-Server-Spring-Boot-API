package com.demo.mcpservicetube;

import com.demo.mcpservicetube.controller.SubController;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpservicetubeApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpservicetubeApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider apiTools(SubController subController){
		return MethodToolCallbackProvider.builder().toolObjects(subController).build();
	}

}
