package hkust.com.bitwise.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import hkust.com.bitwise.R;
import hkust.com.bitwise.VenueActivity_;
import hkust.com.bitwise.models.FoodCategory;
import hkust.com.bitwise.models.FoodVenue;
import hkust.com.bitwise.ui.RecyclerViewAdapterBase;
import hkust.com.bitwise.ui.ViewWrapper;
import hkust.com.bitwise.ui.items.FoodVenueItemView;
import hkust.com.bitwise.ui.items.FoodVenueItemView_;
import hkust.com.bitwise.utils.FragmentUtil;

@EFragment(R.layout.fragment_browse_venues)
public class BrowseVenuesFragment extends Fragment {
    List<FoodVenue> foodVenueList = new ArrayList<FoodVenue>();

    @ViewById
    RecyclerView list;

    VenueAdapter adapter;

    RecyclerView.LayoutManager layoutManager;

    @AfterViews
    void setupList() {
        list.setLayoutManager(layoutManager = new LinearLayoutManager(getContext()));
        list.setAdapter(adapter = new VenueAdapter());

        for (int i = 0; i < 50; i++)
            foodVenueList.add(new FoodVenue());
        adapter.notifyDataSetChanged();
    }

    class VenueAdapter extends RecyclerViewAdapterBase<FoodVenue, FoodVenueItemView> {

        @Override
        protected FoodVenueItemView onCreateItemView(ViewGroup parent, int viewType) {
            return FoodVenueItemView_.build(getContext());
        }

        @Override
        public void onBindViewHolder(ViewWrapper<FoodVenueItemView> holder, int position) {
            FoodVenueItemView view = holder.getView();
            FoodVenue venue = foodVenueList.get(position);
            view.bind(venue);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VenueActivity_.intent(getContext()).start();
                }
            });
        }

        @Override
        public int getItemCount() {
            return foodVenueList.size();
        }
    }
}
