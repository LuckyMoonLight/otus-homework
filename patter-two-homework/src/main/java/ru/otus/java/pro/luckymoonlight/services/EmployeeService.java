package ru.otus.java.pro.luckymoonlight.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.pro.luckymoonlight.entity.Employee;
import ru.otus.java.pro.luckymoonlight.repository.EmployeeRepository;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    public void createEmployees() throws SQLException {
        for (int i = 0; i < 100; i++) {
            Employee employee = new Employee(Long.valueOf(i), "Employee_" + i, 1000L);
            employeeRepository.save(employee);
        }
    }

    public void updateAllSalary() throws SQLException {
        List<Employee> employees = employeeRepository.findAll();
        if (employees != null) {
            for (Employee employee : employees) {
                employee.setSalary(employee.getSalary() * 2);
                employeeRepository.save(employee);
            }
        }
    }

    public void printAll() throws SQLException {
        List<Employee> employees = employeeRepository.findAll();
        for(Employee employee : employees) {
            logger.info(employee.toString());
        }
    }

}
