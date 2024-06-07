<%@ page import="org.example.lab7_iweb_20212474.model.beans.Employee" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.lab7_iweb_20212474.model.beans.Job" %>
<%@ page import="org.example.lab7_iweb_20212474.model.beans.Department" %><%--
  Created by IntelliJ IDEA.
  User: bruno
  Date: 6/06/2024
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Employee employee = (Employee) request.getAttribute("empleadoeditar");
    ArrayList<Job> listaJ = (ArrayList<Job>) request.getAttribute("listajobs");
    ArrayList<Employee> listaM = (ArrayList<Employee>) request.getAttribute("listamanagers");
    ArrayList<Department> listaD = (ArrayList<Department>) request.getAttribute("listadepartments");

%>
<html>
<head>
    <title>Editar empleado</title>
</head>
<body>
<h1>Editar empleado</h1>
<form style="display: flex; flex-direction: column; width: 50vh; gap: 10px; padding: 10px" method="POST" action="<%=request.getContextPath()%>/home?action=actualizarEmployee">
    <input type ="hidden" class ="form-control" name ="id" value ="<%=employee.getIdEmployee()%>">
    <label>Nombre (Mantener el formato: Apellido, Nombre)</label>
    <input type="text" id ="fName" name ="fName" value="<%=employee.getFullNameEmployee()%>">
    <label>Email</label>
    <input type="text" id ="email" name ="email" value="<%=employee.getEmail()%>">
    <label>Teléfono celular</label>
    <input type="text" id ="phNumber" name ="phNumber"  value="<%=employee.getPhNumber()%>">
    <label>Salario</label>
    <input type="text" id="salary" name="salary" value="<%=employee.getSalary()%>">
    <label>Fecha de contratación</label> <!--Calendario-->
    <input type="date" name="hireDate" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(employee.getHireDate()) %>">
    <label>Trabajo</label><!--Lista-->
    <select id="jobId" name="jobId">
        <%
            for (Job job : listaJ){
        %>
        <option value="<%=job.getJobId()%>" <%= job.getTitle().equals(employee.getJob()) ? "selected" : "" %>><%=job.getTitle()%></option>
        <%
            }
        %>
    </select>

    <label>Departamento</label><!--Lista-->
    <select id="departmentId" name="departmentId">
        <%
            for (Department dep : listaD){
        %>
        <option value="<%=dep.getDepartmentId()%>" <%= dep.getName().equals(employee.getDepartment()) ? "selected" : "" %>><%=dep.getName()%></option>
        <%
            }
        %>
    </select>


    <label>Manager</label><!--Lista-->

    <select id="managerId" name="managerId">
        <%
            for (Employee manager : listaM){
        %>
        <option value="<%=manager.getIdEmployee()%>" <%= manager.getFullNameEmployee().equals(employee.getManager()) ? "selected" : "" %>><%=manager.getFullNameEmployee()%></option>
        <%
            }
        %>
    </select>
    <div style="display: flex; justify-content: center; gap: 10px; margin-top: 20px;">
        <button onclick="window.location.href='<%=request.getContextPath()%>/home';" style="width: 50%;">CANCELAR</button>
        <button type="submit" style="width: 50%;">GUARDAR</button>
    </div>
</form>


</body>
</html>
