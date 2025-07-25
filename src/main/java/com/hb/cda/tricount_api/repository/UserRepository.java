package com.hb.cda.tricount_api.repository;

import com.hb.cda.tricount_api.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByEmail(String email);

  List<User> findByGroup_Id(String groupId);

}
