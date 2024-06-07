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
    ArrayList<Job> listaJ = (ArrayList<Job>) request.getAttribute("listajobs");
    ArrayList<Employee> listaM = (ArrayList<Employee>) request.getAttribute("listamanagers");
    ArrayList<Department> listaD = (ArrayList<Department>) request.getAttribute("listadepartments");

%>
<html>
<head>
    <title>Crear empleado</title>
</head>
<body>
<h1>Crear empleado</h1>
<form style="display: flex; flex-direction: column; width: 50vh; gap: 10px; padding: 10px" method="POST" action="<%=request.getContextPath()%>/home?action=nuevoEmployee">
    <label><span style="color: red">O</span>Nombre (Mantener el formato: Apellido, Nombre)<span style="color: red">O</span></label>
    <input type="text" id ="fName" name ="fName" placeholder="Ingrese el nombre completo del empleado (Apellido, Nombre): ">
    <label><span style="color: red">O</span>Email<span style="color: red">O</span></label>
    <input type="text" id ="email" name ="email" placeholder="Ingrese el email del empleado">
    <label>Teléfono celular</label>
    <input type="text" id ="phNumber" name ="phNumber"  placeholder="Ingrese el numero telefónico del empleado (###.###.###):">
    <label>Salario</label>
    <input type="text" id="salary" name="salary" placeholder="Ingrese un salario del empleado:">
    <label><span style="color: red">O</span>Fecha de contratación<span style="color: red">O</span></label> <!--Calendario-->
    <input type="date" name="hireDate">
    <label><span style="color: red">O</span>Trabajo<span style="color: red">O</span></label><!--Lista-->
    <select id="jobId" name="jobId">
        <option value="NULL">---</option>
        <%
            for (Job job : listaJ){
        %>
        <option value="<%=job.getJobId()%>"><%=job.getTitle()%></option>
        <%
            }
        %>
    </select>

    <label>Departamento</label><!--Lista-->
    <select id="departmentId" name="departmentId">
        <option value="NULL">---</option>
        <%
            for (Department dep : listaD){
        %>
        <option value="<%=dep.getDepartmentId()%>"><%=dep.getName()%></option>
        <%
            }
        %>
    </select>


    <label>Manager</label><!--Lista-->

    <select id="managerId" name="managerId">
        <option value="NULL">---</option>
        <%
            for (Employee manager : listaM){
        %>
        <option value="<%=manager.getIdEmployee()%>" ><%=manager.getFullNameEmployee()%></option>
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
