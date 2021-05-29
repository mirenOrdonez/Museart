package com.example.bottomnavbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.bottomnavbar.DbManager.TABLA_OBRAS;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {

    private DbManager db;
    private ListView lista;
    private Cursor c;
    private obraAdapter adapter;

    public GalleryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_gallery, container, false);

        db = new DbManager(getActivity().getApplicationContext());

        getObras();

        //Llenamos el list view
        lista = vista.findViewById(R.id.listaObras);
        c = db.getCursor("obras", "1");
        if (c != null) {
            try {
                c.moveToFirst();
                adapter = new obraAdapter(getActivity().getApplicationContext(), c);
                lista.setAdapter(adapter);
            } catch (Exception e) {
                Log.d("error", e.getMessage());
            }

        }
        else {
            Log.d("ERROR", "listview");
        }
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Log.d("Posicion", String.valueOf(i));
                Cursor cursorObra = (Cursor) lista.getItemAtPosition(i);
                //Cursor cursorObra = db.getCursor("obras", "_id="+i);
                Log.d("posicion cursor", String.valueOf(cursorObra.getPosition()));
                cursorObra = db.getCursor1("obras", "_id="+i, i);
                int id = cursorObra.getColumnIndexOrThrow("_id");
                Log.d("id", String.valueOf(id));
                startActivity(new Intent(getActivity().getApplicationContext(), ObraActivity.class).putExtra("_id", id));

            }
        });

        return vista;
    }

    private void getObras() {
        RequestParams params = new RequestParams();
        params.put("app", "ok");
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.64.2/MUSEART/procesos/api.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    db.borrarTabla("obras");
                    Log.d("OBRAS", response.toString());
                    if (response.getBoolean("success")) {
                        JSONArray obras = response.getJSONArray("listaObras");
                        for (int i=0; i < obras.length(); i++) {
                            JSONObject o = obras.getJSONObject(i);
                            try {
                                db.insertarObras(o.getString("id"),
                                        o.getString("titulo"),
                                        o.getString("artista"),
                                        o.getInt("annoNac"),
                                        o.getString("lugarNac"),
                                        o.getInt("annoFal"),
                                        o.getString("lugarFal"),
                                        o.getInt("publicado_en"),
                                        o.getString("periodo"),
                                        o.getString("descripcion"),
                                        o.getString("dato_curioso"),
                                        o.getString("imagen"));

                            } catch (Exception e) {
                                Log.d("ERROR: ", e.getMessage());
                            }

                        }
                    }
                } catch (Exception e) {
                    Log.d("ERROR: ", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("ERROR: ", errorResponse.toString());
            }
        });


    }


}

class obraAdapter extends CursorAdapter {

    public obraAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.lista_obras, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView _urlimagen = view.findViewById(R.id.urlimagen);
        TextView _titulo = view.findViewById(R.id.titulo);
        TextView _artista = view.findViewById(R.id.artista);


        _titulo.setText(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
        _artista.setText(cursor.getString(cursor.getColumnIndexOrThrow("artista")));

        Picasso.with(context)
                .load("http://192.168.64.2/MUSEART/img/"+cursor.getString(cursor.getColumnIndexOrThrow("imagen")))
                .into(_urlimagen, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });
    }
}
