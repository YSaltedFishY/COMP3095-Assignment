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

dependencyManagement{
	imports{
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.3")
	}
}

repositories {
	mavenCentral()
	maven {
		url=uri("https://packages.confluent.io/maven/")
	}
}

//tasks.register("prepareKotlinBuildScriptModel"){}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
	testImplementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
	implementation("io.confluent:kafka-avro-serializer:7.7.2")
	implementation("io.confluent:kafka-schema-registry-client:7.7.2")
	implementation("org.apache.avro:avro:1.12.0")
//	implementation(project(":shared-schema")) //use : to specify local
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

	implementation("org.springframework.cloud:spring-cloud-circuitbreaker-resilience4j:3.1.2")
	implementation("org.springframework.kafka:spring-kafka:3.3.0")
	implementation("org.springframework.kafka:spring-kafka-test:3.3.0")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:mongodb")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:kafka")

	testImplementation("io.rest-assured:rest-assured:5.5.0")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
