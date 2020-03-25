package com.ericho.coupleShare

import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.web.context.WebApplicationContext
import javax.servlet.ServletContext

class ServletInitializer : SpringBootServletInitializer() {

	override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
		return application.sources(CoupleShareApplication::class.java)

	}

	override fun createRootApplicationContext(servletContext: ServletContext?): WebApplicationContext {
		return super.createRootApplicationContext(servletContext)
	}
}
