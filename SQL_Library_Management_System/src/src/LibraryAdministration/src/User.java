package LibraryAdministration.src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class User {
    String libraryID;
    String drivingLicence;
    String name;
    String subscriptionType;

    public void registration_menu() throws IOException {
        System.out.println("WELCOME TO THE  LOGIN/REGISTRATION MENU\n");
        String repeat = "1. LOGIN\n2. REGISTRATION\n3. RETURN\n";
        Scanner scanner = new Scanner(System.in);
        int n;
        boolean done = false;
        while(true){
            if(done) break;
            System.out.println(repeat);
            n = scanner.nextInt();
            switch(n){
                case 1:
                    System.out.println("Please provide your driving licence: ");
                    String dl = scanner.next();
                    FileManager fileManager = new FileManager();
                    String[] userInfo = fileManager.checkCredentials(dl);
                    if(userInfo[0].trim().equalsIgnoreCase("-1")) System.out.println("This id is not registered");
                    else{
                        libraryID = userInfo[0].trim();
                        drivingLicence = userInfo[1].trim();
                        name = userInfo[2].trim();
                        subscriptionType = userInfo[3].trim();
                        main_menu();
                    }
                    break;
                case 2:
                    System.out.println("Please provide your driving licence: ");
                    drivingLicence = scanner.next();
                    System.out.println("Please provide your name: ");
                    name = scanner.next();
                    boolean done_in = false;
                    while (true) {
                        if (done_in) break;
                        System.out.println("Please chose your preferred subscription plan: ");
                        String plans = "1. Regular\n2. Senior citizens\n3. Educators\n4. Students\n5. Premium subscribers(Need to pay an annual fee of $99)";
                        System.out.println(plans);
                        int plan = scanner.nextInt();
                        switch (plan) {
                            case 1:
                                subscriptionType = "REGULAR";
                                done_in = true;
                                break;
                            case 2:
                                subscriptionType = "SENIOR_CITIZENS";
                                done_in = true;
                                break;
                            case 3:
                                subscriptionType = "EDUCATORS";
                                done_in = true;
                                break;
                            case 4:
                                subscriptionType = "STUDENTS";
                                done_in = true;
                                break;
                            case 5:
                                subscriptionType = "PREMIUM_SUBSCRIBERS";
                                done_in = true;
                                break;
                            default:
                                System.out.println("Invalid Input. Try Again.");
                        }
                    }
                    fileManager = new FileManager();
                    fileManager.waitingRegistrations(drivingLicence, name, subscriptionType);
                    System.out.println("An admin will approve your request. Thank you. Exiting...");
                    done=true;
                    break;
                case 3:
                    System.out.println("Exiting...");
                    done = true;
                    break;
                default:
                    System.out.println("Enter Valid Input");
            }

        }
    }

    public void main_menu() throws IOException {
        System.out.println("WELCOME "+name);
        String repeat = "1. Borrow\n2. Get Dues Information\n3. Pay Dues\n4. Request to be added to waitlist\n5. Return\n6. Exit\n";
        Scanner scanner = new Scanner(System.in);
        int n;
        boolean done = false;
        while(true){
            if(done) break;
            System.out.println(repeat);
            n = scanner.nextInt();
            switch(n){
                case 1:
                    borrow_menu();
                    break;

                case 2:
                    int due = calculatePenalty();
                    System.out.println("Your penalty is: "+Integer.toString(due));
                    break;

                case 3:
                    due = calculatePenalty();
                    FileManager fileManager = new FileManager();
                    fileManager.payDues(due, drivingLicence);
                    System.out.println("Your dues are paid.");
                    break;
                case 4:

                    addToWaitlist();
                    break;
                case 5:
                    return_a_resource();
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

    public void borrow_menu() throws IOException {
        System.out.println("WELCOME TO THE BORROWING SUSTEM"+name);
        String repeat = "1. Books\n2. CDs\n3. DVDs\n4. Return\n";
        Scanner scanner = new Scanner(System.in);
        int n;
        boolean done = false;
        while(true){
            if(done) break;
            System.out.println(repeat);
            n = scanner.nextInt();
            switch(n){
                case 1:
                    borrow_a_resource("BOOKS");
                    break;

                case 2:
                    borrow_a_resource("CDs");
                    break;

                case 3:
                    borrow_a_resource("DVDs");
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

    public void borrow_a_resource(String type) throws IOException {
        Resource resource=null;
        if(type.equalsIgnoreCase("BOOKS")) resource = new Books();
        else if (type.equalsIgnoreCase("DVDS")) resource = new DVDs();
        else if (type.equalsIgnoreCase("CDS")) resource = new CDs();

        SubscriptionPlan subscriptionPlan=null;

        if(subscriptionType.equalsIgnoreCase("Students")) subscriptionPlan = new Students();
        else if(subscriptionType.equalsIgnoreCase("Educators")) subscriptionPlan = new Educators();
        else if(subscriptionType.equalsIgnoreCase("Senior_Citizens")) subscriptionPlan = new SeniorCitizens();
        else if(subscriptionType.equalsIgnoreCase("Regular")) subscriptionPlan = new RegularSubscribers();
        else if(subscriptionType.equalsIgnoreCase("PREMIUM_SUBSCRIBERS")) subscriptionPlan = new PremiumSubscribers();

        ArrayList<String> rescName = resource.getProductName();
        ArrayList<String> rescID = resource.getProductID();
        ArrayList<String> rescAvailability = resource.getAvailability();

        System.out.println("Resource Name\t\tResource Availability");

        for (int i = 0; i < rescName.size(); i++) {
            if (Integer.valueOf(rescAvailability.get(i))>0) System.out.println(Integer.toString(i+1)+". "+rescName.get(i)+"\t\t"+rescAvailability.get(i));
        }
        System.out.println("Choose one or press 0 to exit");
        Scanner scanner = new Scanner(System.in);
        int in = scanner.nextInt();
        if(in==0) return;
        else if (rescAvailability.get(in-1).equalsIgnoreCase("0")) {
            System.out.println("Resource Not Available. You can try for waiting list.");
            return;
        }
        FileManager fileManager = new FileManager();
        fileManager.borrowResource(type.toUpperCase(), drivingLicence, rescID.get(in-1));
    }

    public void return_a_resource() throws IOException {
        FileManager fileManager = new FileManager();
        ArrayList<String> lines = fileManager.getUserRecords(drivingLicence);

        System.out.println("Resource Type\t\t\tResource ID\t\t\tBorrowed on");
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] splitted = line.split(",");
            if(Integer.valueOf(splitted[3].trim())==-1) System.out.println(Integer.toString(i)+". "+splitted[0]+"\t\t\t"+splitted[1]+"\t\t\t"+splitted[2]);
        }

        System.out.println("Choose one or press 0 to exit");
        Scanner scanner = new Scanner(System.in);
        int in = scanner.nextInt();

        String[] splitted = lines.get(in).split(",");
        if(in==0) return;
        else if (Integer.valueOf(splitted[3].trim())!=-1) {
            System.out.println("The item is already returned.");
            return;
        }

        fileManager.returnResource(splitted[0].trim(), splitted[1].trim(), drivingLicence);
    }

    public int calculatePenalty() throws IOException {
        FileManager fileManager = new FileManager();
        ArrayList<String> lines = fileManager.getUserRecords(drivingLicence);

        int total=0;

        SubscriptionPlan subscriptionPlan=null;



        if(subscriptionType.equalsIgnoreCase("Students")) subscriptionPlan = new Students();
        else if(subscriptionType.equalsIgnoreCase("Educators")) subscriptionPlan = new Educators();
        else if(subscriptionType.equalsIgnoreCase("Senior_Citizens")) subscriptionPlan = new SeniorCitizens();
        else if(subscriptionType.equalsIgnoreCase("Regular")) subscriptionPlan = new RegularSubscribers();
        else if(subscriptionType.equalsIgnoreCase("PREMIUM_SUBSCRIBERS")) subscriptionPlan = new PremiumSubscribers();

        int last_payment_date = Integer.valueOf(lines.get(0));
        for (int i = 1; i < lines.size(); i++) {

            String line = lines.get(i);
            System.out.println(line);
            String[] splitted = line.split(",");
            if(splitted.length<=0) continue;
            if(Integer.valueOf(splitted[3].trim())!=(-1) && Integer.valueOf(splitted[3].trim())<fileManager.getCounter()) continue;
            int limit = subscriptionPlan.getBorrowableLimit(splitted[0].trim());
            int penalty = subscriptionPlan.getPenaltyFine(splitted[0].trim());
            int counter = fileManager.getCounter();
            int borrow_time = Integer.valueOf(splitted[2].trim());


            if(borrow_time+limit<last_payment_date) total+=(counter-last_payment_date)*penalty;
            else total+=(counter-borrow_time-limit)*penalty;
        }

        return total;
    }

    public void addToWaitlist() throws IOException {
        String type=null;
        System.out.println("WELCOME TO THE Waiting List Registration"+name);
        String repeat = "1. Books\n2. CDs\n3. DVDs\n4. Return\n";
        Scanner scanner = new Scanner(System.in);
        int n;
        boolean done = false;
        while(true){
            if(done) break;
            System.out.println(repeat);
            n = scanner.nextInt();
            switch(n){
                case 1:
                    type = "BOOKS";
                    done = true;
                    break;

                case 2:
                    type = "CDs";
                    done = true;
                    break;

                case 3:
                    type = "DVDs";
                    done = true;
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Enter Valid Input");
            }

        }


        Resource resource=null;
        int waitingLimit=-1;
        if(type.equalsIgnoreCase("BOOKS")){
            resource = new Books();
            waitingLimit = 5;
        }
        else if (type.equalsIgnoreCase("DVDS")){
            resource = new DVDs();
            waitingLimit = 3;
        }
        else if (type.equalsIgnoreCase("CDS")){
            resource = new CDs();
            waitingLimit = 3;
        }



        ArrayList<String> rescName = resource.getProductName();
        ArrayList<String> rescID = resource.getProductID();
        ArrayList<String> rescAvailability = resource.getAvailability();

        System.out.println("Resource Name\t\tResource Availability");

        for (int i = 0; i < rescName.size(); i++) {
            int rem = Integer.valueOf(rescAvailability.get(i)) + waitingLimit;
            if (rem>0 && rem<=waitingLimit) System.out.println(Integer.toString(i+1)+". "+rescName.get(i)+"\t\t"+Integer.toString(rem));
        }
        System.out.println("Choose one or press 0 to exit");
        int in = scanner.nextInt();
        if(in==0) return;

        FileManager fileManager = new FileManager();
        fileManager.addToWaitingLine(type.toUpperCase(), drivingLicence, rescID.get(in-1));
    }

}
