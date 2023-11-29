/* 
 * CSCI3170 Project
 * usage: java -classpath ./mysql-jdbc.jar:./ entry.java
 * our group's MySQL account: Group10 (password: CSCI3170)
*/
import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;

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
            stmt.executeUpdate("CREATE TABLE transaction (tID INT NOT NULL, pID INT NOT NULL, sID INT NOT NULL, tDate VARCHAR(10) NOT NULL, PRIMARY KEY (tID), FOREIGN KEY (sID) REFERENCES salesperson (sID), FOREIGN KEY (pID) REFERENCES part (pID))");
            //"Transaction data" should be in DATE format. However, date format in mysql is different from required, and thus using string is more convenient.
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
        System.out.print("Processing...");
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE transaction");
            stmt.executeUpdate("DROP TABLE salesperson");
            stmt.executeUpdate("DROP TABLE part");
            stmt.executeUpdate("DROP TABLE manufacturer");
            stmt.executeUpdate("DROP TABLE category");
            stmt.close();
        } catch(Exception e){
            System.out.println(e);
        }
        System.out.println("Done! Database is removed!");
    }
    public static void load_data(){
        /*
        This function reads all data files from a
        user-specified folder and inserts the records
        into the appropriate table in the database.
        */
        //TODO: implement load from datafile
        System.out.print("\nType in the Source Data Folder Path: ");
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String folder_path = in.readLine();
            String this_line;
            System.out.print("Processing...");
            //category
            BufferedReader f_in = new BufferedReader(new FileReader(folder_path + "/category.txt"));
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO category VALUES (?,?)");
            while((this_line = f_in.readLine()) != null){
                String[] data = this_line.split("\t");
                pstmt.setString(1,data[0]);
                pstmt.setString(2,data[1]);
                pstmt.executeUpdate();
            }
            //manufacturer
            f_in.close();
            f_in = new BufferedReader(new FileReader(folder_path + "/manufacturer.txt"));
            pstmt = conn.prepareStatement("INSERT INTO manufacturer VALUES (?,?,?,?)");
            while((this_line = f_in.readLine()) != null){
                String[] data = this_line.split("\t");
                pstmt.setString(1,data[0]);
                pstmt.setString(2,data[1]);
                pstmt.setString(3,data[2]);
                pstmt.setString(4,data[3]);
                pstmt.executeUpdate();
            }
            //part
            f_in.close();
            f_in = new BufferedReader(new FileReader(folder_path + "/part.txt"));
            pstmt = conn.prepareStatement("INSERT INTO part VALUES (?,?,?,?,?,?,?)");
            while((this_line = f_in.readLine()) != null){
                String[] data = this_line.split("\t");
                pstmt.setString(1,data[0]);
                pstmt.setString(2,data[1]);
                pstmt.setString(3,data[2]);
                pstmt.setString(4,data[3]);
                pstmt.setString(5,data[4]);
                pstmt.setString(6,data[5]);
                pstmt.setString(7,data[6]);
                pstmt.executeUpdate();
            }
            //salesperson
            f_in.close();
            f_in = new BufferedReader(new FileReader(folder_path + "/salesperson.txt"));
            pstmt = conn.prepareStatement("INSERT INTO salesperson VALUES (?,?,?,?,?)");
            while((this_line = f_in.readLine()) != null){
                String[] data = this_line.split("\t");
                pstmt.setString(1,data[0]);
                pstmt.setString(2,data[1]);
                pstmt.setString(3,data[2]);
                pstmt.setString(4,data[3]);
                pstmt.setString(5,data[4]);
                pstmt.executeUpdate();
            }
            //transaction
            f_in.close();
            f_in = new BufferedReader(new FileReader(folder_path + "/transaction.txt"));
            pstmt = conn.prepareStatement("INSERT INTO transaction VALUES (?,?,?,?)");
            while((this_line = f_in.readLine()) != null){
                String[] data = this_line.split("\t");
                pstmt.setString(1,data[0]);
                pstmt.setString(2,data[1]);
                pstmt.setString(3,data[2]);
                pstmt.setString(4,data[3]);
                pstmt.executeUpdate();
            }
            pstmt.close();
            f_in.close();
        } catch(Exception e){
            System.out.println(e);
        }
        System.out.println("Done! Data is inputted to the database!");
    }
    public static void show_content(){
        /*
        This function shows the content of a
        user-specified table.
        */
        //TODO: implement show content of a table
        System.out.print("Which table would you like to show: ");
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String req_table = in.readLine();
            Statement stmt = conn.createStatement();
            if(req_table.equals("category")){
                System.out.println("Content of table " + req_table + ":");
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + req_table);
                System.out.println("| cID | cName |");
                while(rs.next()){
                    System.out.print("|");
                    for(int i = 1;i <= 2;i++){
                        System.out.print(" ");
                        System.out.print(rs.getString(i));
                        System.out.print(" |");
                    }
                    System.out.println("");
                }
            }
            else if(req_table.equals("manufacturer")){
                System.out.println("Content of table " + req_table + ":");
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + req_table);
                System.out.println("| mID | mName | mAddress | mPhoneNumber |");
                while(rs.next()){
                    System.out.print("|");
                    for(int i = 1;i <= 4;i++){
                        System.out.print(" ");
                        System.out.print(rs.getString(i));
                        System.out.print(" |");
                    }
                    System.out.println("");
                }
            }
            else if(req_table.equals("part")){
                System.out.println("Content of table " + req_table + ":");
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + req_table);
                System.out.println("| pID | pName | pPrice | mID | cID | pWarrantyPeriod | pAvailableQuantity |");
                while(rs.next()){
                    System.out.print("|");
                    for(int i = 1;i <= 7;i++){
                        System.out.print(" ");
                        System.out.print(rs.getString(i));
                        System.out.print(" |");
                    }
                    System.out.println("");
                }
            }
            else if(req_table.equals("salesperson")){
                System.out.println("Content of table " + req_table + ":");
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + req_table);
                System.out.println("| sID | sName | sAddress | sPhoneNumber | sExperience |");
                while(rs.next()){
                    System.out.print("|");
                    for(int i = 1;i <= 5;i++){
                        System.out.print(" ");
                        System.out.print(rs.getString(i));
                        System.out.print(" |");
                    }
                    System.out.println("");
                }
            }
            else if(req_table.equals("transaction")){
                System.out.println("Content of table " + req_table + ":");
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + req_table);
                System.out.println("| tID | pID | sID | tDate |");
                while(rs.next()){
                    System.out.print("|");
                    for(int i = 1;i <= 4;i++){
                        System.out.print(" ");
                        System.out.print(rs.getString(i));
                        System.out.print(" |");
                    }
                    System.out.println("");
                }
            }
            else{
                System.out.println("Error: no table named \"" + req_table + "\".");
            }
            stmt.close();
        } catch(Exception e){
            System.out.println(e);
        }
    }

    /* Salesperson */
    public static void search_parts() throws IOException{
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
        System.out.println("Choose the Search criterion:");
        System.out.println("1. Part Name");
        System.out.println("2. Manufacturer Name");
        System.out.print("Choose the search criterion: ");
        //get the first select criterion: by partname/ by manufacturer name
        BufferedReader stepone = new BufferedReader(new InputStreamReader(System.in));
        char choiceone = (char)stepone.read();
        if(choiceone!='1' && choiceone!='2'){
            System.out.println("Invalid choice!");
            return;
        }
        //get the search keyword:
        System.out.print("Type in the Search Keyword: ");
        BufferedReader steptwo = new BufferedReader(new InputStreamReader(System.in));
        String keyword = steptwo.readLine();
        //get the second select criterion: asc/ desc
        System.out.println("Choose ordering: ");
        System.out.println("1. By price ,ascending order");
        System.out.println("2. By price, descending order");
        System.out.print("Choose the search criterion: ");
        BufferedReader stepthree = new BufferedReader(new InputStreamReader(System.in));
        char choicetwo = (char)stepthree.read();
        if(choicetwo!='1' && choicetwo!='2'){
            System.out.println("Invalid choice!");
            return;
        }
        
        //do selection
        if (choiceone=='1'){
            //by part
            String query="";
            if(choicetwo=='1'){
                //asc
                query = "SELECT p.pID,p.pName,m.mName,c.cName,p.pAvailableQuantity,p.pWarrantyPeriod,p.pPrice FROM part p, manufacturer m, category c WHERE p.cID=c.cID AND p.mID=m.mID AND p.pName LIKE '"+keyword+"%' ORDER BY p.pPrice ASC;";

            }else{
                //desc
                query = "SELECT p.pID,p.pName,m.mName,c.cName,p.pAvailableQuantity,p.pWarrantyPeriod,p.pPrice FROM part p, manufacturer m, category c WHERE p.cID=c.cID AND p.mID=m.mID AND p.pName LIKE '"+keyword+"%' ORDER BY p.pPrice DESC;";
            }
            try{
                Statement stmt = conn.createStatement();
            
                ResultSet rs = stmt.executeQuery(query);
                System.out.println("| ID | Name | Manufacture | Category | Quantity | Warranty | Price |");
                //print the result
                while (rs.next()){
                    int ID=rs.getInt("pID");
                    String Name=rs.getString("pName");
                    String Manufacturer = rs.getString("mName");
                    String Category = rs.getString("cName");
                    int Quantity = rs.getInt("pAvailableQuantity");
                    int Warranty = rs.getInt("pWarrantyPeriod");
                    int Price = rs.getInt("pPrice");
                    System.out.print("| "+ID+" | "+Name+" | "+Manufacturer+" | "+Category+" | "+Quantity+" | "+Warranty+" | "+Price+" |\n");
                }
                System.out.println("End of Query");
                stmt.close();
                return;
            } catch(Exception e){
                System.out.println("Not Found");
                return;
            }
        }else if (choiceone=='2'){
            //by manufacture
            String query="";
            if(choicetwo=='1'){
                //asc
                query = "SELECT p.pID,p.pName,m.mName,c.cName,p.pAvailableQuantity,p.pWarrantyPeriod,p.pPrice FROM part p, manufacturer m, category c WHERE p.cID=c.cID AND p.mID=m.mID AND m.mName LIKE '"+keyword+"%' ORDER BY p.pPrice ASC;";

            }else{
                //desc
                query = "SELECT p.pID,p.pName,m.mName,c.cName,p.pAvailableQuantity,p.pWarrantyPeriod,p.pPrice FROM part p, manufacturer m, category c WHERE p.cID=c.cID AND p.mID=m.mID AND m.mName LIKE'"+keyword+"%' ORDER BY p.pPrice DESC;";
            }
            try{
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                System.out.println("| ID | Name | Manufacture | Category | Quantity | Warranty | Price |");
                //print the result
                while (rs.next()){
                    int ID=rs.getInt("pID");
                    String Name=rs.getString("pName");
                    String Manufacturer = rs.getString("mName");
                    String Category = rs.getString("cName");
                    int Quantity = rs.getInt("pAvailableQuantity");
                    int Warranty = rs.getInt("pWarrantyPeriod");
                    int Price = rs.getInt("pPrice");
                    System.out.print("| "+ID+" | "+Name+" | "+Manufacturer+" | "+Category+" | "+Quantity+" | "+Warranty+" | "+Price+" |"+"\n");
                }
                System.out.print("End of Query"); 

                stmt.close();
                return;

            } catch(Exception e){
                System.out.println("Not Found");
                return;
            }

        }
    }
    public static void sell_part() throws IOException{
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
        System.out.print("Enter The Part ID: ");
        BufferedReader stepone = new BufferedReader(new InputStreamReader(System.in));
        String partid = stepone.readLine();
        int pid = Integer.parseInt(partid);
        //should I test the validity of input?
        System.out.print("Enter the salesperson ID: ");
        BufferedReader steptwo = new BufferedReader(new InputStreamReader(System.in));
        char sid = (char)steptwo.read();

        //should I test the validity of input?
        try{
            //preparation
            int Quantity=-100;
            String PartName="";
            int tid = -100;
            String query="SELECT * FROM part WHERE pID="+partid+";";
            String get_transactionid="SELECT MAX(tID) FROM transaction;";
            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSet last_tid = stmt2.executeQuery(get_transactionid);
            //get the quantity
            while(rs.next()){
                Quantity=rs.getInt("pAvailableQuantity");
                PartName = rs.getString("pName");
            }
            //get the last transaction id
            while(last_tid.next()){
                tid=last_tid.getInt("MAX(tID)");
            }
            int transaction_tid = tid+1;
            //do transaction
            stmt.close();
            stmt2.close();
            if(Quantity==0){
                //part is sold out
                System.out.println("Error: This part cannot be sold.");
                return;
            }else if(Quantity>0){
                try{
                   //part is available, sell one and update the part table
                    String update="UPDATE part SET pAvailableQuantity=? WHERE pID= ?";
                    PreparedStatement preparedStmt = conn.prepareStatement(update);
                    preparedStmt.setInt(1,Quantity-1);
                    preparedStmt.setInt(2,pid);
                    preparedStmt.executeUpdate(); 
                    preparedStmt.close();
                } catch(SQLException e){
                    System.out.println("Error in update");
                    return;
                } catch(Exception e){
                    e.printStackTrace();
                    return;
                }
                try{
                    //insert a record into transaction table
                    String insert="INSERT INTO transaction (tID, pID, sID, tDate) VALUES (?,?,?,?)";
                    //first, get date
                    java.util.Date udate = new java.util.Date();
                    SimpleDateFormat formatDateJava = new SimpleDateFormat("dd/MM/yyyy");
                    String sdate = formatDateJava.format(udate);
                    PreparedStatement preparedStmt2 = conn.prepareStatement(insert);
                    preparedStmt2.setInt(1,transaction_tid);
                    preparedStmt2.setInt(2,pid);
                    preparedStmt2.setInt(3,Character.getNumericValue(sid));
                    preparedStmt2.setString(4, sdate);
                    preparedStmt2.execute();
                    preparedStmt2.close();
                }catch(SQLException e){
                    System.out.println(e);
                    return;
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
                //output the result
                System.out.println("Product: "+PartName+"(id: "+partid+") Remaining Quantity: "+(Quantity-1));

            }else{
                System.out.println("error");
            }
        }catch(Exception x){
            System.err.println(x);
            return;
        }
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
         BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter sorting order (ASC/DESC): ");
            String order;
            try {
                order = reader.readLine().toUpperCase(); 
                String sortOrder = "";
        
                if (order.equals("ASC")) {
                    sortOrder = "ASC";
                } else if (order.equals("DESC")) {
                    sortOrder = "DESC";
                } else {
                    System.out.println("Invalid sorting order. Please enter ASC or DESC.");
                    return;
                }
        
                String query = "SELECT sID, sName, sPhoneNumber, sExperience FROM salesperson ORDER BY sExperience " + sortOrder;
        
                try (Connection connection = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
                     Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(query)) {
        
                    // Print the results
                    System.out.println("Salespersons:");
                    System.out.println("ID\tName\tPhone Number\tYears of Experience");
                    while (resultSet.next()) {
                        int id = resultSet.getInt("sID");
                        String name = resultSet.getString("sName");
                        String phoneNumber = resultSet.getString("sPhoneNumber");
                        int sExperience = resultSet.getInt("sExperience");
                        System.out.println(id + "\t" + name + "\t" + phoneNumber + "\t" + sExperience);
                    }            
                }
            }catch (Exception e) {
                e.printStackTrace();
            }     
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
        
       BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Please enter the lower limit of the experience range: ");
            int startExperience = Integer.parseInt(reader.readLine());

            System.out.print("Please enter the upper limit of the experience range: ");
            int endExperience = Integer.parseInt(reader.readLine());

            String query = "SELECT salesperson.sID, salesperson.sName, salesperson.sExperience, COUNT(transaction.tID) AS TransactionCount " +
                    "FROM salesperson " +
                    "LEFT JOIN transaction ON salesperson.sID = transaction.sID " +
                    "WHERE sExperience >= ? AND sExperience <= ? " +
                    "GROUP BY salesperson.sID, salesperson.sName, salesperson.sExperience " +
                    "ORDER BY salesperson.sID DESC";

            try (Connection connection = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
                PreparedStatement statement = connection.prepareStatement(query)) {

                // Set the parameters for the query
                statement.setInt(1, startExperience);
                statement.setInt(2, endExperience);

                try (ResultSet resultSet = statement.executeQuery()) {
                    // Print the results
                    System.out.println("Salespersons within the given range of years of experience:");
                    System.out.format("%-5s %-20s %-20s %-20s\n", "sID", "sName", "Years of Experience", "Transaction Count");
                    System.out.println("----------------------------------------------");
                    while (resultSet.next()) {
                        int id = resultSet.getInt("sID");
                        String name = resultSet.getString("sName");
                        int sExperience = resultSet.getInt("sExperience");
                        int transactionCount = resultSet.getInt("TransactionCount");
                        System.out.format("%-5d %-20s %-20d %-20d\n", id, name, sExperience, transactionCount);
                    }
                    System.out.println("----------------------------------------------");
                }   
            }    
        }catch (Exception e) {
            e.printStackTrace();
        }   
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
       String query = "SELECT manufacturer.mID, manufacturer.mName, SUM(part.pPrice) AS TotalSalesValue " +
                "FROM manufacturer " +
                "JOIN part ON manufacturer.mID = part.mID " +
                "GROUP BY manufacturer.mID, manufacturer.mName " +
                "ORDER BY TotalSalesValue DESC";

        try (Connection connection = DriverManager.getConnection(dbAddress,dbUsername,dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Print the results
            System.out.println("Manufacturers sorted by total sales value:");
            System.out.format("%-5s %-20s %-20s\n", "mID", "mName", "Total Sales Value");
            System.out.println("----------------------------------------------");
            while (resultSet.next()) {
                int id = resultSet.getInt("mID");
                String name = resultSet.getString("mName");
                double totalSalesValue = resultSet.getDouble("TotalSalesValue");
                System.out.format("%-5d %-20s %-20.2f\n", id, name, totalSalesValue);
            }
            System.out.println("----------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Please enter the number of parts that you want to list: ");
            int n = Integer.parseInt(reader.readLine());
    
            String query = "SELECT part.pID, part.pName, COUNT(transaction.pID) AS TransactionCount " +
                    "FROM part " +
                    "LEFT JOIN transaction ON part.pID = transaction.pID " +
                    "GROUP BY part.pID, part.pName " +
                    "ORDER BY TransactionCount DESC " +
                    "LIMIT ?";
    
            try (Connection connection = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
                 PreparedStatement statement = connection.prepareStatement(query)) {
    
                // Set the parameter for the query
                statement.setInt(1, n);
    
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Print the results
                    System.out.println(n + " most popular parts:");
                    System.out.format("%-5s %-20s %-20s\n", "ID", "Name", "Transaction Count");
                    System.out.println("----------------------------------------------");
                    while (resultSet.next()) {
                        int id = resultSet.getInt("pID");
                        String name = resultSet.getString("pName");
                        int transactionCount = resultSet.getInt("TransactionCount");
                        System.out.format("%-5d %-20s %-20d\n", id, name, transactionCount);
                    }
                    System.out.println("----------------------------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
