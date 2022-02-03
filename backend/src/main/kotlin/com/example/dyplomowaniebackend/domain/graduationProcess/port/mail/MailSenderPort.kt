package com.example.dyplomowaniebackend.domain.graduationProcess.port.mail

interface MailSenderPort {
    fun sendMail(recipient: String, content: String)
}