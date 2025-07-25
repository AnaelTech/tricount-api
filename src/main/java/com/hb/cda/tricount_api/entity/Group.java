package com.hb.cda.tricount_api.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group_table")
public class Group {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String name;

  @ManyToMany
  private List<User> users = new ArrayList<>();

  @OneToMany(mappedBy = "group")
  private List<Expense> expenses = new ArrayList<>();

  @OneToMany(mappedBy = "group")
  private List<Settlement> settlements = new ArrayList<>();
}
