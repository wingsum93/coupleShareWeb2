package com.ericho.coupleShare

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
		scanBasePackages = ["com.ericho.*"]
)
class CoupleShareApplication

fun main(args: Array<String>) {
	runApplication<CoupleShareApplication>(*args)
}
