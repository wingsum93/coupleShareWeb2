package com.ericho.coupleShare

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoupleShareApplication

fun main(args: Array<String>) {
	val LOGGER: Logger = LogManager.getLogger(CoupleShareApplication::class.java)

	runApplication<CoupleShareApplication>(*args)
	LOGGER.info("Application enter main stage @@!!##");
}
