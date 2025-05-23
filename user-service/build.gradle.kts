plugins {
	java
	id("org.springframework.boot") version "3.3.6"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "ca.gbc"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencyManagement{
	imports{
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.3")
	}
}

//tasks.register("prepareKotlinBuildScriptModel"){}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")

	//Week 7.1
	implementation("org.springframework.kafka:spring-kafka:3.3.0")
	testImplementation("org.springframework.kafka:spring-kafka-test:3.3.0")
	testImplementation("org.testcontainers:kafka:1.20.4")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
	testImplementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.5.0")

	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")

	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("io.rest-assured:rest-assured:5.5.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
