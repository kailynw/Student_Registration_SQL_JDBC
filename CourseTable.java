package com.williams.kailyn;

import java.sql.ResultSet;
import java.sql.Statement;

public class CourseTable implements Exists {

    public void add(Statement statement, String courseCode, String title) {

        if(exists(statement, "course", "code", courseCode)|| exists(statement,"course", "title", title)){
            System.out.println("Course already added");
            return;
        }
        try {
            String query="insert into course values('"+courseCode+ "','" + title + "')";
            statement.executeUpdate(query);

            System.out.println("Course Added Successfully!");

            statement.close();
        }catch(Exception e){
            System.out.println("Insert error");
        }
    }

    public void delete(Statement statement, String courseCode){
        try {
            if (!exists(statement, "course", "code", courseCode)) {
                System.out.println("Course does'nt exist");
            }
            else {
                String deleteQuery="";
                if (exists(statement, "registered", "code", courseCode)) {
                    deleteQuery = "delete from registered where code='" + courseCode + "'";
                    statement.executeUpdate(deleteQuery);
                }

                deleteQuery= "delete from course where code= '"+courseCode+"'";
                statement.executeUpdate(deleteQuery);
                System.out.println("Deletion Successful!");
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Check if Value in table exists
    public boolean exists(Statement statement, String table, String attribute, String value) {
        String query = "select " + attribute + " from " + table + " where " + attribute + "='" + value + "'";
        try {
            ResultSet rs = statement.executeQuery(query);

            String resultString="";
            while (rs.next()) {
                resultString= rs.getString(attribute).trim();
            }
            rs.close();

            if(resultString.equals(value))
                return true;


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}
