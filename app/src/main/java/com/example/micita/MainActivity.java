package com.example.micita;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.micita.database.DBManager;
// pantalla para el login
public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs;
    Button buttonLogin, buttonRegister;
    EditText editDNI, editPassword;
    SQLiteDatabase sqLiteDatabaseObj;
    Boolean textEmpty;
    Cursor cursor;
    static String   DNIHolder;
    String PasswordHolder;
    DBManager manager;
    String dni = "";
    String passwordNotFound= "NOT_FOUND";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = this.getPreferences(Context.MODE_PRIVATE );

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editDNI = (EditText) findViewById(R.id.editDNI);
        editPassword = (EditText) findViewById(R.id.editPassword);
        manager = new DBManager(this);

        //añadimos un listener al botón de login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Comprobamos si editText es vacio o no
                CheckEditTextStatus();

                // Llamamos al metodo para loguearse.
                login();


            }
        });

        // Liatener para el botón de registro
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Abrimos nueva activad de registro usano el botón de registro
                Intent intent2 = new Intent(MainActivity.this, RegisterActivity.class);

                startActivity(intent2);
                Toast.makeText(MainActivity.this, "register 3", Toast.LENGTH_LONG).show();

            }
        });

    }
    protected void onPause() {
        Toast.makeText(this, "Va al onPuase", Toast.LENGTH_SHORT).show();

        super.onPause();

        SharedPreferences.Editor editor = prefs.edit();

        editor.putString( "DNI", editDNI.getText().toString() );
        editor.putString( "contraseña", editPassword.getText().toString() );
        editor.apply();
    }

    @Override
    protected void onResume() {
        Toast.makeText(this, "Va al onResume", Toast.LENGTH_SHORT).show();

        super.onResume();

        editDNI.setText(prefs.getString( "DNI", "" ));
        editPassword.setText(prefs.getString( "Contraseña", "" ));

    }
    public void CheckEditTextStatus() {

        //Obtenemos los valores e los editText
        DNIHolder = editDNI.getText().toString();
        PasswordHolder = editPassword.getText().toString();


        textEmpty = !TextUtils.isEmpty(DNIHolder) && !TextUtils.isEmpty(PasswordHolder);
    }

    public void login() {

        if (textEmpty) {
        //activamos la escritura en la base de datos
            sqLiteDatabaseObj = manager.getWritableDatabase();
        // creamos un cursos bajo las siguientes condiciones
            cursor = sqLiteDatabaseObj.query(DBManager.USUARIO_TABLE_NAME, null, " " + DBManager.USUARIO_COLUMN_DNI + "=?", new String[]{DNIHolder}, null, null, null);
            // desplazamos el cursor
            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {

                    cursor.moveToFirst();

                    passwordNotFound = cursor.getString(cursor.getColumnIndex(DBManager.USUARIO_COLUMN_CONTRASENA));


                }
            }

            // Cerramos cursor y comprobamos el resultado final
            cursor.close();
            CheckFinalResult();

        } else {

            Toast.makeText(MainActivity.this, "Please Enter UserName or Password.", Toast.LENGTH_LONG).show();
        }
    }

    public void CheckFinalResult() {

        if (passwordNotFound.equalsIgnoreCase(PasswordHolder)) {

            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();

            //Vamos a la actividad del dashboard/área de usuario
            Intent intent = new Intent(this, DashboardActivity.class);

            String existe=null;
            if( ((ControladorPrincipal)this.getApplication()).getDBUsuario().existeUsuario(DNIHolder)){
                existe= "si";
            }else{
                existe="no";
            }

            // Sending Email to Dashboard Activity using intent.
            intent.putExtra("dni", DNIHolder);

            startActivity(intent);


        } else {

            Toast.makeText(MainActivity.this, "El DNI o contraseña son invalidos, intentalo otra vez porfavor", Toast.LENGTH_LONG).show();

        }
        passwordNotFound = "NOT_FOUND";

    }

}
