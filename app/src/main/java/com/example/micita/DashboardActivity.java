package com.example.micita;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

//pantalla de Área de Usuario donde puede realizar las acciones principales
public class DashboardActivity extends AppCompatActivity {

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        View cardViewCentros=findViewById(R.id.centros);
        View cardViewEditProfile=findViewById(R.id.perfil);
        View cardViewEditInformacion=findViewById(R.id.informacion);
        View cardViewEditCitas=findViewById(R.id.citas);
        EditText share;
        Bundle bundle = getIntent().getExtras();
        String dni = bundle.getString("dni");

        //obtenemos usuario actual
        usuario = ((ControladorPrincipal)this.getApplication()).getDBUsuario().devolverUsuario(dni);

        //getActionBar().setTitle("Holaaaz");
        getSupportActionBar().setTitle("Hola, "+ usuario.getNombre() + " " + usuario.getApellido1());

//Creamos 4 cartas, representando cada una de ellas una acción diferente
        cardViewEditCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(DashboardActivity.this, CitasGestionActivity.class);
                intent.putExtra("dni", dni);
                startActivity(intent);

            }
        } );

        cardViewCentros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(DashboardActivity.this, CentrosActivity.class);

                intent.putExtra("dni", dni);
                startActivity(intent);

            }
        } );

        cardViewEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(DashboardActivity.this, EditProfileActivity.class);
                intent.putExtra("dni", dni);

                startActivity(intent);

            }
        } );
        cardViewEditInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(DashboardActivity.this, InformacionActivity.class);

                startActivity(intent);

            }
        } );
    }

    // creamos el menú principal
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

//añadimos las opciones de cerras sesión, botoón de compartir y botón de puntuar la aplicación
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean toret = false;
        //switch case para seleccionar la opcion del menú
        switch (menuItem.getItemId()) {
            case R.id.logout:
                if (pedirConfirmacion()) {
                    finish();
                    toret = true;
                }
                break;
            case R.id.shareButton:

                try {
                    //mensaje y pantalla a mostrar a la hora de compartir la aplicación
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MiCita");
                    String shareMessage= "\nTe recomeindo está aplicación\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
                break;
            case R.id.ratingBar:
                //icono del menú que lleva a una pantalla de puntuación de la aplicación
                Intent intent = new Intent(DashboardActivity.this, RatingActivity.class);
                startActivity(intent);
        }
        return toret;
    }

    // se pide confirmación para que el usuario cierre su sesión
    public boolean pedirConfirmacion() {
        //usamos AlertDialog.Builder para crear el diálogo con el usuario y pedirle así participación
        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        //añadimos titulo, mensajes y opciones
        builder.setTitle("Confirmación de logout");
        builder.setMessage("Estás seguro de querer desloguearte?");
        builder.setNegativeButton("No", null);
        boolean toret2 = false;
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dlg, int i) {
                boolean toret2 = true;
                System.exit(0);
            }
        });

        if (toret2) {
            builder.create().show();

            return true;
        } else {
            builder.create().show();

            return false;
        }

    }
}


