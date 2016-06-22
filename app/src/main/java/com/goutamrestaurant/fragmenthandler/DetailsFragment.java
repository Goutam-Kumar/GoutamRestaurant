package com.goutamrestaurant.fragmenthandler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.goutamrestaurant.R;
import com.goutamrestaurant.apihelper.APIHelper;
import com.goutamrestaurant.beanmodelhelper.DetailsFoodModel;
import com.goutamrestaurant.beanmodelhelper.FoodCourtModel;
import com.goutamrestaurant.sharedhelper.SharedData;
import com.goutamrestaurant.volleyhelper.VolleyRequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bubun Goutam on 6/20/2016.
 */
public class DetailsFragment extends Fragment{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<DetailsFoodModel> menuModel;
    private ViewPagerAdapter adapter;
    private LinearLayout lin_panel;
    private static final String GET_MENU_URL = APIHelper.GET_MENU_URL;
    SharedData data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_details_foodcourt,container,false);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        lin_panel = (LinearLayout) view.findViewById(R.id.lin_panel);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        data = new SharedData(getActivity());
        data.setNumber(0);
        data.setAmount(0);

        Bundle args = getArguments();
        String foodCourtId = args.getString("Food Court Id");

        HashMap<String,String> param = new HashMap<String, String>();
        param.put("partnerId",foodCourtId);
        //param.put("partnerId","569a264c8b0dc2c327e1c6a6");
        StringRequest streq = new VolleyRequestHandler().GeneralVolleyRequestWithPostParam(
                getActivity(),
                1,
                GET_MENU_URL,
                param,
                new VolleyRequestHandler.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("Response>>>",result);
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i=0;i<jsonArray.length();i++){
                               if (jsonArray.length() > 0){
                                   JSONObject object = jsonArray.getJSONObject(i);
                                   String name = object.optString("name");
                                   JSONArray products = object.getJSONArray("products");
                                   ArrayList<DetailsFoodModel> listOfMenu = new ArrayList<DetailsFoodModel>();
                                   if (products.length() > 0){
                                       for (int j=0;j<products.length();j++){
                                           JSONObject menuobject = products.getJSONObject(j);
                                           JSONObject productId = menuobject.getJSONObject("productId");
                                           String menuId = productId.optString("_id");
                                           String menuName = productId.optString("name");
                                           String menuPrice = productId.getJSONArray("productOptions").getJSONObject(0).optString("price");
                                           listOfMenu.add(new DetailsFoodModel(menuId,menuName,menuPrice,0));
                                       }
                                   }
                                   //Add Fragment
                                    FoodListFragment foodlistFragment = new FoodListFragment();
                                    Bundle bundle=new Bundle();
                                    bundle.putSerializable("list of menu",listOfMenu);
                                    foodlistFragment.setArguments(bundle);
                                    adapter.addFragment(foodlistFragment, name);
                                    adapter.notifyDataSetChanged();
                                   Log.e("Added>>>>>",""+viewPager.getAdapter().getCount());
                                   tabLayout.setTabsFromPagerAdapter(adapter);

                               }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String fail) {

                    }
                }
        );
        Volley.newRequestQueue(getActivity()).add(streq);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        //ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FoodListFragment(), "STARTERS");
        adapter.addFragment(new FoodListFragment(), "BREADS");
        adapter.addFragment(new FoodListFragment(), "COMBOS");
        adapter.addFragment(new FoodListFragment(), "MEALS");
        adapter.addFragment(new FoodListFragment(), "RICE");
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }
}
