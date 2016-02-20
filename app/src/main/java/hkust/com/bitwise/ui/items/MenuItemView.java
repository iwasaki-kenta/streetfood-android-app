package hkust.com.bitwise.ui.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import hkust.com.bitwise.R;
import hkust.com.bitwise.models.FoodCategory;
import hkust.com.bitwise.models.FoodMenuItem;
import hkust.com.bitwise.models.FoodVendor;

@EViewGroup(R.layout.item_menu_item)
public class MenuItemView extends LinearLayout {
    private FoodMenuItem item;

    @ViewById
    TextView quantity;

    @ViewById
    TextView price;

    @ViewById
    TextView title;

    public MenuItemView(Context context) {
        super(context);
    }

    public void bind(FoodMenuItem item) {
        this.item = item;

        quantity.setText(String.valueOf(item.getQuantity()));
        title.setText(item.getName());
        price.setText("HKD$" + String.valueOf(item.getPrice()));
    }

    @Click(R.id.addButton)
    void addQuantity() {
        item.increaseQuantity();
        quantity.setText(String.valueOf(item.getQuantity()));
    }

    @Click(R.id.minusButton)
    void minusQuantity() {
        item.minusQuantity();
        quantity.setText(String.valueOf(item.getQuantity()));
    }
}
