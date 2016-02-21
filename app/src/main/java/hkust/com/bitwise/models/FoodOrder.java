package hkust.com.bitwise.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "orders")
public class FoodOrder extends Model implements Parcelable {
    @Column(name = "orderedItems")
    private String orderedItems;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "orderDate")
    private String orderDate;

    // 0 for in progress, 1 for finished.
    @Column(name = "state")
    private int currentState = 0;

    public FoodOrder() {

    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public FoodVendor getVendor() {
        return new Gson().fromJson(vendor, FoodVendor.class);
    }

    public void setVendor(FoodVendor vendor) {
        this.vendor = new Gson().toJson(vendor);
    }

    public String getRawVendor() {
        return vendor;
    }

    public Date getOrderDate() {
        return new Gson().fromJson(orderDate, Date.class);
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = new Gson().toJson(orderDate);
    }

    public List<FoodMenuItem> getOrderedItems() {
        return new Gson().fromJson(orderedItems, new TypeToken<List<FoodMenuItem>>() {
        }.getType());
    }

    public String getRawOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<FoodMenuItem> orderedItems) {
        this.orderedItems = new Gson().toJson(orderedItems);
    }

    protected FoodOrder(Parcel in) {
        orderedItems = in.readString();
        vendor = in.readString();
        orderDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderedItems);
        dest.writeString(vendor);
        dest.writeString(orderDate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FoodOrder> CREATOR = new Parcelable.Creator<FoodOrder>() {
        @Override
        public FoodOrder createFromParcel(Parcel in) {
            return new FoodOrder(in);
        }

        @Override
        public FoodOrder[] newArray(int size) {
            return new FoodOrder[size];
        }
    };
}
