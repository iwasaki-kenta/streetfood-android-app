package hkust.com.bitwise;

import android.content.DialogInterface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hkust.com.bitwise.fragments.BrowseVendorFragment_;
import hkust.com.bitwise.models.FoodCategory;
import hkust.com.bitwise.models.FoodMenuItem;
import hkust.com.bitwise.models.FoodOrder;
import hkust.com.bitwise.models.FoodVendor;
import hkust.com.bitwise.models.Likes;
import hkust.com.bitwise.ui.NoScrollRecycler;
import hkust.com.bitwise.ui.RecyclerViewAdapterBase;
import hkust.com.bitwise.ui.ViewWrapper;
import hkust.com.bitwise.ui.items.FoodCategoryItemView;
import hkust.com.bitwise.ui.items.FoodCategoryItemView_;
import hkust.com.bitwise.ui.items.MenuItemView;
import hkust.com.bitwise.ui.items.MenuItemView_;
import hkust.com.bitwise.utils.APIUtils;
import hkust.com.bitwise.utils.FragmentUtil;

@EActivity(R.layout.activity_venue)
public class VendorActivity extends AppCompatActivity {
    @ViewById
    Toolbar toolbar;

    @Extra
    FoodVendor vendor;

    @ViewById
    CollapsingToolbarLayout toolbarLayout;

    @ViewById
    TextView district;

    @ViewById
    TextView ordersCompleted;

    List<FoodMenuItem> menuItemList = new ArrayList<FoodMenuItem>();

    @ViewById
    NoScrollRecycler menuItems;

    @ViewById
    ToggleButton likeButton;

    @ViewById
    Button takeOutButton;

    @ViewById
    ImageView toolbarImage;

    RecyclerView.LayoutManager layoutManager;
    MenuItemAdapter adapter;

    @AfterViews
    public void init() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        menuItems.setLayoutManager(layoutManager = new LinearLayoutManager(this));
        menuItems.setAdapter(adapter = new MenuItemAdapter());

        Ion.with(this).load(APIUtils.menuItems(vendor.getMenuIds())).asJsonArray().setCallback(new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }
                menuItemList.clear();
                for (int i = 0; i < result.size(); i++) {
                    JsonObject obj = result.get(i).getAsJsonObject();
                    FoodMenuItem category = new FoodMenuItem();
                    category.setId(obj.get("_id").getAsString());
                    category.setName(obj.get("name").getAsString());
                    category.setImage(obj.get("image").getAsString());
                    category.setPrice((int) obj.get("price").getAsFloat());
                    menuItemList.add(category);
                }
                adapter.notifyDataSetChanged();
            }
        });

        toolbarLayout.setTitle(vendor.getName());
        district.setText(vendor.getDistrict());
        ordersCompleted.setText(String.valueOf(vendor.getOrdersCompleted()) + " Orders Completed");

        likeButton.setChecked(new Select().from(Likes.class).where("vendorId = ?", vendor.getId()).exists());

        Ion.with(toolbarImage).centerCrop().load(vendor.getImage());

    }

    public int totalCost() {
        int sum = 0;
        for (FoodMenuItem item : menuItemList) {
            sum += item.getQuantity() * item.getPrice();
        }
        return sum;
    }

    @Click(R.id.likeButton)
    void likeVendor() {
        if (likeButton.isChecked()) {
            Likes like = new Likes(vendor.getId(), new Gson().toJson(vendor));
            like.save();
        } else {
            Likes like = new Select().from(Likes.class).where("vendorId = ?", vendor.getId()).executeSingle();
            like.delete();
        }
    }

    @Click(R.id.takeOutButton)
    void takeoutVendor() {
        int totalCost = totalCost();

        if (totalCost > 0) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            FoodOrder order = new FoodOrder();
                            order.setOrderDate(Calendar.getInstance().getTime());
                            order.setOrderedItems(menuItemList);
                            order.setVendor(vendor);
                            order.save();

                            Toast.makeText(VendorActivity.this, "Your order has successfully been placed!", Toast.LENGTH_LONG).show();
                            OrderActivity_.intent(VendorActivity.this).extra("selectedOrder", order).start();
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your order costs a total of HKD$" + String.valueOf(totalCost) + ".\n\n" +
                    "Would you like to use:\nCard [XXXX-4509 VISA]\n\n...to pay for your order?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        } else {
            Toast.makeText(VendorActivity.this, "Please select items to take away.", Toast.LENGTH_SHORT).show();
        }
    }

    class MenuItemAdapter extends RecyclerViewAdapterBase<FoodMenuItem, MenuItemView> {

        @Override
        protected MenuItemView onCreateItemView(ViewGroup parent, int viewType) {
            return MenuItemView_.build(VendorActivity.this);
        }

        @Override
        public void onBindViewHolder(ViewWrapper<MenuItemView> holder, int position) {
            final FoodMenuItem cat = menuItemList.get(position);

            MenuItemView view = holder.getView();
            view.bind(cat);
        }

        @Override
        public int getItemCount() {
            return menuItemList.size();
        }
    }
}
