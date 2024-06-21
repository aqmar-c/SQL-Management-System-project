package LibraryAdministration.src;

import com.mysql.cj.protocol.Resultset;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

public class txttomysql {
    public static void main(String[] args) throws SQLException, IOException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library", "root", "+Notaq2001");

        Statement statement = connection.createStatement();

        statement.execute("DELETE FROM `Library`.`WaitingList`");
        statement.execute("DELETE FROM `Library`.`BorrowHistory`");
        statement.execute("DELETE FROM `Library`.`Subscriber`");
        statement.execute("DELETE FROM `Library`.`Resource`");

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("users.txt"))) {
            String line = bufferedReader.readLine();
            while(line != null) {
                String[] splitted = line.split(",");
                String values = splitted[0].trim() + ", '" + splitted[1].trim() + "', '" + splitted[2].trim() + "', '" + splitted[3].trim()+"', ";

                String lastPaid = "";
                String pathname = System.getProperty("user.dir") + "/userfiles/";
                File dir = new File(pathname);
                File[] directoryListing = dir.listFiles();
                if (directoryListing != null) {
                    for (File child : directoryListing) {
                        String filename = child.getName();
                        if(filename.equalsIgnoreCase(splitted[1].trim()+".txt")){
                            filename = child.getAbsolutePath();
                            ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8));
                            lastPaid = fileContent.get(0).trim();
                        }

                    }
                }
                if(lastPaid.equalsIgnoreCase("")) lastPaid="0";

                values += lastPaid;
                statement.execute("INSERT INTO `Library`.`Subscriber` (`SubscriberID`, `DrivingLicence`, `Name`, `SubscriptionType`, `DuesPaidTill`) VALUES ("+ values +");");
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            // Exception handling
        } catch (IOException e) {
            // Exception handling
        }


        ArrayList<String> resourceFiles = new ArrayList<>();
        resourceFiles.add("book");
        resourceFiles.add("cd");
        resourceFiles.add("dvds");
        resourceFiles.add("journal");
        resourceFiles.add("toy");

        for (String type: resourceFiles){
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(type+"s.txt"))) {
                String line = bufferedReader.readLine();
                while(line != null) {
                    String[] splitted = line.split(",");
                    String values = splitted[0].trim() + ", '" + type + "', '" + splitted[1].trim() + "', " + splitted[2].trim();
                    statement.execute("INSERT INTO `Library`.`Resource` (`ResourceID`, `Type`, `Name`, `Availability`) VALUES ("+ values +");");
                    line = bufferedReader.readLine();
                }
            } catch (FileNotFoundException e) {
                // Exception handling
            } catch (IOException e) {
                // Exception handling
            }
        }



        int counter=1;
        String pathname = System.getProperty("user.dir") + "/userfiles/";
        File dir = new File(pathname);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String filename = child.getAbsolutePath();
                ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8));
                filename = child.getName();
                System.out.println(filename.substring(0, filename.length() - 4));
                for (int i = 1; i < fileContent.size(); i++) {
                    String[] splitted = fileContent.get(i).split(",");
                    String values = Integer.toString(counter)+ ", " + splitted[2].trim() + ", " + splitted[3].trim() + ", '" + filename.substring(0, filename.length() - 4) + "', '" + splitted[1].trim() + "'";
                    statement.execute("INSERT INTO `Library`.`BorrowHistory` (`BorrowedID`, `BorrowDate`, `ReturnDate`, `Borrower`, `Resource`) VALUES ("+ values +");");
                    counter+=1;
                }
            }
        }


        counter=1;

        for (String type: resourceFiles){
            pathname = System.getProperty("user.dir") + "/resourcefiles/"+type+"s_approved/";
            Files.createDirectories(Paths.get(pathname));
            dir = new File(pathname);
            directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    String filename = child.getAbsolutePath();
                    ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8));
                    filename = child.getName();
                    System.out.println(filename);
                    String rescid = filename.substring(0, filename.length() - 4);
                    int serial = 1;
                    for (int i = 0; i < fileContent.size(); i++) {
                        if(fileContent.get(i).length()<=0) continue;
                        String values = Integer.toString(counter)+ ", '" + fileContent.get(i).trim() + "', '" + rescid + "', " + Integer.toString(serial);
                        System.out.println(values);
                        statement.execute("INSERT INTO `Library`.`WaitingList` (`WaitingListID`, `Borrower`, `Resource`, `Serial`) VALUES ("+ values +");");
                        counter+=1;
                        serial+=1;
                    }
                }
            }
        }





        ResultSet resultSet = statement.executeQuery("select * from WaitingList");

        while (resultSet.next()){
            System.out.println(resultSet.getString("Borrower"));
        }
    }
}