package org.soma.tripper.repository;

import org.soma.tripper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findUserByEmailAndPassword(String user_email, String password);
    User findByEmail(String user_email);
}
