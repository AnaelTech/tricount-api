package com.hb.cda.tricount_api.repository;

import com.hb.cda.tricount_api.entity.Group;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

  List<Group> findByUsers_Id(String userId);

  Optional<Group> findByName(String name);
}
