package com.example.food.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.R;
import com.example.food.SearchActivity;
import com.example.food.adapter.CategoryAdapter;
import com.example.food.adapter.FoodAdapter;
import com.example.food.adapterSlide.AdapterViewPayer;
import com.example.food.adapterSlide.ModelItem;
import com.example.food.callback.Categoriescallback;
import com.example.food.callback.Foodcallback;
import com.example.food.daofirebase.DaoCategories;
import com.example.food.daofirebase.DaoFood;
import com.example.food.model.Categories;
import com.example.food.model.Food;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentHome extends Fragment {
    private AdapterViewPayer adapter;
    SliderView sliderView;
    EditText edtsearch;
    RecyclerView rcvhome,rcvmonan;
    ArrayList<Categories> datacategories;
    DaoCategories daoCategories;
    TextView txtslogan;
    CategoryAdapter categoryAdapter;
    FoodAdapter foodAdapter;
    ArrayList<Food> foodArrayList;
    DaoFood daoFood;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragmenthome,container,false);
        sliderView = view.findViewById(R.id.imageSlider);
        rcvhome = view.findViewById(R.id.rcvhome);
        rcvmonan = view.findViewById(R.id.rcvmonan);
        txtslogan = view.findViewById(R.id.txtslogan);
        daoCategories = new DaoCategories(getActivity());
        datacategories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(datacategories,getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rcvhome.setLayoutManager(linearLayoutManager);
        rcvhome.setHasFixedSize(true);
        rcvhome.setAdapter(categoryAdapter);
        Date currentTime = Calendar.getInstance().getTime();
        if (currentTime.getHours() < 12) {
            txtslogan.setText("B???n mu???n ??n g?? trong S??ng nay?");
            foodArrayList = new ArrayList<>();
            foodAdapter = new FoodAdapter(foodArrayList,getActivity());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
            rcvmonan.setLayoutManager(gridLayoutManager);
            rcvmonan.setHasFixedSize(true);
            rcvmonan.setAdapter(foodAdapter);
            daoFood = new DaoFood(getActivity());
            daoFood.getAll(new Foodcallback() {
                @Override
                public void onSuccess(ArrayList<Food> lists) {
                    foodArrayList.clear();
                    for (int i =0;i<lists.size();i++){
                        if (lists.get(i).getStatus().equalsIgnoreCase("S??ng")){
                            foodArrayList.add(lists.get(i));
                            foodAdapter.notifyDataSetChanged();
                        }
                    }

                }
                @Override
                public void onError(String message) {

                }
            });
        } else if (currentTime.getHours() <= 18 && currentTime.getHours() >= 14 ){
            txtslogan.setText("B???n mu???n ??n g?? trong Chi???u nay?");
            foodArrayList = new ArrayList<>();
            foodAdapter = new FoodAdapter(foodArrayList,getActivity());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
            rcvmonan.setLayoutManager(gridLayoutManager);
            rcvmonan.setHasFixedSize(true);
            rcvmonan.setAdapter(foodAdapter);
            daoFood = new DaoFood(getActivity());
            daoFood.getAll(new Foodcallback() {
                @Override
                public void onSuccess(ArrayList<Food> lists) {
                    foodArrayList.clear();
                    for (int i =0;i<lists.size();i++){
                        if (lists.get(i).getStatus().equalsIgnoreCase("Chi???u")){
                            foodArrayList.add(lists.get(i));
                            foodAdapter.notifyDataSetChanged();
                        }
                    }

                }
                @Override
                public void onError(String message) {

                }
            });
        }else if (currentTime.getHours() > 18){
            txtslogan.setText("B???n mu???n ??n g?? trong T???i nay?");
            foodArrayList = new ArrayList<>();
            foodAdapter = new FoodAdapter(foodArrayList,getActivity());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
            rcvmonan.setLayoutManager(gridLayoutManager);
            rcvmonan.setHasFixedSize(true);
            rcvmonan.setAdapter(foodAdapter);
            daoFood = new DaoFood(getActivity());
            daoFood.getAll(new Foodcallback() {
                @Override
                public void onSuccess(ArrayList<Food> lists) {
                    foodArrayList.clear();
                    for (int i =0;i<lists.size();i++){
                        if (lists.get(i).getStatus().equalsIgnoreCase("T???i")){
                            foodArrayList.add(lists.get(i));
                            foodAdapter.notifyDataSetChanged();
                        }
                    }

                }
                @Override
                public void onError(String message) {

                }
            });
        }
        daoCategories.getAll(new Categoriescallback() {
            @Override
            public void onSuccess(ArrayList<Categories> lists) {
                datacategories.clear();
                datacategories.addAll(lists);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }
        });
        edtsearch  = (EditText)view.findViewById(R.id.edtsearch);
        edtsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });

        adapter = new AdapterViewPayer(getContext());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        renewItems(view);
        removeLastItem(view);
        addNewItem(view);
        return view;
    }
    public void renewItems(View view) {
        List<ModelItem> sliderItemList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ModelItem sliderItem = new ModelItem();
            if (i == 0) {
                sliderItem.setImageurl("https://lemoney.com.vn/wp-content/uploads/2021/05/chuong_trinh_khuyen_mai-800x560.png");
            } else if(i == 1) {
                sliderItem.setImageurl("https://vuakhuyenmai.vn/wp-content/uploads/pizza-hut-khuyen-mai-mua1-tang1-1-11-2021.jpg.pagespeed.ce.UZ3fmMKTaN.jpg");
            }
            else if(i == 2) {
                sliderItem.setImageurl("https://pizzaparadise.vn/wp-content/uploads/1-pizza-khuyen-mai.jpg");
            }
            else if(i == 3) {
                sliderItem.setImageurl("https://vuakhuyenmai.vn/wp-content/uploads/popeyes-khuyen-mai-moi-nhat-13-12-21.jpg");
            }
            else if(i == 4) {
                sliderItem.setImageurl("https://vuakhuyenmai.vn/wp-content/uploads/burger-king-tang-coca-7-12-21.jpg");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.ViewPagerAdapter(sliderItemList);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteitem(adapter.getCount() - 1);
    }

    public void addNewItem(View view) {
        ModelItem sliderItem = new ModelItem();
//        sliderItem.setDescription("Re");
        sliderItem.setImageurl("https://images.foody.vn/res/g92/915164/prof/s1242x600/image-002863fa-201019120313.jpeg");
        adapter.addItem(sliderItem);
    }

    @Override
    public void onResume() {
        super.onResume();
//        foodAdapter.notifyDataSetChanged();
//        categoryAdapter.notifyDataSetChanged();
    }
}
