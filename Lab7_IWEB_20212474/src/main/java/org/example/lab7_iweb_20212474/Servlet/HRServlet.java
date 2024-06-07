package org.example.lab7_iweb_20212474.Servlet;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.lab7_iweb_20212474.model.beans.Department;
import org.example.lab7_iweb_20212474.model.beans.Employee;
import org.example.lab7_iweb_20212474.model.beans.Job;
import org.example.lab7_iweb_20212474.model.daos.DaoEmployee;
import org.example.lab7_iweb_20212474.model.daos.DaoJobs;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@WebServlet(name = "Lab7IWEB", value = "/home")
public class HRServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null? "listaEmployees" : request.getParameter("action");
        String vista;
        RequestDispatcher rd;
        DaoEmployee daoEmployee = new DaoEmployee();
        switch (action){
            case "listaEmployees":

                ArrayList<Employee> listaEmployees= daoEmployee.listarEmployee();
                ArrayList<Employee> listaManagers = daoEmployee.listarManagers();
                request.setAttribute("listaempleados", listaEmployees);
                request.setAttribute("listamanagers", listaManagers);
                vista = "listaEmpleados.jsp";
                rd = request.getRequestDispatcher(vista);
                rd.forward(request,response);
                break;
            case "editarEmployee":
                int idEd = Integer.parseInt(request.getParameter("id"));
                Employee employeeEd = daoEmployee.devolverEmpleoyeeId(idEd);

                //Lista jobs
                DaoJobs daoJobs = new DaoJobs();
                ArrayList<Job> listaJobs = daoJobs.listarJobs();
                //Lista manager
                ArrayList<Employee> listaManagersE = daoEmployee.listarManagers();
                //Lista department
                ArrayList<Department> listaDepartments = daoEmployee.listarDepartments();

                request.setAttribute("empleadoeditar", employeeEd);
                request.setAttribute("listajobs", listaJobs);
                request.setAttribute("listamanagers", listaManagersE);
                request.setAttribute("listadepartments", listaDepartments);


                vista = "editarEmpleado.jsp";
                rd = request.getRequestDispatcher(vista);
                rd.forward(request,response);

                break;
            case "crearEmployee":
                //Lista jobs
                DaoJobs daoJobsCr = new DaoJobs();
                ArrayList<Job> listaJobsCr = daoJobsCr.listarJobs();
                //Lista manager
                ArrayList<Employee> listaManagersCr = daoEmployee.listarManagers();
                //Lista department
                ArrayList<Department> listaDepartmentsCr = daoEmployee.listarDepartments();

                request.setAttribute("listajobs", listaJobsCr);
                request.setAttribute("listamanagers", listaManagersCr);
                request.setAttribute("listadepartments", listaDepartmentsCr);


                vista = "crearEmpleado.jsp";
                rd = request.getRequestDispatcher(vista);
                rd.forward(request,response);

                break;
            case "eliminarEmployee":
                int id = Integer.parseInt(request.getParameter("id"));
                Employee employee = daoEmployee.devolverEmpleoyeeId(id);

                if(employee != null){
                    daoEmployee.borrarEmployeeId(id);
                }
                response.sendRedirect(request.getContextPath() + "/home");
                break;
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        DaoEmployee daoEmployee = new DaoEmployee();
        switch (action){
            case "actualizarEmployee":
                Employee employeeAct = new Employee();
                employeeAct.setIdEmployee(Integer.parseInt(request.getParameter("id")));
                employeeAct.setFullNameEmployee(request.getParameter("fName"));
                employeeAct.setEmail(request.getParameter("email"));
                employeeAct.setPhNumber(request.getParameter("phNumber"));
                employeeAct.setSalary(Double.parseDouble(request.getParameter("salary")));
                String hireDateStr = request.getParameter("hireDate");
                Date hireDate = null;
                if (hireDateStr != null && !hireDateStr.isEmpty()) {
                    try {
                        hireDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(hireDateStr);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
                employeeAct.setHireDate(hireDate);
                String jobIdAct = request.getParameter("jobId");
                int deptIdAct = Integer.parseInt(request.getParameter("departmentId"));
                int managerId = Integer.parseInt(request.getParameter("managerId"));
                boolean actValid = true;
                if(jobIdAct.length()>10){
                    actValid = false;
                }
                if(actValid){
                    daoEmployee.editarEmployeeId(employeeAct, jobIdAct, managerId, deptIdAct);
                    response.sendRedirect(request.getContextPath() + "/home");
                }

                break;
            case "nuevoEmployee":
                Employee employeeCr = new Employee();
                employeeCr.setFullNameEmployee(request.getParameter("fName"));
                employeeCr.setEmail(request.getParameter("email"));
                employeeCr.setPhNumber(request.getParameter("phNumber"));
                employeeCr.setSalary(Double.parseDouble(request.getParameter("salary")));
                String hireDateStrCr = request.getParameter("hireDate");
                Date hireDateCr = null;
                if (hireDateStrCr != null && !hireDateStrCr.isEmpty()) {
                    try {
                        hireDateCr = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(hireDateStrCr);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
                employeeCr.setHireDate(hireDateCr);
                String jobIdCr = request.getParameter("jobId");
                int deptIdCr = Integer.parseInt(request.getParameter("departmentId"));
                int managerIdCr = Integer.parseInt(request.getParameter("managerId"));
                boolean actValidCr = true;
                if(jobIdCr.length()>10){
                    actValidCr = false;
                }
                if(actValidCr){
                    daoEmployee.crearEmployee(employeeCr, jobIdCr, managerIdCr, deptIdCr);
                    response.sendRedirect(request.getContextPath() + "/home");
                }
                break;

        }
    }

}
