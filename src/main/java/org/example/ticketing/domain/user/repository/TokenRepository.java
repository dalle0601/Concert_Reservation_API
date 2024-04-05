package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.Token;

public interface TokenRepository {
    Token tokenInsertOrUpdate(Long user_id, String token);
}
