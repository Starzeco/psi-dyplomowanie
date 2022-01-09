package com.example.dyplomowaniebackend

import com.example.dyplomowaniebackend.infrastructure.persistence.repository.CustomSimpleJpaRepositoryImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomSimpleJpaRepositoryImpl::class)
class DyplomowanieBackendApplication

fun main(args: Array<String>) {
	runApplication<DyplomowanieBackendApplication>(*args)
}
