package com.hb.cda.tricount_api.entity;

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
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_table")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String name;

  private String email;

  private String password;

  private boolean active;

  @ManyToMany(mappedBy = "users")
  private List<Group> groups = new ArrayList<>();

  @ManyToMany(mappedBy = "beneficiaries")
  private List<Expense> beneficiaryExpenses = new ArrayList<>();

  @OneToMany(mappedBy = "payer")
  private List<Expense> paidExpenses = new ArrayList<>();

  @OneToMany(mappedBy = "creditor")
  private List<Settlement> creditorSettlements = new ArrayList<>();

  @OneToMany(mappedBy = "debtor")
  private List<Settlement> debtorSettlements = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(() -> "ROLE_USER");
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isEnabled() {
    return active;
  }
}
