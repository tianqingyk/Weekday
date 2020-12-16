package com.team3.weekday.service;

import com.team3.weekday.IDataBus;
import com.team3.weekday.db.entity.User;
import com.team3.weekday.service.interf.IEmailSenderService;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.Response;
import com.team3.weekday.valueobject.ResponseState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.URLEncoder;
import java.text.MessageFormat;

@Service
public class EmailSenderService implements IEmailSenderService {

    @Autowired
    private IDataBus dataBus;

    @Autowired
    JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setTo(to);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public Response sendActiveEmail(User user) throws Exception {
        String code = user.getActiveCode();
        String subject = "Weekday: Active Account";
        String link = "http://localhost:8080/checkCode?code=" + code;
        String template = "src/main/resources/templates/email/confirmRegister.html";
        dataBus.emailSenderService().sendEmail(user.getEmail(), subject, buildContent(template, link));
        return ResponseUtil.send(ResponseState.OK, "email has been sent");
    }

    @Override
    public void sendResetPwdEmail(String to) throws Exception {
        //set expired time after 24 hours
        System.out.println(to);
        long endTimes = System.currentTimeMillis() + 1 * 24 * 3600 * 1000;
        String param = to + ";" + endTimes;

        //encrypt and encode url
        String encode = URLEncoder.encode(param,"UTF-8");
        String subject = "Weekday: Reset Password";
        String link = "http://localhost:8080/resetPassword?encode=" + encode;
        String template = "src/main/resources/templates/email/confirmResetPwd.html";
        dataBus.emailSenderService().sendEmail(to, subject,buildContent(template, link) );
    }

    @Override
    public String buildContent(String template, String link) throws Exception {
        InputStream inputStream = new FileInputStream(new File(template));
        //InputStream inputStream = getClass().getClassLoader().getResourceAsStream(template); This method doesnt work
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            fileReader.close();
        }
        String content = MessageFormat.format(buffer.toString(),link);
        System.out.println(content);
        return content;
    }
}
