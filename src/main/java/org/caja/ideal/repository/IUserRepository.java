package org.caja.ideal.repository;

import org.caja.ideal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
  //  Optional<User> findByIdUsername(String username);
}
