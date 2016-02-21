package hkust.com.bitwise.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class FoodVendor implements Parcelable {
    private String id;
    private String name;
    private String district;
    private String location;
    private String image;
    private List<String> menuIds = new ArrayList<String>();

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void addMenuItemId(String item) {
        menuIds.add(item);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodVendor() {
    }

    protected FoodVendor(Parcel in) {
        id = in.readString();
        name = in.readString();
        district = in.readString();
        menuIds = null;
        location = in.readString();
        image = in.readString();
        if (in.readByte() == 0x01) {
            menuIds = new ArrayList<String>();
            in.readList(menuIds, String.class.getClassLoader());
        } else {
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(district);
        dest.writeString(location);
        dest.writeString(image);
        if (menuIds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(menuIds);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FoodVendor> CREATOR = new Parcelable.Creator<FoodVendor>() {
        @Override
        public FoodVendor createFromParcel(Parcel in) {
            return new FoodVendor(in);
        }

        @Override
        public FoodVendor[] newArray(int size) {
            return new FoodVendor[size];
        }
    };
}