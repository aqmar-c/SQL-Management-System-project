import java.sql.*;

public class SQLtest {

    public static void main(String[] args) {

        String connectionString = "jdbc:mysql://localhost:3306/";

//Change the values to your database schema Name.
        String schemaName = "library_schema";
        String userID = "root";
        String password= "+Notaq2001";


        try {
            Connection connection = DriverManager.getConnection(connectionString+schemaName, userID, password);//Establishing connection
            System.out.println("Connected With the database successfully");




        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while connecting to the database");
        }
    }

}
