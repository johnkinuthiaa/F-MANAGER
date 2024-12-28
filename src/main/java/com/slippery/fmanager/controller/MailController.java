package com.slippery.fmanager.controller;

import com.slippery.fmanager.service.impl.SendEmail;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send/mail")
public class MailController {
    private final SendEmail sendEmail;

    public MailController(SendEmail sendEmail) {
        this.sendEmail = sendEmail;
    }
    @PostMapping
    public void mail(@RequestParam String to, @RequestBody String body, @RequestParam String subject){
        sendEmail.Mail(to, subject,body);
    }
}
