<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.lab7_iweb_20212474.model.beans.Employee" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<Employee> lista = (ArrayList<Employee>) request.getAttribute("listaempleados");
    ArrayList<Employee> listaManagers = (ArrayList<Employee>) request.getAttribute("listamanagers");
%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Lista de Empleados</title>
</head>
<body>
<h1>Lista de Empleados</h1>
<button onclick="window.location.href ='${pageContext.request.contextPath}/home?action=crearEmployee';">Crear Empleado</button>

<%
    if (lista == null || lista.isEmpty()) {
%>
<p>No hay empleados</p>
<%
} else {
%>
<table border="1">
    <thead>
    <tr>
        <th>Nombre</th>
        <th>Email</th>
        <th>Teléfono celular</th>
        <th>Salario</th>
        <th>Fecha de contratación</th>
        <th>Trabajo</th>
        <th>Departamento</th>
        <th>Manager</th>
        <th colspan="2">Acciones</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Employee employee : lista) {
            boolean Actions = false;
            for (Employee manager : listaManagers) {
                if (employee.getIdEmployee() == manager.getIdEmployee()) {
                    Actions = true;
                    break;
                }
            }
    %>
    <tr>
        <td><%=employee.getFullNameEmployee() %></td>
        <td><%=employee.getEmail() %></td>
        <td><%=employee.getPhNumber() %></td>
        <td><%=employee.getSalary() %></td>
        <td><%=employee.getHireDate() %></td>
        <td><%=employee.getJob() %></td>
        <td><%=employee.getDepartment() %></td>
        <td><%=employee.getManager() %></td>
        <%
            if (!Actions) {
        %>
        <td><button onclick="window.location.href ='${pageContext.request.contextPath}/home?action=editarEmployee&id=<%=employee.getIdEmployee()%>';">Editar</button></td>
        <td><a onclick="return confirm('¿Quieres eliminar a este empleado?')" href="<%=request.getContextPath()%>/home?action=eliminarEmployee&id=<%= employee.getIdEmployee() %>">Eliminar</a></td>
        <%
        } else {
        %>
        <td>Editar</td>
        <td>Eliminar</td>
        <%
            }
        %>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<%
    }
%>
</body>
</html>
