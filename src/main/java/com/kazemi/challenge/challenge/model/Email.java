package com.kazemi.challenge.challenge.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_email")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Email  extends BaseEntity {

    @Column(name = "mail")
    private String mail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
