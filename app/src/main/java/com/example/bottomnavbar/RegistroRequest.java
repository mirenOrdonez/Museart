package com.example.bottomnavbar;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistroRequest extends StringRequest {

   //AQUI SE LLAMA AL .PHP DEL REGISTROS

    private static final String REGISTER_REQUEST_URL="http://192.168.64.2/MUSEART/procesos/apiregistro.php";

    private Map<String, String> params;

    //STRING POR CAMPO A RELLENAR
    public RegistroRequest(int idAdmin, String nombre, String email, String password, int nivel,  Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params= new HashMap<>();

        //PARAMETRO POR CAMPO A RELLENAR
        params.put("idAdmin", String.valueOf(idAdmin));
        params.put("nombre", nombre);
        params.put("email", email);
        params.put("password", password);
        params.put("nivel", String.valueOf(nivel));

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
