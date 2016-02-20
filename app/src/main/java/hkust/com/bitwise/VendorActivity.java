package hkust.com.bitwise;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

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
import java.util.List;

import hkust.com.bitwise.fragments.BrowseVendorFragment_;
import hkust.com.bitwise.models.FoodCategory;
import hkust.com.bitwise.models.FoodMenuItem;
import hkust.com.bitwise.models.FoodVendor;
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

    List<FoodMenuItem> menuItemList = new ArrayList<FoodMenuItem>();

    @ViewById
    RecyclerView menuItems;

    @ViewById
    ToggleButton likeButton;

    @ViewById
    Button takeOutButton;

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

    }

    @Click(R.id.likeButton)
    void likeVendor() {

    }

    @Click(R.id.takeOutButton)
    void takeoutVendor() {
        finish();
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
