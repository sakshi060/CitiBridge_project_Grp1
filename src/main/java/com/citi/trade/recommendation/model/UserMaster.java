package com.citi.trade.recommendation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "UserMaster")
@AllArgsConstructor
@NoArgsConstructor
public class UserMaster {

    @Id
    @Column(name = "userId")
    String userId;

    @Column(name = "password")
    String password;

}
