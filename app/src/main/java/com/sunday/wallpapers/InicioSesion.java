package com.sunday.wallpapers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioSesion extends AppCompatActivity {

    EditText Correo, Password;
    Button BtnAcceder;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        /* It's the orange bar at the top */
        /* This is only for our 'inicio-sesion' activity design */
        ActionBar actionBar = getSupportActionBar();    // androidx.appcompat.app.ActionBar
        assert actionBar != null;                       // assert that actionBar is not null
        actionBar.setTitle("Inicio sesión");            // assign a title
        actionBar.setDisplayHomeAsUpEnabled(true);      // enable 'retroceso' button
        actionBar.setDisplayShowHomeEnabled(true);

        Correo = findViewById(R.id.Correo);
        Password = findViewById(R.id.Password);
        BtnAcceder = findViewById(R.id.BtnAcceder);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(InicioSesion.this);
        progressDialog.setMessage("Ingresando, espere por favor.");
        progressDialog.setCancelable(false);

        /* CORREO & PASSWORD VALIDATION */
        BtnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = Correo.getText().toString();
                String password = Password.getText().toString();

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
                    logeoAdmin(correo, password);
                }
            }
        });

    }

    private void logeoAdmin(String correo, String password) {
        progressDialog.show();
        progressDialog.setCancelable(false);

        /* SIGN IN */
        firebaseAuth.signInWithEmailAndPassword(correo, password)
                /* ON COMPLETE */
                .addOnCompleteListener(InicioSesion.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        /* SIGN IN SUCCESSFUL */
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            startActivity( new Intent( InicioSesion.this, MainActivityAdmin.class));
                            assert firebaseUser != null;
                            Toast.makeText(InicioSesion.this, "Bienvenido(a) "+firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
                            finish(); // to destroy 'InicioSesion' activity
                        }
                        /* SIGN IN NOT SUCCESSFUL */
                        else {
                            progressDialog.dismiss();
                            usuarioInvalido();
                        }
                    }
                })
                /* ON FAILURE */
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        usuarioInvalido();
                    }
                });
    }

    private void usuarioInvalido() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InicioSesion.this);
        builder.setCancelable(false);
        builder.setTitle("Ha ocurrido un error!");
        builder.setMessage("Verifique que el correo y contraseña sean correctos")
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show(); // to show the AlertDialog

    }

    // When 'retroceso' button is pressed, it will redirect to the previous activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}