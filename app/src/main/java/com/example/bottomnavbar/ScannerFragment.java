package com.example.bottomnavbar;

import android.content.Intent;
import android.database.Cursor;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScannerFragment extends Fragment {

    private DbManager db;
    private Cursor c;
    private ImageView _imagen;
    private TextView _titulo, _artista, _annoNac, _lugarNac, _annoFal, _lugarFal, _publicado_en, _periodo, _descripcion, _dato_curioso;
    Button btnLeerCodigo;

    public ScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_scanner, container, false);

        //Definimos los componentes
        _imagen = vista.findViewById(R.id.urlimagen);
        _titulo = vista.findViewById(R.id.titulo);
        _artista = vista.findViewById(R.id.artista);
        _annoNac = vista.findViewById(R.id.annoNac);
        _lugarNac = vista.findViewById(R.id.lugarNac);
        _annoFal = vista.findViewById(R.id.annoFal);
        _lugarFal = vista.findViewById(R.id.lugarFal);
        _publicado_en = vista.findViewById(R.id.publicado_en);
        _periodo = vista.findViewById(R.id.periodo);
        _descripcion = vista.findViewById(R.id.descripcion);
        _dato_curioso = vista.findViewById(R.id.dato_curioso);



        escanear();

        return vista;

    }

    public void escanear() {

        IntentIntegrator intent = IntentIntegrator.forSupportFragment(ScannerFragment.this);
        intent.setCaptureActivity(CapturaAct.class);
        intent.setOrientationLocked(false);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt("ESCANEAR CODIGO");
        intent.setCameraId(0);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "Cancelaste el escaneo", Toast.LENGTH_SHORT).show();
            } else {
                String respuesta = result.getContents();
                db = new DbManager(getActivity().getApplicationContext());

                c = db.getCursor("obras", "_id =" + respuesta);
                if (c.moveToFirst()) {
                    do {
                        _titulo.setText(c.getString(c.getColumnIndexOrThrow("titulo")));
                        _artista.setText(c.getString(c.getColumnIndexOrThrow("artista")));
                        _annoNac.setText(c.getString(c.getColumnIndexOrThrow("annoNac")));
                        _lugarNac.setText(c.getString(c.getColumnIndexOrThrow("lugarNac")));
                        _annoFal.setText(c.getString(c.getColumnIndexOrThrow("annoFal")));
                        _lugarFal.setText(c.getString(c.getColumnIndexOrThrow("lugarFal")));
                        _publicado_en.setText(c.getString(c.getColumnIndexOrThrow("publicado_en")));
                        _periodo.setText(c.getString(c.getColumnIndexOrThrow("periodo")));
                        _descripcion.setText(c.getString(c.getColumnIndexOrThrow("descripcion")));
                        _dato_curioso.setText(c.getString(c.getColumnIndexOrThrow("dato_curioso")));

                        Picasso.with(getActivity().getApplicationContext()).load("http://192.168.64.2/MUSEART/img/" + c.getString(c.getColumnIndexOrThrow("imagen")))
                                .into(_imagen, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                    } while (c.moveToNext());
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }

        /*btnLeerCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });*/
            }
    }

}
}
