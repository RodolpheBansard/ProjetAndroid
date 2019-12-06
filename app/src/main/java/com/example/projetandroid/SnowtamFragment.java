package com.example.projetandroid;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class SnowtamFragment extends Fragment {
    View view;
    private TextView airportName, dateHourMeasurment;
    public SnowtamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_snowtam, container, false);
        airportName = view.findViewById(R.id.Airport);
        dateHourMeasurment = view.findViewById(R.id.datehour);
        String message = getArguments().getString("message");
        String[] snowtam = message.split("\n");
        airportName.setText(snowtam[0]);
        dateHourMeasurment.setText(snowtam[1]);

        createView(snowtam);

        return view;
    }

    public void createView(String[] snowtam){
        ConstraintLayout myLayout = view.findViewById(R.id.myLayout);
        int index=13;

        for(int i=2; i<snowtam.length-1; i++){
            TextView textView = (TextView) myLayout.getChildAt(index);
            textView.setText(snowtam[i]);
            index++;
        }
        deleteNotUseChilds(index);

    }

    public void deleteNotUseChilds(int index){
        ConstraintLayout myLayout = view.findViewById(R.id.myLayout);
        int childCount = myLayout.getChildCount();
        for (int i = index; i < childCount; i++) {
            View textView = myLayout.getChildAt(i);
            textView.setVisibility(View.GONE);
        }
    }

}
