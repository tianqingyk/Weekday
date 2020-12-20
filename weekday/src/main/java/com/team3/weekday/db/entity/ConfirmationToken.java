package com.team3.weekday.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;

    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)//TODO: join userid
    private User user;

    public ConfirmationToken(User user) {
        this.user = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

    public ConfirmationToken() {

    }
}
