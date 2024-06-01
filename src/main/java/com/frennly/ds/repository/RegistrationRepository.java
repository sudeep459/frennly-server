package com.frennly.ds.repository;

import com.frennly.ds.model.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<UserRegistration, Long> {

}
