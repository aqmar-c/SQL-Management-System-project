package LibraryAdministration.src;

import java.util.ArrayList;

public class Books implements Resource{
    ArrayList<String> productID = new ArrayList<>();
    ArrayList<String> productName = new ArrayList<>();
    ArrayList<String> Availability = new ArrayList<>();
    boolean isBorrowable;
    Books(){
        setProductID();
        setProductName();
        setAvailability();
        setBorrowability();
    }
    @Override
    public void setProductID() {
        FileManager fileManager = new FileManager();
        productID = fileManager.getField("books.txt", 0);
    }

    @Override
    public void setProductName() {
        FileManager fileManager = new FileManager();
        productName= fileManager.getField("books.txt", 1);
    }

    @Override
    public void setAvailability() {
        FileManager fileManager = new FileManager();
        Availability = fileManager.getField("books.txt", 2);
    }

    @Override
    public void setBorrowability() {
        isBorrowable = true;
    }

    @Override
    public ArrayList<String> getProductID() {
        return productID;
    }

    @Override
    public ArrayList<String> getProductName() {
        return productName;
    }

    @Override
    public ArrayList<String> getAvailability() {
        return Availability;
    }
}
