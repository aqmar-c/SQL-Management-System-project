package LibraryAdministration.src;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Library {
    public static void main(String[] args) throws IOException {
        run_menu();
    }

    public static void run_menu() throws IOException {
        System.out.println("WELCOME TO THE Library System.\n");
        String repeat = "1. User Login\n2. Admin Login\n3. View counter\n4. Forward a week\n5. Forward a month\n6. Exit\n";
        Scanner scanner = new Scanner(System.in);
        int n;
        boolean done = false;
        while(true){
            if(done) break;
            System.out.println(repeat);
            n = scanner.nextInt();
            switch(n){
                case 1:
                    User user = new User();
                    user.registration_menu();
                    break;

                case 2:
                    Admin admin = new Admin();
                    admin.run_menu();
                    break;

                case 3:
                    FileManager fileManager = new FileManager();
                    System.out.println("Current day counter: "+fileManager.getCounter());
                    break;
                case 4:
                    fileManager = new FileManager();
                    fileManager.increaseCounter(7);
                    break;
                case 5:
                    fileManager = new FileManager();
                    fileManager.increaseCounter(30);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    done = true;
                    break;
                default:
                    System.out.println("Enter Valid Input");
            }

        }

    }
}
