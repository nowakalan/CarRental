package pl.zdjavapol140.carrental.utils;

import pl.zdjavapol140.carrental.model.Employee;
import pl.zdjavapol140.carrental.model.Job;

import java.util.Random;

public class EmployeeGenerator {

    private static final String[] FIRST_NAMES = {"John", "Jane", "David", "Emily", "Michael", "Sarah"};
    private static final String[] LAST_NAMES = {"Doe", "Smith", "Johnson", "Davis", "Wilson", "Brown"};

    public static Employee generateRandomEmployee() {
        Random random = new Random();
        Employee employee = new Employee();

        employee.setFirstName(FIRST_NAMES[random.nextInt(FIRST_NAMES.length)]);
        employee.setLastName(LAST_NAMES[random.nextInt(LAST_NAMES.length)]);
        employee.setJob(Job.values()[random.nextInt(Job.values().length)]);
        employee.setBranch(null);
        return employee;
    }

}
