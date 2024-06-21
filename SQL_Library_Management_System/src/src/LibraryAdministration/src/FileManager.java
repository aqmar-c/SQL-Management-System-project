package LibraryAdministration.src;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {
    private boolean borrowFromWaiting=false;
    public String[] checkCredentials(String drivingLisenceNumber){
        // Read the content from file
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("users.txt"))) {
            String line = bufferedReader.readLine();
            while(line != null) {
                String[] splitted = line.split(",");
                if(splitted[1].trim().equalsIgnoreCase(drivingLisenceNumber)) return splitted;
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            // Exception handling
        } catch (IOException e) {
            // Exception handling
        }
        String line = "-1, -1, -1, -1";
        String[] splitted = line.split(",");
        return splitted;
    }


    public void waitingRegistrations(String drivingLisenceNumber, String name, String subscriptionType){
        int counter=0;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("users.txt"))) {
            String line = bufferedReader.readLine();
            while(line != null) {
                String[] splitted = line.split(",");
                if(splitted[1].trim().equalsIgnoreCase(drivingLisenceNumber)){
                    System.out.println("You are already registered. Please Log in.");
                    return;
                }
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            // Exception handling
        } catch (IOException e) {
            // Exception handling
        }

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("waitingUsers.txt"))) {
            String line = bufferedReader.readLine();
            while(line != null) {
                String[] splitted = line.split(",");
                if(splitted.length<=0) {
                    continue;
                }
                if(splitted[1].trim().equalsIgnoreCase(drivingLisenceNumber)){
                    System.out.println("You are already in approval waiting.");
                    return;
                }
                counter+=1;
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            // Exception handling
        } catch (IOException e) {
            // Exception handling
        }

        // Write the content in file
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("waitingUsers.txt", true))) {
            counter+=1;
            String fileContent = Integer.toString(counter)+", "+drivingLisenceNumber+", "+name+", "+subscriptionType+"\n";
            bufferedWriter.append(fileContent);
        } catch (IOException e) {
            // Exception handling
        }
    }


    public void increaseCounter(int days){
        File f = new File("counter.txt");
        if(!f.exists()) {
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("counter.txt"))) {
                bufferedWriter.write("0");
            } catch (IOException e) {
                // Exception handling
            }
        }

        int counter=0;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("counter.txt"))) {
            String line = bufferedReader.readLine();
            counter = Integer.valueOf(line) + days;
        } catch (FileNotFoundException e) {
            // Exception handling
        } catch (IOException e) {
            // Exception handling
        }

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("counter.txt"))) {
            bufferedWriter.write(Integer.toString(counter));
        } catch (IOException e) {
            // Exception handling
        }
    }

    public int getCounter(){
        File f = new File("counter.txt");
        if(!f.exists()) {
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("counter.txt"))) {
                bufferedWriter.write("0");
            } catch (IOException e) {
                // Exception handling
            }
        }

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("counter.txt"))) {
            String line = bufferedReader.readLine();
            return Integer.valueOf(line);
        } catch (FileNotFoundException e) {
            // Exception handling
        } catch (IOException e) {
            // Exception handling
        }
        return -1;
    }

    public ArrayList<String> getField(String filename, int fieldIndex){
        ArrayList<String> s = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line = bufferedReader.readLine();
            while(line != null) {
                String[] splitted = line.split(",");
                s.add(splitted[fieldIndex].trim());
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            // Exception handling
        } catch (IOException e) {
            // Exception handling
        }

        return s;
    }

    public void borrowResource(String type, String drivingLicence, String rescID) throws IOException {
        Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/userfiles/"));
        String filepath = System.getProperty("user.dir") + "/userfiles/"+drivingLicence+".txt";

        int cnt = getCounter();
        File f = new File(filepath);
        if(!f.exists()) {

            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
                bufferedWriter.write(Integer.toString(cnt)+"\n");
            } catch (IOException e) {
                // Exception handling
            }
        }

        // Write the content in file
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath, true))) {
            String fileContent = type+", " + rescID+", " + Integer.toString(cnt)+", "+"-1\n";
            bufferedWriter.append(fileContent);
        } catch (IOException e) {
            // Exception handling
        }

        if(borrowFromWaiting==false){
            String filename = type.toLowerCase()+".txt";

            List<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8));

            for (int i = 0; i < fileContent.size(); i++) {
                String[] splitted = fileContent.get(i).split(",");
                if (splitted[0].trim().equalsIgnoreCase(rescID)) {
                    int avl = Integer.valueOf(splitted[2].trim());
                    String avl_update = Integer.toString(avl-1);
                    String line_update = rescID+", "+splitted[1].trim()+", "+avl_update;
                    fileContent.set(i, line_update);
                    break;
                }
            }

            Files.write(Path.of(filename), fileContent, StandardCharsets.UTF_8);
        }
        borrowFromWaiting=false;
    }

    public ArrayList<String> getUserRecords(String drivingLicence) throws IOException {

        String filename = System.getProperty("user.dir") + "/userfiles/"+drivingLicence+".txt";

        ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8));

        return fileContent;
    }

    public void returnResource(String type, String rescID, String drivingLicence) throws IOException {
        String filename = type.toLowerCase()+".txt";

        List<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8));

        int waitingFlag = 0;
        for (int i = 0; i < fileContent.size(); i++) {
            String[] splitted = fileContent.get(i).split(",");
            if (splitted[0].trim().equalsIgnoreCase(rescID)) {
                int avl = Integer.valueOf(splitted[2].trim());
                if(avl<0) waitingFlag=1;
                String avl_update = Integer.toString(avl+1);
                String line_update = rescID+", "+splitted[1].trim()+", "+avl_update;
                fileContent.set(i, line_update);
                break;
            }
        }

        Files.write(Path.of(filename), fileContent, StandardCharsets.UTF_8);

        int counter = getCounter();

        filename = System.getProperty("user.dir") + "/userfiles/"+drivingLicence+".txt";

        fileContent = new ArrayList<>(Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8));

        for (int i = 1; i < fileContent.size(); i++) {
            String[] splitted = fileContent.get(i).split(",");
            if(splitted.length>1){
                if (splitted[1].trim().equalsIgnoreCase(rescID) && splitted[0].trim().equalsIgnoreCase(type)) {
                    String return_update = Integer.toString(counter);
                    String line_update = splitted[0].trim()+", "+splitted[1].trim()+", "+splitted[2].trim()+", "+return_update;
                    fileContent.set(i, line_update);
                    break;
                }
            }
        }

        Files.write(Path.of(filename), fileContent, StandardCharsets.UTF_8);

        if(waitingFlag==1) getResourceFromWaiting(type, drivingLicence, rescID);
    }

    public void payDues(int amount, String drivingLicence) throws IOException {

        // Write the content in file
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("unapproved_payment.txt", true))) {
            String fileContent = drivingLicence +", "+ Integer.toString(amount)+"\n";
            bufferedWriter.append(fileContent);
        } catch (IOException e) {
            // Exception handling
        }

        String filename = System.getProperty("user.dir") + "/userfiles/"+drivingLicence+".txt";

        ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8));
        String line_update = Integer.toString(getCounter());
        fileContent.set(0, line_update);

        Files.write(Path.of(filename), fileContent, StandardCharsets.UTF_8);
    }

    public void addToWaitingLine(String type, String drivingLicence, String rescID) throws IOException {
        Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/resourcefiles/"+type.toLowerCase()+"_pending/"));
        String filepath = System.getProperty("user.dir") + "/resourcefiles/"+type.toLowerCase()+"_pending/"+rescID+".txt";



        // Write the content in file
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath, true))) {
            bufferedWriter.append(drivingLicence+"\n");
        } catch (IOException e) {
            // Exception handling
        }

        String filename = type.toLowerCase()+".txt";

        List<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            String[] splitted = fileContent.get(i).split(",");
            if (splitted[0].trim().equalsIgnoreCase(rescID)) {
                int avl = Integer.valueOf(splitted[2].trim());
                String avl_update = Integer.toString(avl-1);
                String line_update = rescID+", "+splitted[1].trim()+", "+avl_update;
                fileContent.set(i, line_update);
                break;
            }
        }

        Files.write(Path.of(filename), fileContent, StandardCharsets.UTF_8);
    }

    public void getResourceFromWaiting(String type, String drivingLicence, String rescID) throws IOException {
        String filename = System.getProperty("user.dir") + "/resourcefiles/" + type.toLowerCase() + "_approved/"+rescID+".txt";
        File f = new File(filename);
        if(!f.exists()) {
            System.out.println("A Resource is freed. But the waiting list request is not updated yet.");
            return;
        }



        ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8));
        drivingLicence = fileContent.get(0).trim();
        System.out.println(drivingLicence);
        ArrayList<String> updatedFile = new ArrayList<>();
        for (int i = 1; i < fileContent.size(); i++) {
            updatedFile.add(fileContent.get(i));
        }
        if(updatedFile.size()==0) updatedFile.add("");
        Files.write(Path.of(filename), updatedFile, StandardCharsets.UTF_8);

        borrowFromWaiting=true;
        borrowResource(type, drivingLicence, rescID);
    }
}
