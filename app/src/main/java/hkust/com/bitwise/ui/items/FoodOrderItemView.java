package hkust.com.bitwise.ui.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import hkust.com.bitwise.R;
import hkust.com.bitwise.models.FoodOrder;
import hkust.com.bitwise.models.FoodVendor;

@EViewGroup(R.layout.item_order)
public class FoodOrderItemView extends LinearLayout {
    @ViewById
    ImageView artwork;

    @ViewById
    TextView vendorName;

    @ViewById
    TextView district;

    public FoodOrderItemView(Context context) {
        super(context);
    }

    public void bind(FoodOrder order) {
        vendorName.setText(order.getVendor().getName());
        district.setText(order.getOrderDate().toString());

        Picasso.with(getContext()).load(order.getVendor().getImage()).fit().centerCrop().into(artwork);
    }
}
