package com.hb.cda.tricount_api.entity;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_table")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String name;

  private String email;

  private String password;

  private Boolean active;

  @ManyToMany(mappedBy = "users")
  private List<Group> group = new ArrayList<>();

  @ManyToMany(mappedBy = "beneficiary")
  private List<Expense> expenses = new ArrayList<>();

  @OneToMany(mappedBy = "payer")
  private List<Expense> paidExpenses = new ArrayList<>();

  @OneToMany(mappedBy = "creditor")
  private List<Settlement> creditorSettlements = new ArrayList<>();

  @OneToMany(mappedBy = "debtor")
  private List<Settlement> debtorSettlements = new ArrayList<>();

}
