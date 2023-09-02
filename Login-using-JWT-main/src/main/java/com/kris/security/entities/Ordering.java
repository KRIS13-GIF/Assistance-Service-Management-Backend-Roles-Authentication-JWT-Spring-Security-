package com.kris.security.entities;
import com.kris.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ordering {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    private Product product;

    @ManyToOne
    private User user;


    private int fileNumber;
    private boolean repaired;
    private boolean completed;

    private Date repNonRepDate;

}