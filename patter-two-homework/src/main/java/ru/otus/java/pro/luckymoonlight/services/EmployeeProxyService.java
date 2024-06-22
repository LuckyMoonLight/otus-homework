package ru.otus.java.pro.luckymoonlight.services;

import ru.otus.java.pro.luckymoonlight.repository.DataSource;
import ru.otus.java.pro.luckymoonlight.repository.EmployeeRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeProxyService {
    private DataSource dataSource;
    private EmployeeService employeeService;

    public EmployeeProxyService() {
        dataSource = DataSource.getInstance();
        employeeService = new EmployeeService(new EmployeeRepository(dataSource.getConnection()));
    }

    public void createEmployees() throws SQLException {
        try {
            employeeService.createEmployees();
            dataSource.getConnection().commit();
        } catch (SQLException e)  {
            dataSource.getConnection().rollback();
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateAllSalary() throws SQLException {
        try {
            employeeService.updateAllSalary();
            dataSource.getConnection().commit();
        } catch (SQLException e)  {
            dataSource.getConnection().rollback();
            throw new RuntimeException(e.getMessage());
        }
    }

    public void printAll() {
        try {
            employeeService.printAll();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
