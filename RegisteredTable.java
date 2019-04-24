package com.williams.kailyn;

import java.sql.*;
import java.util.Scanner;

public class RegisteredTable implements Exists {

    public void add(Statement statement, String ssn, String code, String year, String semester) {
        if (!isvalidSSN(ssn)) {
            System.out.println("Invalid SSN (Numeric Values only / 9-digits)");
            return;
        }

        if (registeredExists(statement, ssn, code, year, semester)) {
            System.out.println("Registered Course already existed");
            return;
        }

        try {
            String courseQuery = "insert into course (code) values ('" + code + "')";
            statement.executeUpdate(courseQuery);
            String studentQuery = "insert into student (ssn) values ('" + ssn + "')";
            statement.executeUpdate(studentQuery);

            String registeredQuery = "insert into registered (ssn,code,year,semester) values('" + ssn + "', '" + code + "','" + year + "','" + semester + "')";
            statement.executeUpdate(registeredQuery);


            System.out.println("Registered Course Successfully Added!");

            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void check(Statement statement, String ssn) {
        if (!isvalidSSN(ssn)) {
            System.out.println("Invalid SSN (Numeric Values only / 9-digits)");
            return;
        }
        try {
            if (!exists(statement, "Registered", "ssn", ssn)) {
                System.out.println("Student does not exist. Cannot check for registration");
            } else {
                String query = "select * from registered where ssn='" + ssn + "'";

                ResultSet resultSet = statement.executeQuery(query);
                System.out.println("All Registered Course for Student- SSN: " + ssn + ": \n");
                while (resultSet.next()) {

                    String courseCode = resultSet.getString("CODE");
                    int year = resultSet.getInt("YEAR");
                    String semester = resultSet.getString("SEMESTER");

                    System.out.println(courseCode.trim() + "  " + year + "  " + semester.trim() + " \n");

                }
                resultSet.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void drop(Statement statement, String ssn, String year, String semester) {
        if (!isvalidSSN(ssn)) {
            System.out.println("Invalid SSN (Numeric Values only / 9-digits)");
            return;
        }

        try {
            if (!registeredExists(statement, ssn, year, semester)) {
                System.out.println("Registered Course does'nt exist");
            } else {


                String deleteQuery = "";


                //drop from registered
                deleteQuery = "delete from registered where ssn='" + ssn + "' and year='" + year + "' and semester='" + semester + "'";
                statement.executeUpdate(deleteQuery);

                deleteQuery = "delete from student where ssn='" + ssn + "'";
                statement.executeUpdate(deleteQuery);

                System.out.println("Drop Successful!");

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void uploadGrades(Connection conn,Statement statement, String code, String year, String semester) {
        try {
            if ((!exists(statement, "Registered", "code", code) &&
                    exists(statement, "Registered", "year", year) &&
                    exists(statement, "Registered", "semester", semester))) {
                System.out.println("No students to upload grades too");
                return;
            } else {
                String query = "select ssn ,code, year, semester  from registered where code='" + code + "' and year='" + year + "' and semester='" + semester + "'";
                ResultSet resultSet = statement.executeQuery(query);

                Scanner keyboard = new Scanner(System.in);
                while (resultSet.next()) {
                    String ssn = resultSet.getString("SSN");

                    System.out.println("Enter grade for Student: SSN- " + ssn);


                    char grade= isValidGrade(keyboard);
                    char upperCaseGrade= Character.toUpperCase(grade);

                    Statement st= conn.createStatement();
                    String updateQuery="update registered set grade ='"+upperCaseGrade+"' where ssn='"+ssn+"' and code='"+code+"' and year='"+ year+"' and semester='"+semester+"'";
                   st.executeUpdate(updateQuery);
                    System.out.println("Upload Successful!");
                    System.out.println("");

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void checkGrades(Statement statement, String code, String year, String semester) {

        try {
            if ((!exists(statement, "Registered", "code", code) &&
                    exists(statement, "Registered", "year", year) &&
                    exists(statement, "Registered", "semester", semester))) {
                System.out.println("No students to upload grades too");
                return;
            } else {
                String query = "select ssn ,code, year, semester,grade  from registered where code='" + code + "' and year='" + year + "' and semester='" + semester + "'";
                ResultSet resultSet = statement.executeQuery(query);
                System.out.println("Grade for Students in "+ code+" "+ year+" "+ semester+": ");

                Scanner keyboard = new Scanner(System.in);
                while (resultSet.next()) {
                    String ssn = resultSet.getString("SSN");
                    String grade= resultSet.getString("GRADE");
                    System.out.println("SSN: "+ ssn);
                    System.out.println("Grade: "+ grade);
                    System.out.println();

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //If registered course exist in table return true
    public boolean registeredExists(Statement statement, String ssn, String code, String year, String semester) {
        return (exists(statement, "Registered", "ssn", ssn) &&
                exists(statement, "Registered", "code", code) &&
                exists(statement, "Registered", "year", year) &&
                exists(statement, "Registered", "semester", semester));

    }

    public boolean registeredExists(Statement statement, String ssn, String year, String semester) {
        return (exists(statement, "Registered", "ssn", ssn) &&
                exists(statement, "Registered", "year", year) &&
                exists(statement, "Registered", "semester", semester));

    }


    public boolean exists(Statement statement, String table, String attribute, String value) {
        String query = "select " + attribute + " from " + table + " where " + attribute + "='" + value + "'";
        try {
            ResultSet rs = statement.executeQuery(query);

            String resultString = "";
            while (rs.next()) {
                resultString = rs.getString(attribute).trim();
            }
            rs.close();

            if (resultString.equals(value))
                return true;


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private boolean isvalidSSN(String ssn) {
        if (isNumeric(ssn) && ssn.length() == 9) return true;
        else return false;
    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public char isValidGrade( Scanner keyboard) {

        while (true) {

            char grade= keyboard.next().charAt(0);
            if (grade >='a' && grade <='f' && grade!='e'|| grade>='A' && grade<='F' && grade!='E') {
                return grade;
            }
            System.out.println("Invalid Grade. Try Again!");

        }
    }



}
