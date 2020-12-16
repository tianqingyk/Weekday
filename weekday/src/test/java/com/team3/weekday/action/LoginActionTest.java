package com.team3.weekday.action;

import com.team3.weekday.IDataBus;
import com.team3.weekday.WeekdayApplication;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.db.dao.UserRepository;
import com.team3.weekday.db.entity.User;
import com.team3.weekday.net.ChannelSession;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.Response;
import com.team3.weekday.valueobject.ResponseState;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.Null;
import java.awt.*;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeekdayApplication.class)
class LoginActionTest {

    @Autowired
    private  LoginAction LoginAction;

    @Autowired
    private IDataBus dataBus;

    @MockBean
    private UserRepository userRepository;
    @Test
    void login() throws Exception {

        User user1 = new User("RW", "123", "123@123.com", 123L, new Date(), "");
        user1.setId(1l);

        Mockito.when(dataBus.userRepository().findByUsername("RW")).thenReturn(user1);

        try{
            LoginAction.login("RW","123",new ChannelSession(null));

        }catch (Exception e) {

            Assert.assertEquals(e.getMessage(), CommonConstant.ERROR_LOG_IN_PASSWORD_WRONG);
        }
        user1.setPassword(CommonConstant.passwordEncoder.encode(user1.getPassword()));
        Response response = LoginAction.login("RW", "123", new ChannelSession(1l,null));
                Assert.assertEquals(response.isSuccess(), true);

                try {
                    LoginAction.login("RW","1233",new ChannelSession(1l,null));
                }catch(Exception e) {
                    Assert.assertEquals(e.getMessage(),CommonConstant.ERROR_LOG_IN_PASSWORD_WRONG);
                }
    }


}