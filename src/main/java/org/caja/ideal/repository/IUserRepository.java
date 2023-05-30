package org.caja.ideal.repository;

import org.caja.ideal.models.UserModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserModels, Long> {
  Optional<UserModels> findByUsername(String username);
}
