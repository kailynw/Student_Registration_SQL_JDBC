package com.williams.kailyn;
import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.*;
import java.util.*;
public class Main {
    public static Scanner scan= new Scanner(System.in);
    public static  CourseTable course= new CourseTable();
    public static StudentTable student= new StudentTable();
    public static RegisteredTable registered= new RegisteredTable();

    public static void main(String[] args) {

        Database_Connection db = new Database_Connection();

        try {

            displayMenu();
            while (true) {
                Connection conn = db.validateUserConnection();
                Statement st = conn.createStatement();
                Scanner keyboard= new Scanner(System.in);

                int number= 0;
                number= validateNumber(number);

                switch (number){
                    case 1:
                        addCourse(st);
                        st.close();
                        conn.close();
                        break;
                    case 2:
                        deleteCourse(st);
                        st.close();
                        conn.close();
                        break;
                    case 3:
                        addCourse(st);
                        st.close();
                        conn.close();
                        break;
                    case 4:
                        deleteStudent(st);
                        st.close();
                        conn.close();
                        break;
                    case 5:
                        registerCourse(st);
                        st.close();
                        conn.close();
                        break;
                    case 6:
                        dropCourse(st);
                        st.close();
                        conn.close();
                        break;
                    case 7:
                        checkRegistration(st);
                        st.close();
                        conn.close();
                        break;
                    case 8:
                        uploadGrades(conn, st);
                        st.close();
                        conn.close();
                        break;
                    case 9:
                        checkGrades(st);
                        st.close();
                        conn.close();
                        break;
                    case 10:
                        System.out.println("\n Have a great day!  :)");
                        System.exit(0);
                    default:
                        System.out.println("Invalid Input\n");

                }

            }




        } catch (Exception e) {
            System.out.println("Error");
        }


    }
    public static void displayMenu(){
        System.out.println("******************************************************************\n");
        System.out.println("***                                                            ***\n");
        System.out.println("***          Welcome to Online Registration System             ***\n");
        System.out.println("***                                                            ***\n");
        System.out.println("******************************************************************\n");

        System.out.println("1. Add a course\n");
        System.out.println("2. Delete a course\n");
        System.out.println("3. Add a student\n");
        System.out.println("4. Delete a student\n");
        System.out.println("5. Register a course\n");
        System.out.println("6. Drop a course\n");
        System.out.println("7. Check student registration\n");
        System.out.println("8. Upload grades\n");
        System.out.println("9. Check grade\n");
        System.out.println("10. Quit\n");

    }
    public static void checkGrades(Statement st){
        System.out.println("Enter Course Code: ");
        String courseCode= scan.next();

        System.out.println("Enter Year: ");
        String year = scan.next();

        System.out.println("Enter Semester: ");
        String semester= scan.next();

        registered.checkGrades(st, courseCode, year, semester);
    }

    public static void uploadGrades(Connection conn, Statement st){
        System.out.println("Enter Course Code: ");
        String courseCode= scan.next();

        System.out.println("Enter Year: ");
        String year= scan.next();

        System.out.println("Enter Semester: ");
        String semester= scan.next();

        registered.uploadGrades(conn, st, courseCode,year, semester);

    }

    public static void checkRegistration(Statement st){
        System.out.println("Enter SSN: ");
        String ssn= scan.next();

        registered.check(st,ssn);
    }

    public static void registerCourse(Statement st){
        System.out.println("Enter SSN: ");
        String ssn= scan.next();

        System.out.println("Enter Course Code: ");
        String courseCode= scan.next();

        System.out.println("Enter Year: ");
        String year= scan.next();

        System.out.println("Enter Semester: ");
        String semester= scan.next();

        registered.add(st, ssn,courseCode,year, semester);
    }
    public static void dropCourse(Statement st){
        System.out.println("Enter SSN: ");
        String ssn= scan.next();

        System.out.println("Enter Year: ");
        String year= scan.next();

        System.out.println("Enter Semester: ");
        String semester=scan.next();

        registered.drop(st,ssn, year, semester);
    }

    public static void addCourse(Statement st){

        System.out.println("Enter Course Code: ");
        String courseCode= scan.next();

        System.out.println("Enter Course Name: ");
        String title= scan.next();

        course.add(st,courseCode,title );


    }

    public static void deleteCourse(Statement st){
        System.out.println("Enter Course Code: ");
        String courseCode= scan.next();
        course.delete(st,courseCode);
    }

    public static void addStudent(Statement st){
        System.out.println("Enter SSN: ");
        String ssn= scan.next();

        System.out.println("Enter Student Name: ");
        String name= scan.next();

        System.out.println("Enter Address: ");
        String address= scan.next();

        System.out.println("Enter Major: ");
        String major= scan.next();

       student.add(st,ssn,name, address,major);
    }

    public static void deleteStudent(Statement st){
        System.out.println("Enter SSN: ");
        String ssn= scan.next();

        student.delete(st,ssn);

    }

    public static int validateNumber(int number) {

        while (true) {
            Scanner keyboard = new Scanner(System.in);
            try {
                System.out.println("Enter a Number: ");
                number = keyboard.nextInt();

                if (number >= 1 && number <= 10) {
                    return number;
                }
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }
    }



}
