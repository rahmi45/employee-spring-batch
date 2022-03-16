package fr.atos.spring.batch.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Employee {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;

}
