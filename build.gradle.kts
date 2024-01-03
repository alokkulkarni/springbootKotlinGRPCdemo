import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

buildscript {
	dependencies {
		classpath("com.google.protobuf:protobuf-gradle-plugin:0.9.4")
	}
}


plugins {
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.graalvm.buildtools.native") version "0.9.28"
	id("com.google.protobuf") version "0.9.4"
	kotlin("jvm") version "1.9.21"
	kotlin("plugin.spring") version "1.9.21"
}

group = "com.alok.grpc"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

allprojects {
	repositories {
		mavenLocal()
		mavenCentral()
		google()
	}
}
dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.grpc:grpc-protobuf:1.60.1")
	implementation("io.grpc:grpc-stub:1.60.1")
	implementation("io.grpc:grpc-netty:1.60.1")
	compileOnly("javax.annotation:javax.annotation-api:1.3.2")
	api("com.google.protobuf:protobuf-java-util:3.25.1")
	implementation("io.grpc:grpc-all:1.53.0")
	api("io.grpc:grpc-kotlin-stub:1.4.1")
	implementation("io.grpc:protoc-gen-grpc-kotlin:1.4.1")
	implementation("net.devh:grpc-server-spring-boot-autoconfigure:2.15.0.RELEASE")
//	implementation("com.google.protobuf:protobuf-gradle-plugin:0.9.4")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

protobuf {
	protoc{
		artifact = "com.google.protobuf:protoc:3.25.1"
	}
	plugins {
		id("grpc"){
			artifact = "io.grpc:protoc-gen-grpc-java:1.60.1"
		}
		id("grpckt") {
			artifact = "io.grpc:protoc-gen-grpc-kotlin::1.4.1:jdk8@jar"
		}
	}
	generateProtoTasks {
		all().forEach {
			it.plugins {
				id("grpc")
				id("grpckt")
			}
		}
	}
}
