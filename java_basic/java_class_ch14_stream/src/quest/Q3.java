package quest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Q3 {
    record Employee(String name, String team) {}

    public static void main(String[] args) {
        List<Employee> employees = List.of(
            new Employee("Kim", "Backend"),
            new Employee("Lee", "Frontend"),
            new Employee("Park", "Backend")
        );

        Map<String, List<Employee>> grouped = employees.stream()
            .collect(Collectors.groupingBy(Employee::team));

        System.out.println(grouped);
    }
}
