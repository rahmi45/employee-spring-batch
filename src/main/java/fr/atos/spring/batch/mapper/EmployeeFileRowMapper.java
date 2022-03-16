package fr.atos.spring.batch.mapper;

import fr.atos.spring.batch.dto.EmployeeDto;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class EmployeeFileRowMapper implements FieldSetMapper<EmployeeDto> {

    @Override
    public EmployeeDto mapFieldSet(FieldSet fieldSet) {
        EmployeeDto employee = new EmployeeDto();
        employee.setId(fieldSet.readString("id"));
        employee.setFirstName(fieldSet.readString("firstName"));
        employee.setLastName(fieldSet.readString("lastName"));
        employee.setEmail(fieldSet.readString("email"));
        try {
            employee.setAge(fieldSet.readInt("age"));
        } catch (Exception e) {

        }
        return employee;
    }

}
