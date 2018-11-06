package org.soma.tripper.user.repository;

import org.soma.tripper.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByEmailAndPassword(String user_email, String password);
    Optional<User> findByEmail(String user_email);
    Optional<User> findUserByValidateUrl(String str);
}
