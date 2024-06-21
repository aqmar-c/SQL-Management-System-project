package LibraryAdministration.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
    public void run_menu() throws IOException {
        System.out.println("WELCOME TO ADMIN MENU.\n");
        String repeat = "1. APPROVE CHECK-IN \n2. COLLECT DUES\n3. ADD SUBSCRIBERS TO WAITLIST\n4. CHECK-OUT\n";
        Scanner scanner = new Scanner(System.in);
        int n;
        boolean done = false;
        while (true) {
            if (done) break;
            System.out.println(repeat);
            n = scanner.nextInt();
            switch (n) {
                case 1:
                    approve_menu();
                    break;

                case 2:
                    FileManagerAdmin fma = new FileManagerAdmin();
                    fma.approvePayment();
                    System.out.println("All the dues are collected.");
                    break;
                case 3:
                    fma = new FileManagerAdmin();
                    fma.approveWaitinglist();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    done = true;
                    break;
                default:
                    System.out.println("Enter Valid Input");
            }
        }
    }

    public void approve_menu() {
        System.out.println("CHECK_IN APPROVAL MENU.\n");

        FileManagerAdmin fileManager = new FileManagerAdmin();
        ArrayList<String> waitingReg = fileManager.getwaitingRegistrations();
        if(waitingReg.size()==0){
            System.out.println("NO APPROVAL PENDING");
            return;
        }
        System.out.println("Approved Requests:\nName\tDriving Licence Number\tSubscription Type");
        for(String s: waitingReg){
            String[] splitted = s.split(",");
            String drivingLicence = splitted[1].trim();
            String name  = splitted[2].trim();
            String subscritionType = splitted[3].trim();
            fileManager.register(drivingLicence, name, subscritionType);
            System.out.println(name+"\t"+drivingLicence+"\t"+subscritionType);
        }
    }
}
