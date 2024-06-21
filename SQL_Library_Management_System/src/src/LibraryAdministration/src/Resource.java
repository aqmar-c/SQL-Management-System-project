package LibraryAdministration.src;

import java.util.ArrayList;

public interface Resource {
    public void setProductID();
    public void setProductName();
    public void setAvailability();
    public void setBorrowability();

    ArrayList<String> getProductID();
    ArrayList<String> getProductName();
    ArrayList<String> getAvailability();

}
