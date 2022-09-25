package com.sunday.wallpapers.AdminFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sunday.wallpapers.MainActivityAdmin;
import com.sunday.wallpapers.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RegistrarAdmin extends Fragment {

    // Declaration
    TextView FechaRegistro;
    EditText Correo, Password, Nombres, Apellidos, Edad;
    Button BtnRegistrar;

    FirebaseAuth auth;
    // animation after clicking in btnRegistrar
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_registrar_admin, container, false);

        // Initialization
        FechaRegistro = view.findViewById(R.id.FechaRegistro);
        Correo = view.findViewById(R.id.Correo);
        Password = view.findViewById(R.id.Password);
        Nombres = view.findViewById(R.id.Nombres);
        Apellidos = view.findViewById(R.id.Apellidos);
        Edad = view.findViewById(R.id.Edad);

        BtnRegistrar = view.findViewById(R.id.BtnRegistrar);
        auth = FirebaseAuth.getInstance();

        // Date of our device
        Date date = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("d 'de' MMMM 'del' yyyy"); // 14 de enero del 2022
        String stringFecha = fecha.format(date); // Conversion from Date to String
        FechaRegistro.setText(stringFecha);

        // Click in 'Registrar' button
        // Lambda can be used here
        BtnRegistrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = Correo.getText().toString();
                String password = Password.getText().toString();
                String nombres = Nombres.getText().toString();
                String apellidos = Apellidos.getText().toString();
                String edad = Edad.getText().toString();

                /* VALIDATING EMPTY FIELDS */
                if(correo.equals("") || password.equals("") || nombres.equals("") || apellidos.equals("") || edad.equals("")) {
                    Toast.makeText(getActivity(), "Por favor llene todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    /* CORREO & PASSWORD VALIDATION */
                    if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                        // Invalid in absence of '@' and '.com'
                        Correo.setError("Correo inválido");
                        Correo.setFocusable(true);
                    }
                    else if (password.length() < 6) {
                        Password.setError("La contraseña debe tener 6 o más caracteres");
                        Correo.setFocusable(true);
                    }
                    else {
                        registroDeAdmins(correo, password);
                    }
                }
            }
        });

        // As we're in a fragment, we can't write the param: RegistrarAdmin.this
        // ..it's valid only in activities.
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Registrando, espere por favor");
        // to avoid the cancelling of the progressDialog by touching somewhere else
        progressDialog.setCancelable(false);

        return view;
    }

    /* METHOD TO REGISTER ADMINS */
    private void registroDeAdmins(String correo, String password) {
        progressDialog.show();
        auth.createUserWithEmailAndPassword(correo, password)
                // ON COMPLETE
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // if admin was created successfully
                        if (task.isSuccessful()) {
                            progressDialog.dismiss(); // closes the progress-dialog
                            FirebaseUser user = auth.getCurrentUser(); // app current-user

                            // convert to string the admin data
                            String UID = user.getUid();
                            String correo = Correo.getText().toString();
                            String password = Password.getText().toString();
                            String nombres = Nombres.getText().toString();
                            String apellidos = Apellidos.getText().toString();
                            int edad = Integer.parseInt(Edad.getText().toString());

                            // Used to send data to Firebase server
                            HashMap<Object, Object> admins = new HashMap<>();
                            admins.put("UID", UID);
                            admins.put("CORREO", correo);
                            admins.put("PASSWORD", password);
                            admins.put("NOMBRES", nombres);
                            admins.put("APELLIDOS", apellidos);
                            admins.put("EDAD", edad);
                            admins.put("IMAGEN", "");

                            // Initialize Firebase DB
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            // The name we want for our DB
                            DatabaseReference dbReference = db.getReference("DB-ADMINS");
                            // This is like saying: UID is the PK that refs to: UID, CORREO, ...
                            dbReference.child(UID).setValue(admins);
                            // After registration, redirect to 'MainActivityAdmin'
                            startActivity(new Intent(getActivity(), MainActivityAdmin.class));
                            // Toast-message
                            Toast.makeText(getActivity(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                            getActivity().finish();

                        } // END OF: if admin was created successfully

                        else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                        }
                    }
                })

                // ON FAILURE
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}