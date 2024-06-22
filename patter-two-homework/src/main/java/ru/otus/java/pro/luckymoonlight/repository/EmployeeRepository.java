package ru.otus.java.pro.luckymoonlight.repository;

import lombok.RequiredArgsConstructor;
import ru.otus.java.pro.luckymoonlight.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class EmployeeRepository implements Repository<Employee> {

    private final Connection connection;

    @Override
    public void save(Employee employee) throws SQLException {
        if (employee == null)
            return;
        String sql = "MERGE INTO employee USING VALUES(?, ?, ?) e (id, name, salary) ON employee.id = e.id" +
                " WHEN MATCHED THEN UPDATE SET name = e.name, salary = e.salary" +
                " WHEN NOT MATCHED THEN INSERT (id, name, salary) VALUES(e.id, e.name, e.salary)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, employee.getId());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setLong(3, employee.getSalary());
            preparedStatement.execute();
        }
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                employees.add(new Employee(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("salary")
                ));
            }
        }
        return employees;
    }
}
