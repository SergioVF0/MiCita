package com.example.micita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.micita.database.DBUsuario;
import androidx.appcompat.widget.Toolbar;
//editamos y guardamos el perfil del usuario que ha iniciado la sesión
public class EditProfileActivity extends AppCompatActivity {

    EditText   editName, editApellido1, editApellido2 ;
    Button btVolver;
    DBUsuario usuario;
//usamos SharedPreferences para guardas los cambios
    SharedPreferences prefs;

    final int MIN_PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //se crea la instancia de la actividad
        super.onCreate(savedInstanceState);
        // se accede a la vista de la actividad, llamada en este caso activity_edit_profile
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setTitle("Tu perfil");
        prefs = this.getPreferences(Context.MODE_PRIVATE );

        //obtenemos usuario
        usuario =((ControladorPrincipal)this.getApplication()).getDBUsuario();


        Toast.makeText(this,"entró init",Toast.LENGTH_SHORT).show();
        editApellido1 = findViewById(R.id.editApellido1);
        editApellido2 = findViewById(R.id.editApellido2);
        editName = findViewById(R.id.editName);
        Bundle bundle = getIntent().getExtras();
        String dni = bundle.getString("dni");
        if (usuario.existeUsuario(dni)) {
            Usuario user = ((ControladorPrincipal) this.getApplication()).getDBUsuario().devolverUsuario(dni);
            editName.setText(user.getNombre());
            editApellido1.setText(user.getApellido1());
            editApellido2.setText(user.getApellido2());
        } else {
            Toast.makeText(this, "No se ha encontrado el usuario", Toast.LENGTH_SHORT).show();
        }

     }


    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Va al onPuase", Toast.LENGTH_SHORT).show();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString( "nombre", editName.getText().toString() );
        editor.putString( "apellido1", editApellido1.getText().toString() );
        editor.putString( "apellido2", editApellido2.getText().toString() );
        editor.apply();
    }
    //volvemos a mostrar los datos de la última vez que se guardaron
    @Override
    protected void onResume() {
        super.onResume();

        if(editName.getText().equals("")){
        editName.setText(prefs.getString( "nombre", "" ));
        editApellido1.setText(prefs.getString( "apellido1", "" ));
        editApellido2.setText(prefs.getString( "apellido2", "" ));}

    }

    // comprbamos si los inputs en el  form son validos o no
    boolean validateInput() {
        if (editApellido1.getText().toString().equals("")) {
            editApellido1.setError("Introduce el primer apellido");
            return false;
        }
        if (editApellido2.getText().toString().equals("")) {
            editApellido2.setError("Introduce el segundo apellido");
            return false;
        }
        if (editName.getText().toString().equals("")) {
            editName.setError("Introduce el nombre");
            return false;
        }
        return true;
    }


    public void performEditProfile (View v) {
        if (validateInput()) {

            // Los input son correctos, actualizamos y guardamos

            String apellido1= editApellido1.getText().toString();
            String apellido2 = editApellido2.getText().toString();
            String name = editName.getText().toString();
            Bundle bundle = getIntent().getExtras();
            String dni = bundle.getString("dni");
            if(usuario.existeUsuario(dni)) {
                usuario.actualizarPerfil(dni, name, apellido1, apellido2);
            }else {

                Toast.makeText(this, "No se ha podido actualizar el perfil", Toast.LENGTH_SHORT).show();
            }

            finish();
//finalizamos la activiad
        }
    }

}