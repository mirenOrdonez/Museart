package com.example.bottomnavbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class DetalleNews extends AppCompatActivity {
    private TextView tvTitulo, tvDescripcion, tvFechas, tvContenido;
    private ImageView imgFoto;
    private News item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallenews);
        item = (News) getIntent().getSerializableExtra("item");
        if (item != null){
            tvTitulo = findViewById(R.id.tvTitulo);
            tvDescripcion = findViewById(R.id.tvDescripcion);
            tvContenido = findViewById(R.id.tvContenido);
            imgFoto = findViewById(R.id.imgFoto);
            tvFechas = findViewById(R.id.tvFechas);

            tvTitulo.setText(item.getTitulo());
            tvDescripcion.setText(item.getDescripcion());
            imgFoto.setImageResource(item.getImgFoto());
            tvFechas.setText(item.getFechas());
            tvContenido.setText(item.getContenido());
        }
    }
}
