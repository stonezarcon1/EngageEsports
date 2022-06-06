package com.stonezarcon.engageesports.models;

import javax.persistence.*;

@Entity
@Table(name = "playerprofile")
public class PlayerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String testField;

    @OneToOne(mappedBy = "profile")
    private User user;


    public String getTestField() {
        return testField;
    }

    public void setTestField(String field) {
        this.testField = field;
    }

    public Long getId() {
        return id;
    }
}
