/* 
 * CSCI3170 Project
 * usage: java -classpath ./mysql-jdbc.jar:./ entry.java
 * our group's MySQL account: Group10 (password: CSCI3170)
*/
import java.io.*;
import java.sql.*;

public class entry {
    public static int next_menu = 0;

    public static String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db10?autoReconnect=true&useSSL=false";
    public static String dbUsername = "Group10";
    public static String dbPassword = "CSCI3170";
    public static Connection conn;

    public static void conn_to_db()
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbAddress,dbUsername,dbPassword);
        } catch(ClassNotFoundException e){
            System.out.println("[Error]: Java MySQL DB Driver not found!!");
            System.exit(0);
        } catch(SQLException e){
            System.out.println(e);
        }
        //conn = DriverManager.getConnection("jdbc:mysql://projgw.cse.cuhk.edu.hk:2712/Group10?autoReconnect=true&useSSL=false","Group10","CSCI3170");
    }

    /* Administrator */
    public static void create_table(){
        /*
        This function creates all tables for the
        sales system in the MySQL DBMS based on the
        relational schema given.
        */
        //TODO: implement create all tables
        System.out.print("Processing...");
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE category (cID INT NOT NULL, cName VARCHAR(20) NOT NULL, PRIMARY KEY (cID))");
            stmt.executeUpdate("CREATE TABLE manufacturer (mID INT NOT NULL, mName VARCHAR(20) NOT NULL, mAddress VARCHAR(50) NOT NULL, mPhoneNumber INT NOT NULL, PRIMARY KEY (mID))");
            stmt.executeUpdate("CREATE TABLE part (pID INT NOT NULL, pName VARCHAR(20) NOT NULL, pPrice INT NOT NULL, mID INT NOT NULL, cID INT NOT NULL, pWarrantyPeriod INT NOT NULL, pAvailableQuantity INT NOT NULL, PRIMARY KEY (pID), FOREIGN KEY (cID) REFERENCES category (cID), FOREIGN KEY (mID) REFERENCES manufacturer (mID))");
            stmt.executeUpdate("CREATE TABLE salesperson (sID INT NOT NULL, sName VARCHAR(20) NOT NULL, sAddress VARCHAR(50) NOT NULL, sPhoneNumber INT NOT NULL, sExperience INT NoT NULL, PRIMARY KEY (sID))");
            stmt.executeUpdate("CREATE TABLE transaction (tID INT NOT NULL, pID INT NOT NULL, sID INT NOT NULL, tDate DATE NOT NULL, PRIMARY KEY (tID), FOREIGN KEY (sID) REFERENCES salesperson (sID), FOREIGN KEY (pID) REFERENCES part (pID))");
            stmt.close();
        } catch(Exception e){
            System.out.println(e);
        }
        System.out.println("Done! Database is initialized!");
    }
    public static void delete_table(){
        /*
        This function deletes all existing tables
        of the sales system from MySQL DBMS.
        (note: delete order is important - foreign key constraint)
        */
        //TODO: implement delete all tables
    }
    public static void load_data(){
        /*
        This function reads all data files from a
        user-specified folder and inserts the records
        into the appropriate table in the database.
        */
        //TODO: implement load from datafile
    }
    public static void show_content(){
        /*
        This function shows the content of a
        user-specified table.
        */
        //TODO: implement show content of a table
    }

    /* Salesperson */
    public static void search_parts(){
        /*
        The system has to provide an interface to
        allow a salesperson to search for computer
        parts available in the store based on any one
        of the two different search criteria below.
        - By Part Name (partial matching) 
        - By Manufacturer Name (partial matching) 
        You can assume that only one search criterion
        can be selected by the salesperson for each
        query. 
        After he/she enters the search keyword, the
        program should perform the query and return
        all matching parts in terms of their Part ID,
        Part Name, Manufacturer Name, Category Name,
        Available Quantity, Warranty Period and Part
        Price. The salesperson can then choose any one
        of two different ways to sort the parts:
        - By price, ascending order
        - By price, descending order
        */
        //TODO: implement search for parts
    }
    public static void sell_part(){
        /*
        After a salesperson helps a customer finding
        a part, he/she can then sell the part (i.e.
        perform a transaction) through the sales
        system. First, he/she needs to input part ID
        of the part being sold and his/her salesperson
        ID. Then the system should check whether that
        part is available (Part Available Quantity > 0).
        If the part is available, it is then sold and
        the database is updated accordingly. Finally
        there should be an informative message on
        remaining available quantity of the part sold.
        If the part cannot be sold, an error message
        should also be shown.
        */
        //TODO: implement sell a part
    }

    /* Manager */
    public static void list_salespersons(){
        /*
        The system needs to provide a method for the
        manager to list all salespersons in either
        ascending or descending order of their years
        of experiences. After he/she specifies the
        output order, the program will perform the 
        query and return the ID, name, phone number
        and years of experience of each salesperson.
        */
        //TODO: implement list all salespersons
    }
    public static void count_record(){
        /*
        The system has to provide an interface to
        allow a manager to count the number of
        transaction records of each salesperson
        within a given range on years of experience
        (e.g. from 1 year to 3 years) inclusively.
        After he/she enters a specific range on years
        of experience, the program will perform the
        query and return the ID, name, years of
        experience and number of transaction records
        of each salesperson within the range on years
        of experience specified by the user
        inclusively. These transaction records should
        be sorted in descending order of Salesperson
        ID and outputted as a table.
        */
        //TODO: implement count the no. of sales record of each salesperson under a specific range on years of experience
    }
    public static void show_total(){
        /*
        The system has to provide an interface to
        allow a manager to sort the manufacturers
        according to their total sale values. After 
        the program performs the query, it returns
        the results in terms of Manufacturer ID,
        Manufacturer Name and Total sales value in
        descending order of Total sales value as a
        table.
        */
        //TODO: show the total sales value of each manufacturer
    }
    public static void show_popular(){
        /*
        The system has to provide an interface to
        allow a manager to show the N parts that are
        most popular. After the manager enters the
        number of parts (N) that he/she wants to list,
        the program will perform the query and return
        the N parts that are most popular in terms of
        Part ID, Part Name and Total Number of
        Transaction in descending order of Total
        Number of Transaction as a table.
        */
        //TODO: show the N most popular part
    }

    public static void menu(int menu_type)
    {
        /*
        0 - top menu
        1 - administrator
        2 - salesperson
        3 - manager
        */
        if(menu_type == 0){
            System.out.println("\n-----Main menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Operations for administrator");
            System.out.println("2. Operations for salesperson");
            System.out.println("3. Operations for manager");
            System.out.println("4. Exit this program");
            System.out.print("Enter Your Choice: ");
        }
        else if(menu_type == 1){
            System.out.println("\n-----Operations for administrator menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Create all tables");
            System.out.println("2. Delete all tables");
            System.out.println("3. Load from datafile");
            System.out.println("4. Show content of a table");
            System.out.println("5. Return to the main menu");
            System.out.print("Enter Your Choice: ");
        }
        else if(menu_type == 2){
            System.out.println("\n-----Operations for salesperson menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Search for parts");
            System.out.println("2. Sell a part");
            System.out.println("3. Return to the main menu");
            System.out.print("Enter Your Choice: ");
        }
        else if(menu_type == 3){
            System.out.println("\n-----Operations for manager menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. List all salespersons");
            System.out.println("2. Count the no. of sales record of each salesperson under a specific range on years of experience");
            System.out.println("3. Show the total sales value of each manufacturer");
            System.out.println("4. Show the N most popular part");
            System.out.println("5. Return to the main menu");
            System.out.print("Enter Your Choice: ");
        }
        else{
            System.out.println("Error: menu_type must be between 0 to 3");
        }
    }
    public static void process_choice(int menu_type) throws IOException{
        if(menu_type < 0 || menu_type > 3){
            System.out.println("Error: menu_type must be between 0 to 3");
            return;
        }
        menu(menu_type);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        char choice = (char)in.read();
        if(menu_type == 0){ //main menu
            if(choice == '1'){
                next_menu = 1;
            }
            else if(choice == '2'){
                next_menu = 2;
            }
            else if(choice == '3'){
                next_menu = 3;
            }
            else if(choice == '4'){
                try{
                    conn.close();
                } catch(Exception x){
                    System.err.println("Unable to close the connection!");
                }
                System.out.println("Bye!");
                System.exit(0);
            }
            else{
                System.out.println("Invalid choice!");
            }
        }
        else if(menu_type == 1){    //admin
            if(choice == '1'){
                //create all tables
                create_table();
            }
            else if(choice == '2'){
                //delete all tables
                delete_table();
            }
            else if(choice == '3'){
                //load from datafile
                load_data();
            }
            else if(choice == '4'){
                //show content of a table
                show_content();
            }
            else if(choice == '5'){
                //return to the main menu; no need to execute anything here
            }
            else{
                System.out.println("Invalid choice!");
                return;
            }
            //anyway, return to main menu afterwards
            next_menu = 0;
        }
        else if(menu_type == 2){    //salesperson
            if(choice == '1'){
                //search for parts
                search_parts();
            }
            else if(choice == '2'){
                //sell a part
                sell_part();
            }
            else if(choice == '3'){
                //return to the main menu; no need to execute anything here
            }
            else{
                System.out.println("Invalid choice!");
                return;
            }
            //anyway, return to main menu afterwards
            next_menu = 0;
        }
        else if(menu_type == 3){    //manager
            if(choice == '1'){
                //list all salespersons
                list_salespersons();
            }
            else if(choice == '2'){
                //that very long operation
                count_record();
            }
            else if(choice == '3'){
                //show the total sales value of each manufacturer
                show_total();
            }
            else if(choice == '4'){
                //show the N most popular part
                show_popular();
            }
            else if(choice == '5'){
                //return to the main menu; no need to execute anything here
            }
            else{
                System.out.println("Invalid choice!");
                return;
            }
            //anyway, return to main menu afterwards
            next_menu = 0;
        }
    }
    public static void main(String[] args) throws IOException
    {
        System.out.println("Welcome to sales system!");
        //menu(0);
        //process_choice(0);
        conn_to_db();
        next_menu = 0;
        while(true){
            process_choice(next_menu);
        }
    }
}