package com.doc.word.entity;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
	entityManagerFactoryRef = "entityManager",
	transactionManagerRef = "transactionManager",
	basePackageClasses = {Document.class, User.class})
public class EntityConfig {
	
}
