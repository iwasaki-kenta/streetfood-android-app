package hkust.com.bitwise.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import hkust.com.bitwise.R;
import hkust.com.bitwise.VendorActivity_;
import hkust.com.bitwise.models.FoodCategory;
import hkust.com.bitwise.models.FoodVendor;
import hkust.com.bitwise.ui.RecyclerViewAdapterBase;
import hkust.com.bitwise.ui.ViewWrapper;
import hkust.com.bitwise.ui.items.FoodVendorItemView;
import hkust.com.bitwise.ui.items.FoodVendorItemView_;
import hkust.com.bitwise.utils.APIUtils;

@EFragment(R.layout.fragment_browse_vendors)
public class BrowseVendorFragment extends Fragment {
    ArrayList<FoodVendor> foodVendorList = new ArrayList<FoodVendor>();

    @FragmentArg
    ArrayList<FoodCategory> foodCategoryList;

    @FragmentArg
    String selectedCategory;

    @ViewById
    RecyclerView list;

    @ViewById
    SwipeRefreshLayout refreshLayout;

    VendorAdapter adapter;

    RecyclerView.LayoutManager layoutManager;

    int currentPageNum = 0;
    boolean limitHit = false;

    @AfterViews
    void setupList() {
        list.setLayoutManager(layoutManager = new LinearLayoutManager(getContext()));
        list.setAdapter(adapter = new VendorAdapter());

        if (foodVendorList.size() == 0 && !refreshLayout.isRefreshing()) {
            loadVenues();
        }

        list.clearOnScrollListeners();
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public final void onScrolled(RecyclerView recyclerView, int dx,
                                         int dy) {
                if (!ViewCompat.canScrollVertically(recyclerView, 1) && !refreshLayout.isRefreshing() && !limitHit) {
                    currentPageNum++;
                    loadVenues();
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPageNum = 0;
                loadVenues();

            }
        });
    }

    private void loadVenues() {
        refreshLayout.setRefreshing(true);

        Ion.with(getContext()).load(APIUtils.selections(selectedCategory, currentPageNum, null)).asJsonArray().setCallback(new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }
                if (currentPageNum == 0) foodVendorList.clear();
                for (int i = 0; i < result.size(); i++) {
                    JsonObject obj = result.get(i).getAsJsonObject();
                    FoodVendor venue = new FoodVendor();
                    venue.setId(obj.get("_id").getAsString());
                    venue.setName(obj.get("name").getAsString());
                    venue.setDistrict(obj.get("district").getAsString());
                    for (JsonElement item : obj.get("items").getAsJsonArray()) {
                        venue.addMenuItemId(item.getAsString());
                    }
                    foodVendorList.add(venue);
                }
                limitHit = result.size() == 0;
                adapter.notifyDataSetChanged();

                refreshLayout.setRefreshing(false);
            }
        });

    }

    class VendorAdapter extends RecyclerViewAdapterBase<FoodVendor, FoodVendorItemView> {

        @Override
        protected FoodVendorItemView onCreateItemView(ViewGroup parent, int viewType) {
            return FoodVendorItemView_.build(getContext());
        }

        @Override
        public void onBindViewHolder(ViewWrapper<FoodVendorItemView> holder, int position) {
            final FoodVendor vendor = foodVendorList.get(position);

            FoodVendorItemView view = holder.getView();
            view.bind(vendor);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VendorActivity_.intent(getContext()).extra("vendor", vendor).start();
                }
            });
        }

        @Override
        public int getItemCount() {
            return foodVendorList.size();
        }
    }
}
