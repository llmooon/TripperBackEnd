package org.soma.tripper.user.repository;

import org.soma.tripper.user.domain.TempUser;
import org.soma.tripper.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TempUserRepository extends JpaRepository<TempUser,Integer> {
    Optional<TempUser> findTempUserByEmail(String user_email);
    Optional<TempUser> findTempUserByValidateUrl(String str);

}
