package hkust.com.bitwise.fragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import hkust.com.bitwise.R;
import hkust.com.bitwise.models.FoodCategory;
import hkust.com.bitwise.ui.RecyclerViewAdapterBase;
import hkust.com.bitwise.ui.ViewWrapper;
import hkust.com.bitwise.ui.items.FoodCategoryItemView;
import hkust.com.bitwise.ui.items.FoodCategoryItemView_;
import hkust.com.bitwise.utils.APIUtils;
import hkust.com.bitwise.utils.FragmentUtil;

@EFragment(R.layout.fragment_browse_food)
public class BrowseFoodFragment extends Fragment {

    ArrayList<FoodCategory> foodCategoryList = new ArrayList<FoodCategory>();

    @ViewById
    RecyclerView list;

    FoodCategoryAdapter adapter;

    RecyclerView.LayoutManager layoutManager;

    @AfterViews
    void setupList() {
        list.setLayoutManager(layoutManager = new GridLayoutManager(getContext(), 2));
        list.setAdapter(adapter = new FoodCategoryAdapter());

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setTitle("Loading");
        dialog.setMessage("Looking for closest sources of street food...");
        dialog.show();

        Ion.with(getContext()).load(APIUtils.selections()).asJsonArray().setCallback(new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }

                try {
                    foodCategoryList.clear();
                    for (int i = 0; i < result.size(); i++) {
                        JsonObject obj = result.get(i).getAsJsonObject();
                        FoodCategory category = new FoodCategory();
                        category.setName(obj.get("name").getAsString());
                        category.setImage(obj.get("image").getAsString());
                        foodCategoryList.add(category);
                    }
                } catch (Exception ex) {}

                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    class FoodCategoryAdapter extends RecyclerViewAdapterBase<FoodCategory, FoodCategoryItemView> {

        @Override
        protected FoodCategoryItemView onCreateItemView(ViewGroup parent, int viewType) {
            return FoodCategoryItemView_.build(getContext());
        }

        @Override
        public void onBindViewHolder(ViewWrapper<FoodCategoryItemView> holder, int position) {
            final FoodCategory cat = foodCategoryList.get(position);

            FoodCategoryItemView view = holder.getView();
            view.bind(cat);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentUtil.gotoFragment(getFragmentManager(), BrowseVendorFragment_.builder()
                            .parcelableArrayListArg("foodCategoryList", foodCategoryList)
                            .arg("selectedCategory", cat.getName()).build(), true, null);
                }
            });
        }

        @Override
        public int getItemCount() {
            return foodCategoryList.size();
        }
    }
}
