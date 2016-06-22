package com.goutamrestaurant.fragmenthandler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.goutamrestaurant.R;
import com.goutamrestaurant.apihelper.APIHelper;
import com.goutamrestaurant.beanmodelhelper.FoodCourtModel;
import com.goutamrestaurant.debughandler.LogCollection;
import com.goutamrestaurant.volleyhelper.VolleyRequestHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Bubun Goutam on 6/19/2016.
 */
public class MainFragment extends Fragment{

    private RecyclerView recyclerview;
    private FoodCourtAdapter adapter;
    private ArrayList<FoodCourtModel> foodCourtList = new ArrayList<FoodCourtModel>();
    private static final String PARTNER_URL = APIHelper.GET_PARTNERS;
    private LogCollection logCollection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        logCollection = new LogCollection(getActivity());

        foodCourtList.clear();
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        HashMap<String,String> param = new HashMap<String, String>();
        param.put("partnerCategory","569a23728b0dc2c327e1c69c");
        param.put("venueId","569a21cc8b0dc2c327e1c69a");

        StringRequest req = new VolleyRequestHandler().GeneralVolleyRequestWithPostParam(
                getActivity(),
                1,
                PARTNER_URL,
                param,
                new VolleyRequestHandler.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {

                        try {
                            JSONObject jo = new JSONObject(result);
                            logCollection.setLogERed(jo.toString());
                            JSONArray jarry = jo.getJSONArray("partners");
                            if (jarry.length() > 0){
                                for (int i=0;i<jarry.length();i++){
                                    JSONObject jobj = jarry.getJSONObject(i);
                                    foodCourtList.add(new FoodCourtModel(
                                            jobj.optString("_id"),
                                            jobj.optString("name"),
                                            jobj.optString("pickupCounter"),
                                            jobj.optString("discountDescription"),
                                            jobj.optString("image")
                                    ));
                                }
                                //adapter.notifyDataSetChanged();
                                //recyclerview.setAdapter(adapter);

                                adapter = new FoodCourtAdapter(foodCourtList);
                                recyclerview.setAdapter(adapter);
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
        Volley.newRequestQueue(getActivity()).add(req);

        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FoodCourtAdapter(foodCourtList);
        recyclerview.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView textview = (TextView)getActivity().findViewById(R.id.tv_title);
        textview.setText("Food Court");
    }

    private class FoodCourtAdapter extends RecyclerView.Adapter<FoodCourtAdapter.ViewHolder>{

        ArrayList<FoodCourtModel> listOfFoodCourt;

        public FoodCourtAdapter(ArrayList<FoodCourtModel> listOfFoodCourt) {
            this.listOfFoodCourt = listOfFoodCourt;
        }

        @Override
        public FoodCourtAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(FoodCourtAdapter.ViewHolder holder, int position) {
            final FoodCourtModel foodcourt = listOfFoodCourt.get(position);
            holder.lin_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DetailsFragment f4 = new DetailsFragment();
                    Bundle args = new Bundle();
                    args.putString("Food Court Id",foodcourt.getFoodCourtId());
                    f4.setArguments(args);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                    ft.replace(R.id.frame_container, f4);
                    ft.addToBackStack(null);
                    ft.commit();
                    TextView textview = (TextView)getActivity().findViewById(R.id.tv_title);
                    textview.setText(foodcourt.getFoodCourtName());
                }
            });
            holder.tv_name.setText(foodcourt.getFoodCourtName());
            holder.tv_address.setText(foodcourt.getFoodCourtAddress());
            holder.tv_offer.setText(foodcourt.getFoodCourtOffer());
            holder.lin_container.setTag(foodcourt.getFoodCourtId());
            Picasso.with(getActivity())
                    .load(foodcourt.getFoodCourtURL())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .noFade().resize(150,150)
                    .into(holder.img_logo);
        }

        @Override
        public int getItemCount() {
            return listOfFoodCourt.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout lin_container;
            ImageView img_logo;
            TextView tv_name;
            TextView tv_address;
            TextView tv_offer;

            public ViewHolder(View itemView) {
                super(itemView);

                lin_container = (LinearLayout) itemView.findViewById(R.id.lin_container);
                img_logo = (ImageView) itemView.findViewById(R.id.img_logo);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_address = (TextView) itemView.findViewById(R.id.tv_address);
                tv_offer = (TextView) itemView.findViewById(R.id.tv_offer);
            }
        }
    }
}
