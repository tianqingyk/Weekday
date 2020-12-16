package com.team3.weekday.service.interf;

import com.team3.weekday.db.entity.User;
import com.team3.weekday.valueobject.Response;

import javax.mail.MessagingException;

public interface IEmailSenderService {
    void sendEmail(String to, String subject, String context) throws MessagingException;

    Response sendActiveEmail(User user) throws Exception;

    void sendResetPwdEmail(String to) throws Exception;

    String buildContent(String template, String link) throws Exception;
}
