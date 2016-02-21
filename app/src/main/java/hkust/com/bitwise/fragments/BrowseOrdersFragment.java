package hkust.com.bitwise.fragments;


import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import hkust.com.bitwise.R;
import hkust.com.bitwise.models.FoodOrder;
import hkust.com.bitwise.ui.StableLayoutManager;

@EFragment(R.layout.fragment_browse_orders)
public class BrowseOrdersFragment extends Fragment {
    List<FoodOrder> orders = new ArrayList<FoodOrder>();

    @ViewById
    RecyclerView list;

    @ViewById
    SwipeRefreshLayout refreshLayout;

    RecyclerView.LayoutManager layoutManager;

    @AfterViews
    void setup() {
        list.setLayoutManager(layoutManager = new StableLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }

}
