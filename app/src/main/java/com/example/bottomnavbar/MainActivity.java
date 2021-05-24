package com.example.bottomnavbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_login = findViewById(R.id.btnLogin);
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
            }
        });
        Button btn_registro = (Button) findViewById(R.id.btnRegistro);
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(registro);
            }
        });

        String sesion = getSession("nombre");
        if (!sesion.isEmpty()) {
            //Que por defecto, salga la pantalla menú
            Intent intent = new Intent(getApplicationContext(), Menu.class);
            startActivity(intent);
            finish();
        }


    }

    private String getSession(String nombre) {
        SharedPreferences sharedPref = getSharedPreferences("loginsesion", Context.MODE_PRIVATE);
        return sharedPref.getString(nombre, "");

    }


}
