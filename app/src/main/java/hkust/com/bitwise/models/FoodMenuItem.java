package hkust.com.bitwise.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodMenuItem implements Parcelable {
    private String id;
    private String name;
    private String type;
    private String image;

    private int quantity;

    private int price;

    public void increaseQuantity() {
        quantity++;
    }

    public void minusQuantity() {
        if (quantity >= 1) quantity--;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodMenuItem() {
    }

    protected FoodMenuItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        type = in.readString();
        image = in.readString();
        price = in.readInt();
        quantity = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(image);
        dest.writeInt(price);
        dest.writeInt(quantity);
    }

    @SuppressWarnings("unused")
    public static final Creator<FoodMenuItem> CREATOR = new Creator<FoodMenuItem>() {
        @Override
        public FoodMenuItem createFromParcel(Parcel in) {
            return new FoodMenuItem(in);
        }

        @Override
        public FoodMenuItem[] newArray(int size) {
            return new FoodMenuItem[size];
        }
    };
}