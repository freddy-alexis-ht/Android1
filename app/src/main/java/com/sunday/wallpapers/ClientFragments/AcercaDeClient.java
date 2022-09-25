package com.sunday.wallpapers.ClientFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sunday.wallpapers.InicioSesion;
import com.sunday.wallpapers.R;

public class AcercaDeClient extends Fragment {

    Button btnAcceder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_acerca_de_client, container, false);

        btnAcceder = view.findViewById(R.id.BtnAcceder);

        btnAcceder.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( getActivity(), InicioSesion.class));
            }
        });

        return view;
    }

}