package com.team3.weekday.db.dao;

import com.team3.weekday.db.entity.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {
    ConfirmationToken findByConfirmationToken(String ConfirmationToken);
}
