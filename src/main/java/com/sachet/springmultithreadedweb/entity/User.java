package com.sachet.springmultithreadedweb.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "user_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String gender;

}
