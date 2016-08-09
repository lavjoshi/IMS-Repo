package com.example.UserCredentials;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lav on 2/8/16.
 */
@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials,Long> {

    UserCredentials findByUserName(String userName);
    @Query(value = "select * from User_Credentials as u  where u.role='PRODUCE' or u.role='ACCOUNT'",nativeQuery = true)
    List<UserCredentials> viewAllByRole();
}
