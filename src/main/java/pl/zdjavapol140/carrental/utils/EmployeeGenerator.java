package pl.zdjavapol140.carrental.utils;

import pl.zdjavapol140.carrental.model.Employee;
import pl.zdjavapol140.carrental.model.Job;
import pl.zdjavapol140.carrental.model.Role;

import java.util.Random;

public class EmployeeGenerator {

    private static final String[] FIRST_NAMES = {"John", "Jane", "David", "Emily", "Michael", "Sarah", "Tomasz", "Wojciech", "Robert", "Marcin"};
    private static final String[] LAST_NAMES = {"Doe", "Smith", "Johnson", "Davis", "Wilson", "Brown", "Malik", "Sobczak", "Kusy"};
    private static final String[] EMAIL_DOMAINS = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "example.com", "onet.pl", "interia.pl", "money.pl", "bankier.pl"};

    public static Employee generateRandomEmployee() {
        Random random = new Random();
        Employee employee = new Employee();


        employee.setFirstName(FIRST_NAMES[random.nextInt(FIRST_NAMES.length)]);
        employee.setLastName(LAST_NAMES[random.nextInt(LAST_NAMES.length)]);
        employee.setEmail(employee.getFirstName().toLowerCase() + "." + employee.getLastName().toLowerCase() + "@" + EMAIL_DOMAINS[random.nextInt(EMAIL_DOMAINS.length)]);
        employee.setPassword(employee.getFirstName().toLowerCase());
        employee.setRole(Role.ROLE_EMPLOYEE);

        employee.setJob(Job.values()[random.nextInt(Job.values().length)]);
        employee.setBranch(null);
        return employee;
    }

}
