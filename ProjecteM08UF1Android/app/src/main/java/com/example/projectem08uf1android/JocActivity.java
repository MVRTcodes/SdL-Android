package com.example.projectem08uf1android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class JocActivity extends AppCompatActivity {

    //BBDD

    private TextView nomET;
    private TextView tvPuntuacio;
    private TextView tvAciertos;
    private final String BBDD ="SdLL";
    private final String TAULA = "puntuacion";

    //Color del fons del view dins de GridView

    private ColorDrawable bgColor;
    private int colorId;
    private ArrayList<String> RespostesFinals = new ArrayList<String>(){{
    }};

    //Contador per a numero de contactes

    private int contador = 0;
    private int contadordiago = 0;
    private int NUMCONTACTES = 7;

    //Respostes y comparador (posres) posicions del grid utilitzades
    //(defres) posicions del grid bloquejades

    private String Resposta = "";
    private ArrayList<String> posres = new ArrayList<String>();
    private ArrayList<String> defres = new ArrayList<String>();

    //Array de chars amb l'abecedari per omplir el GridView amb lletres random
    //Arralist per omplir el GridView

    Random r = new Random();
    char[] array = new char[]{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
     'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    ArrayList<String> ar = new ArrayList<String>();
    private final int rdm1= r.nextInt(4),rdm2= r.nextInt(4),rdm3 = r.nextInt(4),rdm4 = r.nextInt(4),rdm5 = r.nextInt(4)
    private final int rdm6 = r.nextInt(4),rdm7 = r.nextInt(4),rdm8 = r.nextInt(4),rdm9 = r.nextInt(4), rdm10 = r.nextInt(3);

    //Contactes

    private ArrayList<String> ContactesTotals = new ArrayList<String>();
    private ListView lvC;

    //Puntuació partida

    private int aciertos = 0;
    private int puntuacion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Cursor Contactes
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {

            //String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String[] n = name.split(" ");
            ContactesTotals.add(n[0].toUpperCase());

        }
        phones.close();

        //Filtre de Contactes (4/6)lletres / No es repeteix / Paraula en Diagonal de 4 lletres
        for(int i=0; i<ContactesTotals.size();i++){

            int rdm = r.nextInt(ContactesTotals.size());

            if(contador<=NUMCONTACTES-1) {
                if(!ContactesTotals.contains("/")) {
                    if(!RespostesFinals.contains(ContactesTotals.get(rdm))) {
                        if(ContactesTotals.get(rdm).length()<6 && ContactesTotals.get(rdm).length()>=4) {
                            if(ContactesTotals.get(rdm).length()==4 && contadordiago==0){

                                RespostesFinals.add(0,ContactesTotals.get(rdm).toUpperCase());
                                contadordiago++;
                                contador++;

                            }else {

                                RespostesFinals.add(ContactesTotals.get(rdm).toUpperCase());
                                contador++;

                            }

                        }

                    }
                }
            }
        }

        setContentView(R.layout.activity_joc);


        nomET = (TextView) findViewById(R.id.nomView4);
        tvPuntuacio = (TextView) findViewById(R.id.tvPuntuacio);
        tvAciertos = (TextView) findViewById(R.id.tvAciertos);
        lvC = (ListView) findViewById(R.id.listaContactos);

        //Intent per a obtenir el nom del jugador

        Intent intent = getIntent();
        final String nomETStr =intent.getStringExtra("nomET");

        //Afegeix la arraylist amb els mots que s'introdueixen dins de la sopa de lletres
        //Dins de la ListView gracies a un ArrayAdapter
        ArrayAdapter<String> arrayA = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                RespostesFinals );

        lvC.setAdapter(arrayA);

        nomET.setText(nomETStr);
        tvPuntuacio.setText(String.valueOf(puntuacion));

        //Bucle per afegir les paraules dins de l'Arraylist amb el contingut del GridView(Sopa de lletres)
        Log.i("addW", "Randoms: "+rdm1+rdm2+rdm3+rdm4+rdm5);
        for (int i=0;i<81;i++){
            boolean next = false;
            //Vertical
            switch(i){
                case 17:
                    ar.add(String.valueOf(RespostesFinals.get(6).charAt(0)).toUpperCase());
                    next = true;
                    break;
                case 26:
                    ar.add(String.valueOf(RespostesFinals.get(6).charAt(1)).toUpperCase());
                    next = true;
                    break;
                case 35:
                    ar.add(String.valueOf(RespostesFinals.get(6).charAt(2)).toUpperCase());
                    next = true;
                    break;
                case 44:
                    if(RespostesFinals.get(6).length()>=4) {
                        ar.add(String.valueOf(RespostesFinals.get(6).charAt(3)).toUpperCase());
                        next = true;
                    }
                    break;
                case 53:
                    if(RespostesFinals.get(6).length()>=5) {
                        ar.add(String.valueOf(RespostesFinals.get(6).charAt(4)).toUpperCase());
                        next = true;
                    }
                    break;
            }
            if(next) continue;

            //Horizontal
            if(i==rdm1){

                String rf = RespostesFinals.get(5);
                Log.i("addW", String.valueOf(i));
                Log.i("addW", rf);
                for(int j = 0; j < rf.length(); j++){
                    ar.add(String.valueOf(rf.charAt(j)).toUpperCase());
                }
                i+=rf.length()-1;
                continue;
            }
            if(i==rdm2+9){
                String rf = RespostesFinals.get(1);
                Log.i("addW", String.valueOf(i));
                Log.i("addW", rf);
                for(int j = 0; j < rf.length(); j++){
                    ar.add(String.valueOf(rf.charAt(j)).toUpperCase());
                }
                i+=rf.length()-1;
                continue;
            }

            if(i==rdm3+(9*2)){
                String rf = RespostesFinals.get(2);
                Log.i("addW", String.valueOf(i));
                Log.i("addW", rf);
                for(int j = 0; j < rf.length(); j++){
                    ar.add(String.valueOf(rf.charAt(j)).toUpperCase());
                }
                i+=rf.length()-1;
                continue;
            }
            if(i==rdm4+(9*3)){
                String rf = RespostesFinals.get(3);
                Log.i("addW", String.valueOf(i));
                Log.i("addW", rf);
                for(int j = 0; j < rf.length(); j++){
                    ar.add(String.valueOf(rf.charAt(j)).toUpperCase());
                }
                i+=rf.length()-1;
                continue;
            }
            if(i==rdm5+(9*4)){
                String rf = RespostesFinals.get(4);
                Log.i("addW", String.valueOf(i));
                Log.i("addW", rf);
                for(int j = 0; j < rf.length(); j++){
                    ar.add(String.valueOf(rf.charAt(j)).toUpperCase());
                }
                i+=rf.length()-1;
                continue;
            }

            //Diagonal
            if(i==45+rdm10){
                ar.add(String.valueOf(RespostesFinals.get(0).charAt(0)).toUpperCase());
                i++;
            }
            if(i==55+rdm10){
                ar.add(String.valueOf(RespostesFinals.get(0).charAt(1)).toUpperCase());
                i++;
            }
            if(i==65+rdm10){
                ar.add(String.valueOf(RespostesFinals.get(0).charAt(2)).toUpperCase());
                i++;
            }
            if(i==75+rdm10){
                ar.add(String.valueOf(RespostesFinals.get(0).charAt(3)).toUpperCase());
                i++;
            }

            //en cas de no colocar cap lletra dessitjada
            //coloca una lletra aleatoria de l'abecedari

            char c = array[r.nextInt(array.length)];
            ar.add(String.valueOf(c).toUpperCase());
        }

        //GridView amb la sopa de lletres

        GridView gridView;

        gridView = (GridView) findViewById(R.id.GridView1);
        //Insereix la ArrayList amb el contingut de la sopa de lletres
        //dins del GridView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, ar);

        gridView.setAdapter(adapter);
        gridView.setBackgroundColor(Color.WHITE);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //Si la posició que has clicat ha sigut utilitzada per resoldre una paraula
                //ignora qualsevol cambi que s'apliqui a la mateixa
                if(!defres.contains(String.valueOf(position))) {
                    //Comprova el color i si no es del dessitjat el cambia a el dessitjat
                    TextView tv = (TextView) v;
                    try {
                        bgColor = (ColorDrawable) v.getBackground();
                        colorId = bgColor.getColor();
                        if (colorId == Color.rgb(255,129,98)) {
                            //Elimina l'ultima lletra seleccionada
                            v.setBackgroundColor(Color.WHITE);
                            tv.setTextColor(Color.BLACK);
                            Resposta = Resposta.substring(0, Resposta.length() - 1);
                            Log.i("Palabra", Resposta);
                            if (posres.contains(String.valueOf(position))) posres.remove(String.valueOf(position));

                        } else {
                            //Afegeix la lletra dins del String que forma la resposta.
                            v.setBackgroundColor(Color.rgb(255,129,98));
                            tv.setTextColor(Color.WHITE);
                            String newWord = String.valueOf(((TextView) v).getText());
                            Resposta += newWord;
                            posres.add(String.valueOf(position));
                            Log.i("Palabra", Resposta);
                        }
                    } catch (Exception e) {
                        v.setBackgroundColor(Color.rgb(255,129,98));
                        tv.setTextColor(Color.WHITE);
                        String newWord = String.valueOf(((TextView) v).getText());
                        Resposta += newWord;
                        posres.add(String.valueOf(position));
                        Log.i("Palabra", Resposta);
                    }

                    if (RespostesFinals.contains(Resposta)) {
                        defres = (ArrayList<String>) posres.clone();
                        puntuacion += Resposta.length()*10;
                        aciertos ++;
                        Resposta = "";
                        tvPuntuacio.setText(String.valueOf(puntuacion));
                        tvAciertos.setText("Mots Adivinats: "+aciertos);

                    }
                    if(aciertos==NUMCONTACTES){

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(JocActivity.this);
                        builder1.setMessage("Has ganado! tu puntuación ha sido de "+puntuacion+" puntos");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "YAHOO!",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        finish();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();

                        ResultadosdentroBBDD(puntuacion,String.valueOf(java.time.LocalDate.now()), nomETStr);
                        alert11.show();
                    }


                    /*Toast.makeText(getApplicationContext(),
                            Resposta, Toast.LENGTH_SHORT).show();*/
                }



            }
            });





    }
    protected void ResultadosdentroBBDD(int puntuacion,String data, String nombre){

        SQLiteDatabase baseDades = null;
        try {
            baseDades = this.openOrCreateDatabase(BBDD, MODE_PRIVATE, null);

            baseDades.execSQL("INSERT INTO "
                    + TAULA
                    + " (nom, data, puntuacio)"
                    + " VALUES (\'"+nombre+"\', \'"+data+"\', "+puntuacion+");");

        } finally {
            if (baseDades != null) {
                baseDades.close();
            }
        }
    }
}
