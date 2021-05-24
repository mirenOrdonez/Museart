package com.example.bottomnavbar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private ListView listaNews;
    private ArrayList<News> exposiciones = new ArrayList<>();
    private Adaptador adaptador;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_news, container, false);

        listaNews =  vista.findViewById(R.id.listviewNews);

        llenarItems();

        return vista;
    }

    private void llenarItems(){
        exposiciones.add(new News(R.drawable.expo1, "Charlotte Johannesson", "Llévame a otro mundo",
                "7 abril – 16 agosto, 2021"));
        exposiciones.add(new News(R.drawable.expo2, "Vivian Suter", "",
                "25 junio, 2021 – 10 enero, 2022"));

        adaptador = new Adaptador(getActivity().getApplicationContext(), exposiciones);
        listaNews.setAdapter(adaptador);
    }
}
