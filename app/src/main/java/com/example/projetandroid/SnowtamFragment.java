package com.example.projetandroid;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SnowtamFragment extends Fragment {

    private TextView textView;
    public SnowtamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_snowtam, container, false);
        textView = view.findViewById(R.id.snowtam_display);
        String message = getArguments().getString("message");
        textView.setText(message);
        return view;
    }

}
