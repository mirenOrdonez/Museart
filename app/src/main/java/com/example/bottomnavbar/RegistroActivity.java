package com.example.bottomnavbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnregistro;
    private EditText txtnombre, txtemail, txtpassword;
    int idAdmin = 0;
    int nivel = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnregistro = findViewById(R.id.btnregistro);
        txtnombre = findViewById(R.id.txtnombre);
        txtemail = findViewById(R.id.txtemail);
        txtpassword = findViewById(R.id.txtpassword);


        btnregistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final String nombre = txtnombre.getText().toString();
        final String email = txtemail.getText().toString();
        final String password = txtpassword.getText().toString();

        Response.Listener<String> respoListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){
                        Log.d("BIEN ", "al hacer el registro");
                        Toast.makeText(getApplicationContext(), "Sing up successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Log.d("ERROR ", "al hacer el registro");
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);
                        builder.setMessage("Error en el registro")
                                .setNegativeButton("Retry",null)
                                .create().show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RegistroRequest registerRequest = new RegistroRequest(idAdmin, nombre, email, password, nivel, respoListener);
        RequestQueue queue = Volley.newRequestQueue(RegistroActivity.this);
        queue.add(registerRequest);

    }



}

