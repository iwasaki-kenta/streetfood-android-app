package hkust.com.bitwise.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import hkust.com.bitwise.R;
import hkust.com.bitwise.models.FoodCategory;
import hkust.com.bitwise.ui.ViewWrapper;
import hkust.com.bitwise.ui.items.FoodCategoryItemView;
import hkust.com.bitwise.ui.RecyclerViewAdapterBase;
import hkust.com.bitwise.ui.items.FoodCategoryItemView_;

@EFragment(R.layout.fragment_browse_food_cat)
public class BrowseFoodCategoryFragment extends Fragment {

    List<FoodCategory> foodCategoryList = new ArrayList<FoodCategory>();

    @ViewById
    RecyclerView list;

    FoodCategoryAdapter adapter;

    RecyclerView.LayoutManager layoutManager;

    @AfterViews
    void setupList() {
        list.setLayoutManager(layoutManager = new GridLayoutManager(getContext(), 3));
        list.setAdapter(adapter = new FoodCategoryAdapter());

        for (int i = 0; i < 50; i++)
            foodCategoryList.add(new FoodCategory());
        adapter.notifyDataSetChanged();
    }

    class FoodCategoryAdapter extends RecyclerViewAdapterBase<FoodCategory, FoodCategoryItemView> {

        @Override
        protected FoodCategoryItemView onCreateItemView(ViewGroup parent, int viewType) {
            return FoodCategoryItemView_.build(getContext());
        }

        @Override
        public void onBindViewHolder(ViewWrapper<FoodCategoryItemView> holder, int position) {
            FoodCategoryItemView view = holder.getView();
            FoodCategory cat = foodCategoryList.get(position);
            view.bind(cat);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return foodCategoryList.size();
        }
    }
}
