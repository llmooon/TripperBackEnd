package org.soma.tripper.user.repository;

import org.soma.tripper.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByEmailAndPassword(String user_email, String password);
    User findByEmail(String user_email);
}
