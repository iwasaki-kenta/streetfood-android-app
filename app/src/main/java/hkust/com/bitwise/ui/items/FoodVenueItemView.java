package hkust.com.bitwise.ui.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import hkust.com.bitwise.R;
import hkust.com.bitwise.models.FoodCategory;
import hkust.com.bitwise.models.FoodVenue;

@EViewGroup(R.layout.item_venue)
public class FoodVenueItemView extends LinearLayout {
    @ViewById
    ImageView artwork;

    public FoodVenueItemView(Context context) {
        super(context);
    }

    public void bind(FoodVenue venue) {
        // TODO: Bind food venue values to view here.
        Ion.with(artwork).load("http://www.placeholdr.pics/250/250");
    }
}
