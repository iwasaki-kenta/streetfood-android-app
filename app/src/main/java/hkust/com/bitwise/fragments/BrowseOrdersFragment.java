package hkust.com.bitwise.fragments;


import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import hkust.com.bitwise.R;
import hkust.com.bitwise.VendorActivity_;
import hkust.com.bitwise.models.FoodOrder;
import hkust.com.bitwise.models.FoodVendor;
import hkust.com.bitwise.models.Likes;
import hkust.com.bitwise.ui.RecyclerViewAdapterBase;
import hkust.com.bitwise.ui.StableLayoutManager;
import hkust.com.bitwise.ui.ViewWrapper;
import hkust.com.bitwise.ui.items.FoodOrderItemView;
import hkust.com.bitwise.ui.items.FoodOrderItemView_;
import hkust.com.bitwise.ui.items.FoodVendorItemView;
import hkust.com.bitwise.ui.items.FoodVendorItemView_;

@EFragment(R.layout.fragment_browse_orders)
public class BrowseOrdersFragment extends Fragment {
    List<FoodOrder> orders = new ArrayList<FoodOrder>();

    @ViewById
    RecyclerView list;

    @ViewById
    SwipeRefreshLayout refreshLayout;

    RecyclerView.LayoutManager layoutManager;
    OrdersAdapter adapter;

    @AfterViews
    void setupList() {
        list.setLayoutManager(layoutManager = new StableLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter = new OrdersAdapter());

        orders = new Select().from(Likes.class).execute();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        orders = new Select().from(FoodOrder.class).execute();
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    class OrdersAdapter extends RecyclerViewAdapterBase<FoodOrder, FoodOrderItemView> {

        @Override
        protected FoodOrderItemView onCreateItemView(ViewGroup parent, int viewType) {
            return FoodOrderItemView_.build(getContext());
        }

        @Override
        public void onBindViewHolder(ViewWrapper<FoodOrderItemView> holder, int position) {
            final FoodOrder vendor = orders.get(position);

            FoodOrderItemView view = holder.getView();
            view.bind(vendor);
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }
    }

}
