package com.example.UserCredentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lav on 2/8/16.
 */
@Service
public class UserCredentialsService {

    @Autowired
    UserCredentialsRepository userCredentialsRepository;
    @Transactional
    UserCredentials findByUserName(String userName)
    {
        return userCredentialsRepository.findByUserName(userName);
    }

    @Transactional
    public UserCredentials createUser(UserCredentials userCredentials)
    {
        return userCredentialsRepository.save(userCredentials);
    }

    @Transactional
    public List<UserCredentials> viewAllByRoles()
    {
        return userCredentialsRepository.viewAllByRole();
    }


    @Transactional
    public List<UserCredentials> deleteUser(String name)
    {
        UserCredentials userCredentials=userCredentialsRepository.findByUserName(name);
        userCredentialsRepository.delete(userCredentials);
        return userCredentialsRepository.viewAllByRole();
    }

    @Transactional
    public UserCredentials findUser(UserCredentials userCredentials)
    {
        return userCredentialsRepository.findByUserName(userCredentials.getUserName());
    }

    @Transactional
    public boolean updatePass(UserCredentials userCredentials,String newPass)
    {
        UserCredentials u=userCredentialsRepository.findByUserName(userCredentials.getUserName());
        if(u.getPassword().equals(userCredentials.getPassword()))
        {
            u.setPassword(newPass);
            return true;
        }
        else
            return false;
    }
}
