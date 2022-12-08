package com.example.micita;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.micita.database.DBCita;

import java.util.ArrayList;
import java.util.List;

//clase para ver las citas creadas
public class VerCitasPendientesActivity extends AppCompatActivity {

    private static ArrayList<String> items;
    private static ArrayList<Cita> recorrer;

    private static ArrayAdapter<String> itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vercitaspendientes);

        Bundle bundle = getIntent().getExtras();

        String dni = bundle.getString("dni");
        EditText theFilter = (EditText) findViewById(R.id.searchFilter);
        getSupportActionBar().setTitle("Tus citas");
        recorrer=  ((ControladorPrincipal) this.getApplication()).getDBCita().devolverCita(dni);

        boolean hayCita = ((ControladorPrincipal) this.getApplication()).getDBCita().existenCitas(dni);
        int i=0;

        if (hayCita) {
            ListView lvItems = (ListView) this.findViewById(R.id.lvItems);

            // Pepare list and list adapter
            this.items = new ArrayList<String>();

            this.itemsAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_selectable_list_item,
                    this.items);
            lvItems.setAdapter(this.itemsAdapter);
            for(i=0; i< recorrer.size();i++) {

                items.add(((ControladorPrincipal)this.getApplication()).getDBCita().devolverCita(dni).get(i).getFecha());

            }
            lvItems.setLongClickable(true);
        }
        else {
            Toast.makeText(this, "No hay citas pendientes", Toast.LENGTH_SHORT).show();


        }
//buscador
        theFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (VerCitasPendientesActivity.this).itemsAdapter.getFilter().filter(charSequence);


            }

            @Override
            public void afterTextChanged(Editable editable) {
            }


        });

    }
        public void onCreateContextMenu(ContextMenu contxt, View v, ContextMenu.ContextMenuInfo cmi)
        {
            super.onCreateContextMenu( contxt, v, cmi );
            if ( v.getId() == R.id.lvItems)
            {
                this.getMenuInflater().inflate( R.menu.centros_menu, contxt );
            }
        }

        @Override
        public boolean onContextItemSelected(MenuItem item)
        {
            boolean toret = super.onContextItemSelected(item);
            int pos = ( (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ).position;
            switch( item.getItemId() ) {
                case R.id.edEliminar:
                   VerCitasPendientesActivity.this.items.remove(pos);
                    VerCitasPendientesActivity.this.updateData();
                    toret = true;
                    break;
                case R.id.verUbi:
                    Intent intent = new Intent(VerCitasPendientesActivity.this, ActForFragmentNameActivity.class);
                    startActivity(intent);
                    toret = true;
                    break;
            }
            return toret;
        }






        private void updateData()
        {

            itemsAdapter.notifyDataSetChanged();

        }




}





