package com.example.projectem08uf1android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String BBDD ="SdL";
    private final String TAULA = "puntuacion";
    private Button jugar;
    private ListView lista;
    private EditText nomET;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    protected WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jugar = (Button) findViewById(R.id.bJugar);
        jugar.setOnClickListener(this);
        lista = (ListView) findViewById(R.id.llista);
        nomET = (EditText) findViewById(R.id.NameEText);

        ArrayList<String> resultats = new ArrayList<String>();

        //Permisos per els contactes
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {


            } else {


                ActivityCompat.requestPermissions(this ,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }


        SQLiteDatabase baseDades = null;
        try {

            baseDades = this.openOrCreateDatabase(BBDD, MODE_PRIVATE, null);


            baseDades.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TAULA
                    + " (nom VARCHAR,"
                    + " puntuacio INT);");


            baseDades.execSQL("INSERT INTO "
                    + TAULA
                    + " (nom, puntuacio)"
                    + " VALUES ('alejandro', 1);");


            Cursor c = baseDades.rawQuery("SELECT nom, puntuacio"
                            + " FROM " + TAULA
                            + " WHERE puntuacio > 10 LIMIT 5;",
                    null);


            int columnaNom = c.getColumnIndex("nom");
            int columnaPuntuacio = c.getColumnIndex("puntuacio");


            if (c != null) {
                if(c.getCount() > 0) {
                    if (c.isBeforeFirst()) {
                        c.moveToFirst();
                        int i = 0;

                        do {
                                i++;
                                String nom = c.getString(columnaNom);
                                int puntuacio = c.getInt(columnaPuntuacio);

                                String nomColumnaPuntuacio = c.getColumnName(columnaPuntuacio);


                                resultats.add("" + i + ": " + nom
                                        + " (" + nomColumnaPuntuacio + ": " + puntuacio + ")");

                        } while (c.moveToNext());
                    }
                }
            }

        } finally {
            if (baseDades != null) {
                baseDades.close();
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                resultats );

        lista.setAdapter(arrayAdapter);


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item1) {
            Intent principal = new Intent(this, MainActivity.class);
            startActivity(principal);

        } else if (id == R.id.item2) {
            Intent instrucciones = new Intent(this, WebViewActivity.class);
            startActivity(instrucciones);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, JocActivity.class);
        String n1 = nomET.getText().toString();
        intent.putExtra("nomET",n1);
        startActivity(intent);
    }
}
