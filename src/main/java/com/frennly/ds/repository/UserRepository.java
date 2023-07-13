package com.frennly.ds.repository;

import com.frennly.ds.enums.UserType;
import com.frennly.ds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
    public User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE (u.username LIKE :query OR u.email LIKE :query) AND u.userType=:usertype")
    public List<User> searchUser(@Param("query") String query, @Param("usertype") UserType userType);
    public List<User> findByUserType(UserType userType);
}
