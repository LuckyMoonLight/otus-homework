package ru.otus.java.pro.luckymoonlight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.pro.luckymoonlight.services.EmployeeProxyService;



public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        EmployeeProxyService employeeProxyService = new EmployeeProxyService();
        try {
            employeeProxyService.createEmployees();
            employeeProxyService.updateAllSalary();
            employeeProxyService.printAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}