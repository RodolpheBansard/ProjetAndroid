package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Affiche une vue aérienne de l'aéroport
 */

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Récupère les coordonnées de l'aéroport
        Intent intent = getIntent();
        message = intent.getStringExtra(SnowtamFragment.EXTRA_MESSAGE);

        SupportMapFragment mapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Affiche l'aérport sur les coordonnées récupérées
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        String[] coordinates = message.split("/");
        float latitude = Float.parseFloat(coordinates[0]);
        float longitude = Float.parseFloat(coordinates[1]);

        LatLng location = new LatLng(latitude,longitude);
        // Ajoute un marker
        map.addMarker(new MarkerOptions().position(location));
        // Zoom sur l'aéroport
        map.moveCamera(CameraUpdateFactory.newLatLng(location));
        // Passe la view en mode satellite
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

    }
}
