package com.example.dyplomowaniebackend

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class AppConfiguration {
    @Bean
    fun clock(): Clock = Clock.systemDefaultZone()
}