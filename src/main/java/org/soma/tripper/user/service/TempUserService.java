package org.soma.tripper.user.service;


import org.soma.tripper.user.domain.TempUser;
import org.soma.tripper.user.domain.User;

import java.util.Optional;

public interface TempUserService {
    Optional<TempUser> findUserByEmail(String user_email);
    void registerTempUser(TempUser tempUser);
    Optional<TempUser> validateUser(String str);
    void deleteTempUser(TempUser tempUser);
}
