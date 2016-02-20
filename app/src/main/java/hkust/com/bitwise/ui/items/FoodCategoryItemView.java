package hkust.com.bitwise.ui.items;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import hkust.com.bitwise.R;
import hkust.com.bitwise.models.FoodCategory;

@EViewGroup(R.layout.item_food_category)
public class FoodCategoryItemView extends LinearLayout {
    @ViewById
    ImageView artwork;

    public FoodCategoryItemView(Context context) {
        super(context);
    }

    public void bind(FoodCategory cat) {
        // TODO: Bind food category values to view here.
        Glide.with(getContext()).load("http://www.placeholdr.pics/250/250").into(artwork);
    }
}
