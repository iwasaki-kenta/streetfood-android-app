package hkust.com.bitwise.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.Model;
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
import hkust.com.bitwise.models.FoodCategory;
import hkust.com.bitwise.models.FoodVendor;
import hkust.com.bitwise.models.Likes;
import hkust.com.bitwise.ui.RecyclerViewAdapterBase;
import hkust.com.bitwise.ui.StableLayoutManager;
import hkust.com.bitwise.ui.ViewWrapper;
import hkust.com.bitwise.ui.items.FoodVendorItemView;
import hkust.com.bitwise.ui.items.FoodVendorItemView_;

@EFragment(R.layout.fragment_likes)
public class LikesFragment extends Fragment {
    List<Model> likesList = new ArrayList<Model>();

    @FragmentArg
    ArrayList<FoodCategory> foodCategoryList;

    @FragmentArg
    String selectedCategory;

    @ViewById
    RecyclerView list;

    @ViewById
    SwipeRefreshLayout refreshLayout;

    LikesAdapter adapter;

    RecyclerView.LayoutManager layoutManager;

    int currentPageNum = 0;
    boolean limitHit = false;

    @AfterViews
    void setupList() {
        list.setLayoutManager(layoutManager = new StableLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter = new LikesAdapter());

        likesList = new Select().from(Likes.class).execute();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        likesList = new Select().from(Likes.class).execute();
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    class LikesAdapter extends RecyclerViewAdapterBase<FoodVendor, FoodVendorItemView> {

        @Override
        protected FoodVendorItemView onCreateItemView(ViewGroup parent, int viewType) {
            return FoodVendorItemView_.build(getContext());
        }

        @Override
        public void onBindViewHolder(ViewWrapper<FoodVendorItemView> holder, int position) {
            final FoodVendor vendor = new Gson().fromJson(((Likes) likesList.get(position)).data, FoodVendor.class);

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
            return likesList.size();
        }
    }
}
