package com.joelhelkala.watcherServer;

import com.joelhelkala.watcherServer.appuser.AppUser;
import com.joelhelkala.watcherServer.appuser.AppUserRole;
import com.joelhelkala.watcherServer.appuser.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WatcherServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatcherServerApplication.class, args);
	}
}