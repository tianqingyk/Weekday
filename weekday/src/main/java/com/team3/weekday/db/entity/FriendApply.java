package com.team3.weekday.db.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class FriendApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId1;

    private Long userId2;

    public FriendApply(Long userId1, Long userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }

    public FriendApply() {

    }

}
