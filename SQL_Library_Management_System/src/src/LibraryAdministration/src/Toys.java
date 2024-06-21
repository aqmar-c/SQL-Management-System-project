package LibraryAdministration.src;

import java.util.ArrayList;

public class Toys implements Resource{
    ArrayList<String> productID = new ArrayList<>();
    ArrayList<String> productName = new ArrayList<>();
    ArrayList<String> Availability = new ArrayList<>();
    boolean isBorrowable;
    Toys(){
        setProductID();
        setProductName();
        setAvailability();
        setBorrowability();
    }
    @Override
    public void setProductID() {
        FileManager fileManager = new FileManager();
        productID = fileManager.getField("toys.txt", 0);
    }

    @Override
    public void setProductName() {
        FileManager fileManager = new FileManager();
        productName= fileManager.getField("toys.txt", 1);
        for (String s: productName) System.out.println(s);
    }

    @Override
    public void setAvailability() {
        FileManager fileManager = new FileManager();
        Availability = fileManager.getField("toys.txt", 2);
    }

    @Override
    public void setBorrowability() {
        isBorrowable = true;
    }

    @Override
    public ArrayList<String> getProductID() {
        return null;
    }

    @Override
    public ArrayList<String> getProductName() {
        return null;
    }

    @Override
    public ArrayList<String> getAvailability() {
        return null;
    }
}

