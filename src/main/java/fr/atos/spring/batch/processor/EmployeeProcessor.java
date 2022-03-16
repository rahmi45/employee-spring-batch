package fr.atos.spring.batch.processor;

import fr.atos.spring.batch.dto.EmployeeDto;
import fr.atos.spring.batch.model.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EmployeeProcessor implements ItemProcessor<EmployeeDto, Employee> {

    @Override
    public Employee process(EmployeeDto employeeDto) throws Exception {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setAge(employeeDto.getAge());
        System.out.println("inside processor " + employee.toString());
        return employee;
    }
}
