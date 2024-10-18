package com.waifucomic.www.wc_b;

import com.waifucomic.www.wc_b.comics.ComicRepository;
import com.waifucomic.www.wc_b.user.User;
import com.waifucomic.www.wc_b.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WcBApplication {

	public static void main(String[] args) {
		SpringApplication.run(WcBApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(
			@Value("${admin.user}") String username,
			@Value("${admin.passwd}") String passwd,
			UserRepository repository,
			ComicRepository comicRepository
	) {
		return (args) -> {
			repository.deleteAll();
			comicRepository.deleteAll();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			repository.save(new User(username, encoder.encode(passwd)));
		};
	}
}
