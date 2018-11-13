package org.soma.tripper.user.service;

import org.soma.tripper.user.domain.TempUser;
import org.soma.tripper.user.repository.TempUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TempUserServiceImpl implements TempUserService {
    @Autowired
    TempUserRepository tempUserRepository;

    @Override
    public Optional<TempUser> findUserByEmail(String user_email) {
        return tempUserRepository.findTempUserByEmail(user_email);
    }

    @Override
    public void registerTempUser(TempUser tempUser) {
        tempUserRepository.save(tempUser);
    }

    @Override
    public Optional<TempUser> validateUser(String str) {
        return tempUserRepository.findTempUserByValidateUrl(str);
    }

    @Override
    public void deleteTempUser(TempUser tempUser) {
        tempUserRepository.delete(tempUser);
    }
}
