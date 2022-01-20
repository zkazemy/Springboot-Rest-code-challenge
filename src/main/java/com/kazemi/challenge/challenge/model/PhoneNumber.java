package com.kazemi.challenge.challenge.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_phone_number")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber extends BaseEntity{

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
