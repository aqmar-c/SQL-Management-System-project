package LibraryAdministration.src;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public class FileManagerAdmin {
    public ArrayList<String> getwaitingRegistrations() {
        ArrayList<String> lines = new ArrayList<>();
        // Read the content from file
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("waitingUsers.txt"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            // Exception handling
        } catch (IOException e) {
            // Exception handling
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("waitingUsers.txt"))) {
            bufferedWriter.write("");
        } catch (IOException e) {
            // Exception handling
        }
        return lines;
    }

    public void register(String drivingLisenceNumber, String name, String subscriptionType) {
        int counter = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("users.txt"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] splitted = line.split(",");
                if (splitted[1].trim().equalsIgnoreCase(drivingLisenceNumber)) {
                    System.out.println("You are already registered");
                    return;
                }
                counter += 1;
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            // Exception handling
        } catch (IOException e) {
            // Exception handling
        }

        // Write the content in file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("users.txt", true))) {
            counter += 1;
            String fileContent = Integer.toString(counter) + ", " + drivingLisenceNumber + ", " + name + ", " + subscriptionType + "\n";
            bufferedWriter.append(fileContent);
        } catch (IOException e) {
            // Exception handling
        }
    }

    public void approvePayment() throws IOException {
        String filename = "unapproved_payment.txt";

        ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8));

        // Write the content in file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("approved_payment.txt", true))) {
            for (String s : fileContent) {
                bufferedWriter.append(s + "\n");
            }
        } catch (IOException e) {
            // Exception handling
        }

        // Write the content in file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("unapproved_payment.txt"))) {
            bufferedWriter.append("");
        } catch (IOException e) {
            // Exception handling
        }
    }

    public void approveWaitinglist() throws IOException {
        ArrayList<String> types = new ArrayList<>();
        types.add("books");
        types.add("cds");
        types.add("dvds");

        for (String type : types) {
            Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/resourcefiles/" + type.toLowerCase() + "_pending/"));
            String pathname = System.getProperty("user.dir") + "/resourcefiles/" + type.toLowerCase() + "_pending/";

            File dir = new File(pathname);
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    String filename = child.getAbsolutePath();

                    ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8));

                    filename = child.getName();
                    Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/resourcefiles/" + type.toLowerCase() + "_approved/"));
                    filename = System.getProperty("user.dir") + "/resourcefiles/" + type.toLowerCase() + "_approved/"+filename;

                    // Write the content in file
                    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename, true))) {
                        for (String s : fileContent) {
                            bufferedWriter.append(s + "\n");
                        }
                    } catch (IOException e) {
                        // Exception handling
                    }

                    // Write the content in file
                    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(child.getAbsolutePath()))) {
                        bufferedWriter.append("");
                    } catch (IOException e) {
                        // Exception handling
                    }
                }


            }

        }
    }
}
