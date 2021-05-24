package com.example.bottomnavbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnregistro;
    private EditText txtnombre, txtemail, txtpassword;

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
                        Intent intent = new Intent(getApplicationContext(), Menu.class);
                        startActivity(intent);
                        finish();
                    }else{
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

        RegistroRequest registerRequest = new RegistroRequest(nombre, email, password, respoListener);
        RequestQueue queue = Volley.newRequestQueue(RegistroActivity.this);
        queue.add(registerRequest);

    }



}

    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnregistro = findViewById(R.id.btnregistro);
        txtnombre = findViewById(R.id.txtnombre);
        txtemail = findViewById(R.id.txtemail);
        txtpassword = findViewById(R.id.txtpassword);

        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }

    public void registrar() {
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, URL_SERVIDOR,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // En este apartado se programa lo que deseamos hacer en caso de no haber errores

                        if(response.equals("ERROR 1")) {
                            Toast.makeText(RegistroActivity.this, "Se deben de llenar todos los campos.", Toast.LENGTH_SHORT).show();
                        } else if(response.equals("ERROR 2")) {
                            Toast.makeText(RegistroActivity.this, "Fallo el registro.", Toast.LENGTH_SHORT).show();
                        } else if(response.equals("MENSAJE")) {
                            Toast.makeText(RegistroActivity.this, "Registro exitoso.", Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // En caso de tener algun error en la obtencion de los datos
                Toast.makeText(RegistroActivity.this, "ERROR CON LA CONEXION", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // En este metodo se hace el envio de valores de la aplicacion al servidor
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("nombre", txtnombre.getText().toString().trim());
                parametros.put("email", txtemail.getText().toString().trim());
                parametros.put("password", txtpassword.getText().toString().trim());

                Log.d("error",parametros.toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegistroActivity.this);
        requestQueue.add(stringRequest);
    }
}
*/
        /*@Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registro);

            btnregistro = findViewById(R.id.btnregistro);
            txtnombre = findViewById(R.id.txtnombre);
            txtemail = findViewById(R.id.txtemail);
            txtpassword = findViewById(R.id.txtpassword);

            btnregistro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Los campos deben estar rellenos, si no, salta el mensaje
                    if (txtnombre.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Ingrese el nombre de usuario.", Toast.LENGTH_LONG).show();
                    } else if (txtemail.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Ingrese el email.", Toast.LENGTH_LONG).show();
                    } else if (txtpassword.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Ingrese la contraseña.", Toast.LENGTH_LONG).show();
                    } else {
                        registro(txtnombre.getText().toString(), txtemail.getText().toString(), txtpassword.getText().toString());
                    }
                }
            });
        }

        private void registro (String nombre, String email, String password){
            RequestParams params = new RequestParams();
            params.put("btnlogin", "ok");
            params.put("txtnombre", nombre+"");
            params.put("txtemail", email+"");
            params.put("txtpassword", password+"");

            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://192.168.64.2/MUSEART/procesos/apiregistro.php", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        if (response.getBoolean("success")) {
                            Log.d("REGISTRADO ", response.getString("nombre"));
                            saveSession(response.getString("nombre"));
                            Toast.makeText(getApplicationContext(), "Sing up successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Sing up error", Toast.LENGTH_LONG).show();
                            Log.d("MENSAJE: ", "No se ha registrado correctamente");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Log.d("MENSAJEERROR", errorResponse.toString());
                }
            });
        }

        private void saveSession (String nombre){
            SharedPreferences sharedPref = getSharedPreferences("registrosession", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("nombre", nombre);
            editor.apply();
        }

    }*/