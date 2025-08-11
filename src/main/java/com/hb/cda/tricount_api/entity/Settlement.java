package com.hb.cda.tricount_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "settlement")
public class Settlement {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private Double amount;

  private String comment;

  @ManyToOne
  private User debtor;

  @ManyToOne
  private User creditor;

  @ManyToOne
  private Group group;

}
