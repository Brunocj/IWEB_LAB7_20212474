package org.example.lab7_iweb_20212474.model.daos;

import java.sql.*;
import java.util.ArrayList;

import org.example.lab7_iweb_20212474.model.beans.Department;
import org.example.lab7_iweb_20212474.model.beans.Employee;

public class DaoEmployee {
        public ArrayList<Employee> listarEmployee(){
            ArrayList<Employee> listaEmployees = new ArrayList<>();

            try {
                Class.forName( "com.mysql.cj.jdbc.Driver");

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            //Parametros de conexion a que base de datos me quiero unir//
            String url ="jdbc:mysql://localhost:3306/hr";
            String username = "root";
            String password = "123456";

            String sql = "SELECT " +
                    "e.employee_id AS 'idEmployee', " +
                    "CONCAT(e.last_name, ', ', e.first_name) AS 'fName', " +
                    "e.email AS 'Email', " +
                    "e.phone_number AS 'Telefono', " +
                    "e.salary AS 'salario', " +
                    "e.hire_date AS 'hireDate', " +
                    "(SELECT d.department_name FROM departments d WHERE d.department_id = e.department_id) AS 'departamento', " +
                    "(SELECT j.job_title FROM jobs j WHERE j.job_id = e.job_id) AS 'jobName', " +
                    "(SELECT CONCAT(m.last_name, ', ', m.first_name) FROM employees m WHERE m.employee_id = e.manager_id) AS 'manager' " +
                    "FROM " +
                    "employees e;";

            try (Connection conn= DriverManager.getConnection(url, username, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()){
                    Employee employee = new Employee();
                    employee.setIdEmployee(rs.getInt("idEmployee"));
                    employee.setFullNameEmployee(rs.getString("fName"));
                    employee.setEmail(rs.getString("Email"));
                    employee.setPhNumber(rs.getString("Telefono"));
                    employee.setSalary(rs.getDouble("salario"));
                    employee.setDepartment(rs.getString("departamento"));
                    employee.setManager(rs.getString("manager"));
                    employee.setHireDate(rs.getDate("hireDate"));
                    employee.setJob(rs.getString("jobName"));
                    listaEmployees.add(employee);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return listaEmployees;
        }
        public Employee devolverEmpleoyeeId(int idEmployee){
            Employee esclavo = new Employee();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Parámetros de conexión a la base de datos
            String url = "jdbc:mysql://localhost:3306/hr";
            String username = "root";
            String password = "123456";

            String sql = "SELECT " +
                    "e.employee_id AS 'idEmployee', " +
                    "CONCAT(e.last_name, ', ', e.first_name) AS 'fName', " +
                    "e.email AS 'Email', " +
                    "e.phone_number AS 'Telefono', " +
                    "e.salary AS 'salario', " +
                    "e.hire_date AS 'hireDate', " +
                    "(SELECT d.department_name FROM departments d WHERE d.department_id = e.department_id) AS 'departamento', " +
                    "(SELECT j.job_title FROM jobs j WHERE j.job_id = e.job_id) AS 'jobName', " +
                    "(SELECT CONCAT(m.last_name, ', ', m.first_name) FROM employees m WHERE m.employee_id = e.manager_id) AS 'manager' " +
                    "FROM " +
                    "employees e " +
                    "WHERE e.employee_id = ?;";

            try (Connection conn = DriverManager.getConnection(url, username, password);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, idEmployee);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        esclavo.setIdEmployee(rs.getInt("idEmployee"));
                        esclavo.setFullNameEmployee(rs.getString("fName"));
                        esclavo.setEmail(rs.getString("Email"));
                        esclavo.setPhNumber(rs.getString("Telefono"));
                        esclavo.setSalary(rs.getDouble("salario"));
                        esclavo.setDepartment(rs.getString("departamento"));
                        esclavo.setManager(rs.getString("manager"));
                        esclavo.setHireDate(rs.getDate("hireDate"));
                        esclavo.setJob((rs.getString("jobName")));
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            return esclavo;
        }
    public void editarEmployeeId(Employee employee, String jobId, int managerId, int departmentId){
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url ="jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "123456";

        String sql= "UPDATE employees SET first_name = ?, " +
                "last_name = ?, " +
                "email = ?, " +
                "phone_number = ?, " +
                "hire_date = ?, " +
                "job_id = ?, " +
                "salary = ?, " +
                "manager_id = ?, " +
                "department_id = ? " +
                "WHERE employee_id = ?;";

        String fullName = employee.getFullNameEmployee();
        String[] nameParts = fullName.split(", ");
        String nombre = nameParts[0].trim();
        String apellido = nameParts[1].trim();

        try (Connection conn= DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPhNumber());
            pstmt.setDate(5, new java.sql.Date(employee.getHireDate().getTime()));
            pstmt.setString(6, jobId);
            pstmt.setDouble(7, employee.getSalary());
            pstmt.setInt(8, managerId);
            pstmt.setInt(9, departmentId);
            pstmt.setInt(10, employee.getIdEmployee());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void borrarEmployeeId(int idEmployee){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "123456";


        String sql = "DELETE FROM employees WHERE employee_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);

             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEmployee);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void crearEmployee(Employee employee, String jobId, Integer managerId, Integer departmentId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "123456";
        String sql = "INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, job_id, salary, department_id, manager_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String fullName = employee.getFullNameEmployee();
        String[] nameParts = fullName.split(", ");
        String nombre = nameParts[0].trim();
        String apellido = nameParts[1].trim();

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, employee.getEmail());
            stmt.setString(4, employee.getPhNumber());
            stmt.setDate(5, new java.sql.Date(employee.getHireDate().getTime()));
            stmt.setString(6, jobId);
            stmt.setDouble(7, employee.getSalary());

            if (departmentId == null) {
                stmt.setNull(8, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(8, departmentId);
            }

            if (managerId == null) {
                stmt.setNull(9, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(9, managerId);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Funciones extras para mostrar listas en el formulario de editar y agregar
    public ArrayList<Employee> listarManagers(){
        ArrayList<Employee> Managers = new ArrayList<>();
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Parametros de conexion a que base de datos me quiero unir//
        String url ="jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "123456";

        String sql = "SELECT employee_id as idEmployee, CONCAT(last_name, ', ', first_name) AS fName " +
                "FROM employees " +
                "WHERE employee_id IN (SELECT DISTINCT manager_id FROM employees WHERE manager_id IS NOT NULL);";

        try (Connection conn= DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()){
                Employee employee = new Employee();
                employee.setIdEmployee(rs.getInt("idEmployee"));
                employee.setFullNameEmployee(rs.getString("fName"));
                Managers.add(employee);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Managers;
    }

    public ArrayList<Department> listarDepartments(){
        ArrayList<Department> listaDepartamentos = new ArrayList<>();

        try {
            Class.forName( "com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Parametros de conexion a que base de datos me quiero unir//
        String url ="jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "123456";

        String sql = "SELECT * FROM departments";

        try (Connection conn= DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()){
                Department dep = new Department();
                dep.setDepartmentId(rs.getInt(1));
                dep.setName(rs.getString(2));
                listaDepartamentos.add(dep);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaDepartamentos;
    }

}

