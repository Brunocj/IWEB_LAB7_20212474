package org.example.lab7_iweb_20212474.model.daos;

import org.example.lab7_iweb_20212474.model.beans.Employee;
import org.example.lab7_iweb_20212474.model.beans.Job;

import java.sql.*;
import java.util.ArrayList;

public class DaoJobs {
    public ArrayList<Job> listarJobs(){
        ArrayList<Job> listaJobs = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "123456";

        String sql = "select * from jobs";


        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Job job = new Job();
                job.setJobId(rs.getString(1));
                job.setTitle(rs.getString(2));
                job.setMinSalary(rs.getInt(3));
                job.setMaxSalary(rs.getInt(4));

                listaJobs.add(job);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaJobs;
    }
    public void AddJob(String jobId, String jobTitle, int minSalary, int maxSalary){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "123456";

        String sql = "insert into jobs (job_id, job_title, min_salary,max_salary) values (?,?,?,?)";

        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setString(1,jobId);
            pstmt.setString(2,jobTitle);
            pstmt.setInt(3,minSalary);
            pstmt.setInt(4,maxSalary);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Job devolverTrabajoId(String idJob){
        Job job = new Job();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "123456";

        String sql = "select * from jobs where job_id = ?";


        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,idJob);

            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                    job.setJobId(rs.getString(1));
                    job.setTitle(rs.getString(2));
                    job.setMinSalary(rs.getInt(3));
                    job.setMaxSalary(rs.getInt(4));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return job;
    }
}
