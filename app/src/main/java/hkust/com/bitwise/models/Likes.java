package hkust.com.bitwise.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "likes")
public class Likes extends Model {
    @Column(name = "vendorId")
    public String vendorId;

    @Column(name = "data")
    public String data;

    public Likes() {
        super();
    }

    public Likes(String vendorId, String data) {
        super();
        this.vendorId = vendorId;
        this.data = data;
    }
}
