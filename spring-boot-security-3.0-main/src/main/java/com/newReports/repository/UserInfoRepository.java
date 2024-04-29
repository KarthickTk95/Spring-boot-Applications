package com.newReports.repository;

import com.newReports.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<Member, Integer> {

  //  @Query("SELECT m FROM prdApp.Member m WHERE m.username = :username")
    Optional<Member> findByUsername(String username);

}
