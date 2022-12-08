package com.example.micita;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.micita.database.DBManager;
import com.example.micita.database.DBUsuario;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//actividad de registro del usuario
public class RegisterActivity extends AppCompatActivity {

    EditText editDNI, editPassword, editName, editApellido1, editApellido2;
    Button Register;
    String NameHolder, DNIHolder, PasswordHolder, Apellido1Holder, Apellido2Holder, CentroHolder;
    Boolean EditTextEmptyHolder;
    DBManager manager;
    DBUsuario usuario;
    String F_Result = "Not_Found";
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        prefs = this.getPreferences(Context.MODE_PRIVATE );

        Register = (Button) findViewById(R.id.buttonRegister);

        editDNI = (EditText) findViewById(R.id.editDNI);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editName = (EditText) findViewById(R.id.editName);
        editApellido1 = (EditText) findViewById(R.id.editApellido1);
        editApellido2 = (EditText) findViewById(R.id.editApellido2);
        manager = ((ControladorPrincipal) this.getApplication()).getDBManager();
        usuario = ((ControladorPrincipal) this.getApplication()).getDBUsuario();


        // Añadimos un listener al boton register
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Comprobamos si EditText es vacio
                CheckEditTextStatus();

                // comprobamos si el    DNI, ya existe o no

                CheckingDNIAlreadyExistsOrNot();


                EmptyEditTextAfterDataInsert();


            }
        });

    }

    @Override
    protected void onPause() {
        Toast.makeText(this, "Va al onPuase", Toast.LENGTH_SHORT).show();

        super.onPause();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString( "nombre", editName.getText().toString() );
        editor.putString( "apellido1", editApellido1.getText().toString() );
        editor.putString( "apellido2", editApellido2.getText().toString() );
        editor.putString( "DNI", editDNI.getText().toString() );
        editor.putString( "contraseña", editPassword.getText().toString() );
        editor.apply();
    }

    @Override
    protected void onResume() {
        Toast.makeText(this, "Va al onResume", Toast.LENGTH_SHORT).show();

        super.onResume();
        editName.setText(prefs.getString( "Nombre", "" ));
        editApellido1.setText(prefs.getString( "Apellido1", "" ));
        editApellido2.setText(prefs.getString( "Apellido2", "" ));
        editDNI.setText(prefs.getString( "DNI", "" ));
        editPassword.setText(prefs.getString( "Contraseña", "" ));

    }
    // Insertamos los datos mediante sql
    public void InsertDataIntoSQLiteDatabase() {

        if (EditTextEmptyHolder == true) {

            usuario.insertaItem(DNIHolder, NameHolder, Apellido1Holder, Apellido2Holder, PasswordHolder,CentroHolder);

            String existe = null;
            if (((ControladorPrincipal) this.getApplication()).getDBUsuario().existeUsuario(DNIHolder)) {
                existe = "si";
            } else {
                existe = "no";
            }
            Toast.makeText(RegisterActivity.this, existe, Toast.LENGTH_LONG).show();
            // Printing toast message after done inserting.
            Toast.makeText(RegisterActivity.this, "User Registered Successfully", Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent2);


        }
        else {

            Toast.makeText(RegisterActivity.this, "Porfavor rellena los campos.", Toast.LENGTH_LONG).show();

        }

    }

    public void EmptyEditTextAfterDataInsert() {

        editName.getText().clear();

        editApellido1.getText().clear();
        editApellido2.getText().clear();

        if (!isValidDNI(DNIHolder)) {
            editDNI.setError(("El DNI introducido no es de formato de DNI"));

        } else {
            editDNI.getText().clear();
        }

        if (!isValidName(NameHolder, Apellido1Holder)) {
            editName.setError(("El nombre y el primer apellido introducidos deben tener por lo menos 3 caracteres"));
            editApellido1.setError(("El nombre y el primer apellido introducidos deben tener por lo menos 3 caracteres"));

        } else {
            editName.getText().clear();
            editApellido1.getText().clear();
        }

        if (!isValidPassword(PasswordHolder)) {
            editPassword.setError(("La contraseña introducida debe tener como mínimo letras mayúsculas y minúsculas, un número, 5 caracteres y no debe contener espacios"));

        } else {
            editPassword.getText().clear();
        }

    }

    public void CheckEditTextStatus() {
        Toast.makeText(this, "CheckEditTextStatus principio", Toast.LENGTH_SHORT).show();
        // obtenemos valores de los EditText  y los guardamos en strings
        NameHolder = editName.getText().toString();
        Apellido1Holder = editApellido1.getText().toString();
        Apellido2Holder = editApellido2.getText().toString();

        DNIHolder = editDNI.getText().toString();
        PasswordHolder = editPassword.getText().toString();
        int random = ThreadLocalRandom.current().nextInt(0,4);
        CentroHolder = Centros.getCentro(random);

        if (TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(Apellido1Holder) || TextUtils.isEmpty(Apellido2Holder) || TextUtils.isEmpty(DNIHolder) || TextUtils.isEmpty(PasswordHolder)) {

            EditTextEmptyHolder = false;

        } else {

            EditTextEmptyHolder = true;
        }
        Toast.makeText(this, "CheckEditTextStatus final", Toast.LENGTH_SHORT).show();
    }

    public boolean isValidDNI(final String dni) {

        Pattern pattern;
        Matcher matcher;

        final String DNI_PATTERN = "[0-9]{8}[A-Za-z]{1}";

        pattern = Pattern.compile(DNI_PATTERN);
        matcher = pattern.matcher(dni);

        return matcher.matches();

    }

    public boolean isValidName(final String name, final String apellido) {

        Pattern pattern;
        Matcher matcherNombre, matcherApellido;


        final String APELLIDO_PATTERN = "[a-zA-Z]{3,}";

        pattern = Pattern.compile(APELLIDO_PATTERN);

        matcherApellido = pattern.matcher(apellido);
        matcherNombre = pattern.matcher(name);

        return (matcherNombre.matches()&&matcherApellido.matches());

    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*[a-zA-Z])" +
                "(?=\\S+$)" +
                ".{5,}" +
                "$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public void CheckingDNIAlreadyExistsOrNot() {

        boolean seguir = false;

        if (isValidDNI(DNIHolder)&&isValidName(NameHolder,Apellido1Holder)) {
            seguir = true;
        }

        if (usuario.existeUsuario(DNIHolder) && seguir == true) {

            F_Result = "DNI Found";
        }

        if (isValidPassword(PasswordHolder) && seguir == true) {

            CheckFinalResult();

        } else {
            editPassword.setError(("La contraseña introucida no tiene como mínimo una letra,un número o un simbolo especial"));

        }


    }


    // comprobamos resultado
    public void CheckFinalResult() {

        if (F_Result.equalsIgnoreCase("DNI Found")) {


            Toast.makeText(RegisterActivity.this, "DNI  Existe", Toast.LENGTH_LONG).show();

        } else {

//si dni no exite
            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "Not_Found";
        Toast.makeText(this, "CheckFinalResult final", Toast.LENGTH_SHORT).show();

    }


}