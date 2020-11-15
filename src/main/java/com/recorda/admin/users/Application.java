package com.recorda.admin.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Bootstrap class for micro-service
 *
 * This application is responsible for users management for Recardo e-commerce platform.
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.recorda.admin.users"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.US);
		return resolver;
	}

	@Bean
	public MessageSource messageResource() {
		ResourceBundleMessageSource rbm = new ResourceBundleMessageSource();
		rbm.setBasename("msg.message");
		return rbm;
	}

}
