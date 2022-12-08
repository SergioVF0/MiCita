package com.example.micita;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.micita.database.DBUsuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//creamos una nueva cita
public class CrearNuevaCitaActivity extends AppCompatActivity implements
        View.OnClickListener {
    Button btnDatePicker, btnTimePicker,confirmar;
    EditText txtDate, txtTime;
    String date;
    String time;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevacita);
        // identificamos y asignamos los elementos del layout con los declarados arriba en el código
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        confirmar=(Button)findViewById(R.id.buttonConfirmar);
        Bundle bundle = getIntent().getExtras();
        String dni = bundle.getString("dni");
        getSupportActionBar().setTitle("Elige fecha y hora");

        // le añadimos listener al boton para la seleccion de día fecha y mes, y otro a hora y minutos
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

    //listener para confirmar la creación de la cita
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cita = date +" "+time;

                ((ControladorPrincipal)getApplication()).getDBCita().insertarCita(dni,cita);
                ((ControladorPrincipal)getApplication()).getDBUsuario().actualizarCita(dni,cita);
                Toast.makeText(CrearNuevaCitaActivity.this, "Cita creada", Toast.LENGTH_LONG).show();
                finish();


            }
        } );

    }


    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            //datePickerDialog para seleccionar día mes y año
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                            Date dateOfGames = cal.getTime();
                            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
                            date = df.format(dateOfGames);
                            //cambiamos el formatos a date
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show(); // lo mostramos
        }
        if (v == btnTimePicker) {

            // se obtiene el tiempo actual
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // lanzamos Picker Dialog para hora y minutos
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                            time = txtTime.getText().toString();
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show(); // lo mostramos
        }
    }
}







