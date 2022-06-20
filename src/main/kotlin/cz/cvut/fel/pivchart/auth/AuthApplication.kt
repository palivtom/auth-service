package cz.cvut.fel.pivchart.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@EnableCaching
@SpringBootApplication
class AuthApplication

fun main(args: Array<String>) {
	runApplication<AuthApplication>(*args)
}