package com.waifucomic.www.wc_b;

import com.waifucomic.www.wc_b.comics.Comic;
import com.waifucomic.www.wc_b.comics.ComicRepository;
import com.waifucomic.www.wc_b.user.User;
import com.waifucomic.www.wc_b.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;

@SpringBootApplication
public class WcBApplication {

	public static void main(String[] args) {
		SpringApplication.run(WcBApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(
			@Value("${admin.user}") String username,
			@Value("${admin.passwd}") String passwd,
			UserRepository repository
	) {
		return (args) -> {
			adminSetup(username, passwd, repository);
			externalSetup();
		};
	}

	private void adminSetup(
			@Value("${admin.user}") String username,
			@Value("${admin.passwd}") String passwd,
			UserRepository repository
	) {
		if (repository.count() > 1 || repository.count() == 0) {
			repository.deleteAll();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			repository.save(new User(username, encoder.encode(passwd)));
		}
	}

	private void externalSetup() {
		File imgDir = new File("img");
		if (!imgDir.exists()) {
			imgDir.mkdir();
		}
	}
}
