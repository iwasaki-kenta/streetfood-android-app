package hkust.com.bitwise.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 2/21/2016.
 */
public class FoodOrder {
    private List<FoodMenuItem> orderedItems = new ArrayList<FoodMenuItem>();
    private FoodVendor vendor;
    private Date orderDate;

    public FoodOrder() {

    }

    public FoodVendor getVendor() {
        return vendor;
    }

    public void setVendor(FoodVendor vendor) {
        this.vendor = vendor;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<FoodMenuItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<FoodMenuItem> orderedItems) {
        this.orderedItems = orderedItems;
    }
}
