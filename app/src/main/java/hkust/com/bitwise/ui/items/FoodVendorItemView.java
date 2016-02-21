package hkust.com.bitwise.ui.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import hkust.com.bitwise.R;
import hkust.com.bitwise.models.FoodVendor;

@EViewGroup(R.layout.item_vendor)
public class FoodVendorItemView extends LinearLayout {
    @ViewById
    ImageView artwork;

    @ViewById
    TextView title;

    @ViewById
    TextView district;

    public FoodVendorItemView(Context context) {
        super(context);
    }

    public void bind(FoodVendor venue) {
        title.setText(venue.getName());
        district.setText(venue.getDistrict());

        Picasso.with(getContext()).load(venue.getImage()).fit().centerCrop().into(artwork);
    }
}
