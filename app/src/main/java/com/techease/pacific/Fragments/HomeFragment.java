package com.techease.pacific.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techease.pacific.R;


public class HomeFragment extends Fragment {

    CardView activeCard,completedCard,readyToShip;
    Fragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        activeCard=view.findViewById(R.id.card_view);
        completedCard=view.findViewById(R.id.card_view2);
        readyToShip=view.findViewById(R.id.card_view3);


        activeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new ActiveOrdersFragment();
                getActivity().setTitle("ACTIVE ORDERS");
                getFragmentManager().beginTransaction().replace(R.id.navContainer,fragment).addToBackStack("active").commit();
            }
        });
        readyToShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new ReadyToShipFragment();
                getActivity().setTitle("READY TO SHIP");
                getFragmentManager().beginTransaction().replace(R.id.navContainer,fragment).addToBackStack("ship").commit();
            }
        });
        completedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new CompletedOrdersFragment();
                getActivity().setTitle("COMPLETED ORDERS");
                getFragmentManager().beginTransaction().replace(R.id.navContainer,fragment).addToBackStack("complete").commit();
            }
        });
        return view;
    }

}
