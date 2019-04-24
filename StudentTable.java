package com.williams.kailyn;

import java.sql.*;

public class StudentTable implements Exists {

    public void add(Statement statement, String ssn, String name, String address, String major){
        if(!isvalidSSN(ssn)){
            System.out.println("Invalid SSN (Numeric Values only / 9-digits)");
            return;
        }

        if(studentExists(statement, ssn, name, address)){
            System.out.println("Student already exists");
            return;
        }

        try{
            String query= "insert into student values('"+ssn+"', '"+ name+"','"+ address+"','"+ major+"')";
            statement.executeUpdate(query);
            System.out.println("Student Successfully Added!");

            statement.close();
        }catch (Exception e){
            System.out.println("Insert Error");
        }
    }

    public void delete(Statement statement, String ssn){
        if(!isvalidSSN(ssn)){
            System.out.println("Invalid SSN (Numeric Values only / 9-digits)");
            return;
        }

        try{
            if(!exists(statement, "student", "ssn", ssn)){
                System.out.println("Student does'nt exist");
            }
            else{
                String deleteQuery="";
                if(exists(statement,"registered", "ssn", ssn)){
                    deleteQuery= "delete from registered where ssn='"+ ssn + "'";
                    statement.executeUpdate(deleteQuery);
                }

                deleteQuery="delete from student where ssn= '"+ ssn+ "'";
                statement.executeUpdate(deleteQuery);
                System.out.println("Deletion Successful!");
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //If student exist in table return true
    public boolean studentExists(Statement statement, String ssn, String name, String address){

        return (exists(statement,"Student","ssn",ssn)||
           exists(statement,"Student", "address",address)||
           exists(statement, "Student", "name", name));




    }



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

    private boolean isvalidSSN(String ssn) {
        if(isNumeric(ssn) && ssn.length()==9) return true;
        else return false;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }




}
