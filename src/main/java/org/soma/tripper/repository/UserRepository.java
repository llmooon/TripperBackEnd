package org.soma.tripper.repository;

import org.soma.tripper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByEmailAndPassword(String user_email, String password);
}
