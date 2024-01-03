package com.alok.grpc.grpcdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(OPAProperties::class)
class GrpcdemoApplication

fun main(args: Array<String>) {
	runApplication<GrpcdemoApplication>(*args)
}
