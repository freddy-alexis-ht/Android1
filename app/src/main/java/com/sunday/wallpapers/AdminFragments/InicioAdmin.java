package com.sunday.wallpapers.AdminFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sunday.wallpapers.AdminCategoriesActivities.MusicaAdmin;
import com.sunday.wallpapers.AdminCategoriesActivities.PeliculasAdmin;
import com.sunday.wallpapers.AdminCategoriesActivities.SeriesAdmin;
import com.sunday.wallpapers.AdminCategoriesActivities.VideojuegosAdmin;
import com.sunday.wallpapers.R;

public class InicioAdmin extends Fragment {

    /* Declaration */
    Button btnPeliculas, btnSeries, btnMusica, btnVideojuegos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio_admin, container, false);

        /* Inicialization */
        btnPeliculas = view.findViewById(R.id.btnPeliculas);
        btnSeries = view.findViewById(R.id.btnSeries);
        btnMusica = view.findViewById(R.id.btnMusica);
        btnVideojuegos = view.findViewById(R.id.btnVideojuegos);

        /* Assign event to buttons (lambda used) */
        btnPeliculas.setOnClickListener(view1 -> {
            startActivity( new Intent( getActivity(), PeliculasAdmin.class));
        });

        btnSeries.setOnClickListener(view1 -> {
            startActivity( new Intent( getActivity(), SeriesAdmin.class));
        });

        btnMusica.setOnClickListener(view1 -> {
            startActivity( new Intent( getActivity(), MusicaAdmin.class));
        });

        btnVideojuegos.setOnClickListener(view1 -> {
            startActivity( new Intent( getActivity(), VideojuegosAdmin.class));
        });

        return view;
    }
}