package com.example.projetandroid;


import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SnowtamFragment extends Fragment {
    View view;
    String[] decryptedSnowtam;
    String cryptedSnowtam;
    private TextView airportName, dateHourMeasurment;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
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
        decryptedSnowtam = message.split("#")[0].split("\n");
        cryptedSnowtam =  message.split("#")[1];
        airportName.setText(decryptedSnowtam[0]);
        dateHourMeasurment.setText(decryptedSnowtam[1]);

        createView(decryptedSnowtam);

        ImageButton Mapbutton = view.findViewById(R.id.MapButton);
        Mapbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                String message = decryptedSnowtam[decryptedSnowtam.length-1];
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });

        ImageButton CryptedButton = view.findViewById(R.id.CryptedButton);
        CryptedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CryptedSnowtamActivity.class);
                String message = cryptedSnowtam;
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });

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
