package fr.atos.spring.batch.dto;

import lombok.Data;

@Data
public class EmployeeDto {

    private  String id;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  int age;
}
