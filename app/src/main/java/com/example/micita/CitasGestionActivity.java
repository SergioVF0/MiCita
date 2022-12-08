package com.example.micita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class CitasGestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citasgestion);
       View cardViewCitasPendientes=findViewById(R.id.citas);
        View cardViewNuevaCita=findViewById(R.id.informacion);

        Bundle bundle = getIntent().getExtras();
        String dni = bundle.getString("dni");

        getSupportActionBar().setTitle("Gestiona tus citas");

    //Le añadimos un listener a  la carta de Citas Pendientes
        cardViewCitasPendientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CitasGestionActivity.this, VerCitasPendientesActivity.class);
                intent.putExtra("dni", dni);
                startActivity(intent);
                // Se realiza un cambio de actividad
            }
        } );

        //Le añadimos un listener a  la carta de Citas nuevas
        cardViewNuevaCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(CitasGestionActivity.this, CrearNuevaCitaActivity.class);
                intent.putExtra("dni", dni);
                startActivity(intent);

            }
        } );
    }





}


