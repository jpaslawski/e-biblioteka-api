package com.jpas.ebiblioteka;

import com.jpas.ebiblioteka.config.SecretKeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class EBibliotekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EBibliotekaApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void afterStartupActions() throws NoSuchAlgorithmException {
		new SecretKeyGenerator().generateSecretKey();
	}
}
