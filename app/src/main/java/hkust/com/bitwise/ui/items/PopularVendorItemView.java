package hkust.com.bitwise.ui.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import hkust.com.bitwise.R;
import hkust.com.bitwise.models.FoodVendor;

@EViewGroup(R.layout.item_popular_vendor)
public class PopularVendorItemView extends LinearLayout {
    @ViewById
    ImageView artwork;

    @ViewById
    TextView title;

    @ViewById
    TextView orders;

    public PopularVendorItemView(Context context) {
        super(context);
    }

    public void bind(FoodVendor venue) {
        title.setText(venue.getName());
        orders.setText(String.valueOf(venue.getOrdersCompleted()) + " Total Orders");

        Picasso.with(getContext()).load(venue.getImage()).fit().centerCrop().into(artwork);
    }
}