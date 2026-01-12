package com.syncstate.apps.SyncTracker;

import com.github.ulisesbocchio.jar.resources.JarResourceLoader;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication
//public class SyncTrackerApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(SyncTrackerApplication.class, args);
//	}
//
//}


@EnableAutoConfiguration
@SpringBootApplication
public class SyncTrackerApplication implements ApplicationContextAware {

	private static ApplicationContext context;

	public static void main(String[] args) {
		StandardEnvironment environment = new StandardEnvironment();
		System.out.println(SpringVersion.getVersion());
		new SpringApplicationBuilder()
				.sources(SyncTrackerApplication.class)
				.environment(environment)
				.resourceLoader(new JarResourceLoader(environment, "resources.extract.dir"))
				.build()
				.run(args);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;

	}

	public static ApplicationContext getContext() {
		return context;
	}
}