package com.goutamrestaurant.fragmenthandler;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goutamrestaurant.R;
import com.goutamrestaurant.beanmodelhelper.DetailsFoodModel;
import com.goutamrestaurant.sharedhelper.SharedData;

import java.util.ArrayList;

/**
 * Created by Bubun Goutam on 6/20/2016.
 */
public class FoodListFragment extends Fragment
implements View.OnTouchListener{
    private RecyclerView recyclerView;
    private Button btn_review;
    private MenuAdapter adapter;
    private ArrayList<DetailsFoodModel> listOfMenus = new ArrayList<DetailsFoodModel>();
    int totalAmount = 0,totalNumber = 0;
    SharedData data ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foodlist,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //btn_review = (Button) view.findViewById(R.id.btn_review);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //btn_review.setOnTouchListener(this);
        data = new SharedData(getActivity());
        Bundle args = getArguments();
        listOfMenus = (ArrayList<DetailsFoodModel>) args.getSerializable("list of menu");
        adapter = new MenuAdapter(listOfMenus);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            /*case R.id.btn_review:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    btn_review.setBackgroundResource(R.drawable.btn_review_active);
                    btn_review.setTextColor(Color.parseColor("#f44437"));
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    btn_review.setBackgroundResource(R.drawable.btn_review);
                    btn_review.setTextColor(Color.parseColor("#ffffff"));
                }
                break;*/
        }
        return false;
    }

    private class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{

        ArrayList<DetailsFoodModel> listoffood;

        public MenuAdapter(ArrayList<DetailsFoodModel> listoffood) {
            this.listoffood = listoffood;
        }

        @Override
        public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(final MenuAdapter.ViewHolder holder, final int position) {
            final DetailsFoodModel food = listoffood.get(position);
            int count = food.getNumber();
            if (count > 0){
                holder.tv_num.setVisibility(View.VISIBLE);
                holder.img_minus.setVisibility(View.VISIBLE);
            }else {
                holder.tv_num.setVisibility(View.INVISIBLE);
                holder.img_minus.setVisibility(View.INVISIBLE);
            }
            holder.tv_name.setText(food.getMenuName());
            holder.tv_price.setText("₹" +food.getMenuPrice());
            holder.tv_num.setText(""+food.getNumber());
            holder.img_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = listoffood.get(position).getNumber();
                        if (count >= 0){
                            listoffood.get(position).setNumber(count + 1);
                            holder.tv_num.setText(""+listoffood.get(position).getNumber());
                            holder.tv_num.setVisibility(View.VISIBLE);
                            holder.img_minus.setVisibility(View.VISIBLE);
                            /*totalNumber = totalNumber + 1;
                            DetailsFragment detFrag = new DetailsFragment();
                            Bundle bnd = new Bundle();
                            bnd.putInt("total number",totalNumber);
                            detFrag.setArguments(bnd);*/

                            int number = data.getNumber() + 1;
                            LinearLayout lin_panel = (LinearLayout) getActivity().findViewById(R.id.lin_panel);
                            lin_panel.setVisibility(View.VISIBLE);
                            TextView lin_tv_num = (TextView) getActivity().findViewById(R.id.lin_tv_num);
                            lin_tv_num.setText(""+number);
                            data.setNumber(number);

                            int amount = data.getAmount() + Integer.parseInt(food.getMenuPrice());
                            TextView lin_tv_total_price = (TextView) getActivity().findViewById(R.id.lin_tv_total_price);
                            lin_tv_total_price.setText(""+amount);
                            data.setAmount(amount);
                        }
                }
            });

            holder.img_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = listoffood.get(position).getNumber();
                    if (count > 0){
                        listoffood.get(position).setNumber(count - 1);
                        holder.tv_num.setText(""+listoffood.get(position).getNumber());
                        holder.tv_num.setVisibility(View.VISIBLE);
                        holder.img_minus.setVisibility(View.VISIBLE);
                        /*totalNumber = totalNumber - 1;
                        DetailsFragment detFrag = new DetailsFragment();
                        Bundle bnd = new Bundle();
                        bnd.putInt("total number",totalNumber);
                        detFrag.setArguments(bnd);*/

                        int number = data.getNumber() - 1;
                        LinearLayout lin_panel = (LinearLayout) getActivity().findViewById(R.id.lin_panel);
                        lin_panel.setVisibility(View.VISIBLE);
                        TextView lin_tv_num = (TextView) getActivity().findViewById(R.id.lin_tv_num);
                        lin_tv_num.setText(""+number);
                        data.setNumber(number);

                        int amount = data.getAmount() - Integer.parseInt(food.getMenuPrice());
                        TextView lin_tv_total_price = (TextView) getActivity().findViewById(R.id.lin_tv_total_price);
                        lin_tv_total_price.setText(""+amount);
                        data.setAmount(amount);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return listoffood.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout lin_item;
            TextView tv_name;
            TextView tv_price;
            ImageView img_plus;
            ImageView img_minus;
            TextView tv_num;
            ImageView img_fv;

            public ViewHolder(View itemView) {
                super(itemView);

                lin_item = (LinearLayout) itemView.findViewById(R.id.lin_item);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_price = (TextView) itemView.findViewById(R.id.tv_price);
                img_plus = (ImageView) itemView.findViewById(R.id.img_plus);
                img_minus = (ImageView) itemView.findViewById(R.id.img_minus);
                tv_num = (TextView) itemView.findViewById(R.id.tv_num);
                img_fv = (ImageView) itemView.findViewById(R.id.img_fv);
            }
        }
    }
}
