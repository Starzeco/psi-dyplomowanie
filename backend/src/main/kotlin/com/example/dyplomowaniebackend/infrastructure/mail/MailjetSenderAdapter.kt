package com.example.dyplomowaniebackend.infrastructure.mail

import com.example.dyplomowaniebackend.domain.graduationProcess.port.mail.MailSenderPort
import com.mailjet.client.ClientOptions
import com.mailjet.client.MailjetClient
import com.mailjet.client.MailjetRequest
import com.mailjet.client.errors.MailjetException
import com.mailjet.client.errors.MailjetSocketTimeoutException
import com.mailjet.client.resource.Emailv31
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service


@Configuration
@ConfigurationProperties(prefix = "mail")
class MailConfig {
    var mailjet: Mailjet? = null

    class Mailjet {
        var apiKey: String? = null
        var apiSecret: String? = null
        var version: String? = null
        var senderMail: String? = null
        var senderName: String? = null
        var mailSubject: String? = null
    }
}


@Service
class MailjetSenderAdapter(private val mailjetConfig: MailConfig) : MailSenderPort {

    override fun sendMail(recipient: String, content: String) {
        val client = MailjetClient(
            mailjetConfig.mailjet!!.apiKey,
            mailjetConfig.mailjet!!.apiSecret,
            ClientOptions(mailjetConfig.mailjet!!.version)
        )

        val request: MailjetRequest = MailjetRequest(Emailv31.resource)
            .property(
                Emailv31.MESSAGES, JSONArray()
                    .put(
                        createMessage(
                            content,
                            mailjetConfig.mailjet!!.mailSubject!!,
                            recipient
                        )
                    )
            )
        try {
            val response = client.post(request)
            println(response.data.toString())
        } catch (e: MailjetException) {
            println(e.message)
        } catch (e: MailjetSocketTimeoutException) {
            println(e.message)
        }
    }

    private fun createMessage(
        content: String,
        subject: String,
        recipient: String,
    ): JSONObject? {
        return JSONObject()
            .put(
                Emailv31.Message.FROM, JSONObject()
                    .put("Email", mailjetConfig.mailjet!!.senderMail)
                    .put("Name", mailjetConfig.mailjet!!.senderName)
            )
            .put(
                Emailv31.Message.TO, JSONArray()
                    .put(
                        JSONObject()
                            .put("Email", recipient)
                    )
            )
            .put(Emailv31.Message.SUBJECT, subject)
            .put(Emailv31.Message.TEXTPART, content)
    }
}