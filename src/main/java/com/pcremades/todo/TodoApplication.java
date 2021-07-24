package com.pcremades.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

//	@Bean(name="junitDatasource")
//	public DriverManagerDataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.h2.Driver");
//		dataSource.setUrl("jdbc:h2:./mydb");
//		dataSource.setUsername("sa");
//		dataSource.setPassword("");
//
//
//		Resource initSchema = new ClassPathResource("./data.sql");
//		DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
//		DatabasePopulatorUtils.execute(databasePopulator, dataSource);
//
//		return dataSource;
//	}

}
