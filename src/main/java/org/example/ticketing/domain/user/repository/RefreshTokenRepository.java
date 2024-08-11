package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
