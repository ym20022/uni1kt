import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Employee {
    private String fullName;
    private int age;
    private double salary;

    public Employee(String fullName, int age, double salary) {
        this.fullName = fullName;
        this.age = age;
        this.salary = salary;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "ФИО: " + fullName + ", Возраст: " + age + ", ЗП: " + salary;
    }
}

class Department {
    private String name;
    private List<Employee> employees;

    public Department(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public double getTotalSalary() {
        return employees.stream().mapToDouble(Employee::getSalary).sum();
    }

    public int getEmployeeCount() {
        return employees.size();
    }

    @Override
    public String toString() {
        return "Отдел: " + name + ", Кол-во сотрудников: " + getEmployeeCount();
    }
}

class Company {
    private List<Department> departments;

    public Company() {
        departments = new ArrayList<>();
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public void removeDepartment(Department department) {
        departments.remove(department);
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void showDepartments() {
        for (Department department : departments) {
            System.out.println(department);
            System.out.println("Сотрудники в отделе:");
            for (Employee employee : department.getEmployees()) {
                System.out.println(employee);
            }
            System.out.println("Сумма ЗП в отделе: " + department.getTotalSalary());
            System.out.println();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Company company = new Company();

        while (true) {
            System.out.println("1. Добавить отдел");
            System.out.println("2. Удалить отдел");
            System.out.println("3. Добавить сотрудника в отдел");
            System.out.println("4. Удалить сотрудника из отдела");
            System.out.println("5. Показать все отделы");
            System.out.println("6. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Очистка буфера

            switch (choice) {
                case 1:
                    System.out.print("Введите название отдела: ");
                    String deptName = scanner.nextLine();
                    company.addDepartment(new Department(deptName));
                    break;

                case 2:
                    System.out.print("Введите название отдела для удаления: ");
                    String removeDeptName = scanner.nextLine();
                    company.removeDepartment(new Department(removeDeptName));
                    break;

                case 3:
                    System.out.print("Введите название отдела: ");
                    String empDeptName = scanner.nextLine();
                    Department departmentToAddEmp = company.getDepartments().stream()
                            .filter(d -> d.getName().equals(empDeptName)).findFirst().orElse(null);
                    if (departmentToAddEmp != null) {
                        System.out.print("Введите ФИО сотрудника: ");
                        String fullName = scanner.nextLine();
                        System.out.print("Введите возраст: ");
                        int age = scanner.nextInt();
                        System.out.print("Введите ЗП: ");
                        double salary = scanner.nextDouble();
                        scanner.nextLine();  // Очистка буфера
                        departmentToAddEmp.addEmployee(new Employee(fullName, age, salary));
                    } else {
                        System.out.println("Отдел не найден.");
                    }
                    break;

                case 4:
                    System.out.print("Введите название отдела: ");
                    String empDeptRemoveName = scanner.nextLine();
                    Department departmentToRemoveEmp = company.getDepartments().stream()
                            .filter(d -> d.getName().equals(empDeptRemoveName)).findFirst().orElse(null);
                    if (departmentToRemoveEmp != null) {
                        System.out.print("Введите ФИО сотрудника для удаления: ");
                        String empFullName = scanner.nextLine();
                        Employee employeeToRemove = departmentToRemoveEmp.getEmployees().stream()
                                .filter(e -> e.getFullName().equals(empFullName)).findFirst().orElse(null);
                        if (employeeToRemove != null) {
                            departmentToRemoveEmp.removeEmployee(employeeToRemove);
                        } else {
                            System.out.println("Сотрудник не найден.");
                        }
                    } else {
                        System.out.println("Отдел не найден.");
                    }
                    break;

                case 5:
                    company.showDepartments();
                    break;

                case 6:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }
}
