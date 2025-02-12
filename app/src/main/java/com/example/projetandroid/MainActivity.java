package com.example.projetandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe permet de récupèrer les codes OACI saisies par l'utilisateur pour faire une requête Post pour obtenir les snowtam
 * Les snowtam sont ensuite envoyé à AffichageAactivity pour le traitement
 */

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    ViewDialog viewDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ajout du logo de l'application
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_logo);
        viewDialog= new ViewDialog(this);

    }

    /**
     * Méthode appelé pour le onClick du bouton
     * Récupère les données saisies, effectue la requête Post et lance AffichageActivity
     * @param view
     */
    public void sendMessage(View view) {
        viewDialog.showDialog();
        final Intent intent = new Intent(this, AffichageActivity.class);
        EditText editText1 = (EditText) findViewById(R.id.editText1);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        EditText editText3 = (EditText) findViewById(R.id.editText3);
        EditText editText4 = (EditText) findViewById(R.id.editText4);
        String message = "";

        if(!editText1.getText().toString().equals(""))
            message += editText1.getText().toString().toUpperCase() + "/";
        if(!editText2.getText().toString().equals(""))
            message += editText2.getText().toString().toUpperCase() + "/";
        if(!editText3.getText().toString().equals(""))
            message += editText3.getText().toString().toUpperCase() + "/";
        if(!editText4.getText().toString().equals(""))
            message += editText4.getText().toString().toUpperCase();


        POST(message, new SnowtamCallback() {
            @Override
            public void onSucess(String result) {
                viewDialog.hideDialog();
                intent.putExtra(EXTRA_MESSAGE, result);
                startActivity(intent);
            }
        });


    }

    /**
     * Méthode POST pour récupérer les snowtam sur le site "notamweb.aviation-civile.gouv"
     * @param AOCI
     * @param callback
     */
    public void POST(final String AOCI, final SnowtamCallback callback){
        final String[] AOCItab = AOCI.split("/");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://notamweb.aviation-civile.gouv.fr/Script/IHM/Bul_Aerodrome.php?Langue=FR";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    /**
                     * Une fois la requête terminé, lance la méthode onSucess lancant AffichageAcitvity
                     * @param response
                     */
                    @Override
                    public void onResponse(String response) {
                        callback.onSucess(parseHTML(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            /**
             * Envoie des paramètre pour le "form data"
             * Le paramètre pour l'heure est dynamique par rapport à l'heure et la date du téléphone
             * @return
             */
            @Override
            protected Map<String,String> getParams(){
                Calendar rightNow = Calendar.getInstance();
                // Minutes
                String minute = Integer.toString(rightNow.get(Calendar.MINUTE));
                if(minute.length() == 1){
                    minute = "0" + minute;
                }
                // Heures
                String hourOfDay = Integer.toString(rightNow.get(Calendar.HOUR_OF_DAY)-1);
                if(hourOfDay.length() == 1){
                    hourOfDay = "0" + hourOfDay;
                }
                // Jour
                String day = Integer.toString(rightNow.get(Calendar.DAY_OF_MONTH));
                if(day.length() == 1){
                    day = "0" + day;
                }
                // Mois
                String month = Integer.toString(rightNow.get(Calendar.MONTH)+1);
                if(month.length() == 1){
                    month = "0" + month;
                }
                // Année
                String year = Integer.toString(rightNow.get(Calendar.YEAR));

                // Date
                String date = year + "/" + month + "/" + day;
                // Heure
                String hour =  hourOfDay + ":" + minute;



                Map<String,String> params = new HashMap<String, String>();
                params.put("bResultat","true");
                params.put("ModeAffichage","COMPLET");
                params.put("AERO_Date_DATE",date);
                params.put("AERO_Date_HEURE",hour);
                params.put("AERO_Langue","FR");
                params.put("AERO_Duree","12");
                params.put("AERO_CM_REGLE","1");
                params.put("AERO_CM_GPS","2");
                params.put("AERO_CM_INFO_COMP","1");
                params.put("AERO_Rayon","10");
                params.put("AERO_Plafond","30");
                // Ajoute un paramètre pour chaque code OACI saisi
                for(int j = 0; j<AOCItab.length;j++){
                    params.put("AERO_Tab_Aero[" + j + "]",AOCItab[j]);
                }
                return params;
            }

            /**
             * Type de la requête
             * @return
             */
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }
        };

        queue.add(stringRequest);
    }

    /**
     * Une fois la requête effectuée cette méthode parse le HTML obtenu
     * On utilise le mot "SWEN" comme repère car il se trouve uniquement avant une snowtam
     * @param html
     * @return
     */
    public String parseHTML(String html){
        int size=0;
        for(int i=0; i<html.length(); i++){
            if(html.charAt(i) == 'S' && html.charAt(i+1) == 'W' && html.charAt(i+2) == 'E' && html.charAt(i+3) == 'N'){
                size++;
            }
        }
        String result = "";
        int compteur = 0;
        int endIndex = 0;
        int startIndex = 0;
        while (compteur<size) {
            startIndex = html.indexOf("SWEN", startIndex + 1);
            endIndex = startIndex;
            while(html.charAt(endIndex) != '<'){
                endIndex++;
            }
            compteur ++;
            result += html.substring(startIndex,endIndex) + "#";
        }
        return result;
    }

}
