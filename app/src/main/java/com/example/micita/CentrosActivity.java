package com.example.micita;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class CentrosActivity extends AppCompatActivity {

    //Para la listview
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    SharedPreferences prefs;

//obtenemos los centros
    Centros centro4 = Centros.COMPLEJO_HOSPITALARIO_UNIVERSITARIO_DE_OURENSE;
    Centros centro1 = Centros.HOSPITAL_ALVARO_CUNQUEIRO;
    Centros centro2 = Centros.HOSPITAL_UNIVERSITARIO_DE_A_CORUÑA;
    Centros centro3 = Centros.HOSPITAL_LUCUS_AUGUSTI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(CentrosActivity.this, "IntentoCENTROS3", Toast.LENGTH_LONG).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centros);
        EditText theFilter = (EditText) findViewById(R.id.searchFilter);
        //guardamos las preferencias
        prefs = this.getPreferences(Context.MODE_PRIVATE );
        //guardamos el dni
        Bundle bundle = getIntent().getExtras();
        String dni = bundle.getString("dni");

        // creamos la listView medienta lvItems
        ListView lvItems = (ListView) this.findViewById(R.id.lvItems);
    //Barra superior con titulo personalizado
        getSupportActionBar().setTitle("Tus centros");

        // Preparamos adapter y arrayList
        this.items = new ArrayList<String>();

        this.itemsAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_selectable_list_item,
                this.items);
        lvItems.setAdapter(this.itemsAdapter);

    // añadimos a items los centros del usuario que inicia sesión para mostrarlo el la listView
        items.add(((ControladorPrincipal)this.getApplication()).getDBUsuario().devolverCentro(dni));


        // Preparamos el pulsar largo para borrar un elemento
        lvItems.setLongClickable( true );
        this.registerForContextMenu(lvItems);
        //Buscador
        theFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (CentrosActivity.this).itemsAdapter.getFilter().filter(charSequence);


            }

            @Override
            public void afterTextChanged(Editable editable) {
            }


        });
    }
    //datos que guardamos como preferences para que vuelvan a estar disponibles más adelante
    @Override
    protected void onPause() {
        Toast.makeText(this, "Va al onPuase", Toast.LENGTH_SHORT).show();

        super.onPause();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString( "centro1", centro1.toString() );
        editor.putString( "centro2", centro2.toString() );
        editor.putString( "centro3", centro3.toString() );
        editor.putString( "centro4", centro4.toString() );
        editor.apply();
    }

    @Override
    protected void onResume() {

        super.onResume();

    }
    //Menú contextual
    public void onCreateContextMenu(ContextMenu contxt, View v, ContextMenu.ContextMenuInfo cmi)
    {
        super.onCreateContextMenu( contxt, v, cmi );
        if ( v.getId() == R.id.lvItems)
        {
            this.getMenuInflater().inflate( R.menu.centros_menu, contxt );
           // contxt.setHeaderTitle( "Eliminar" );
        }
    }
    //Hacemos seleccionables los centros del menú contextual
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        boolean toret = super.onContextItemSelected(item);
        int pos = ( (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ).position;
        switch( item.getItemId() ) {
            case R.id.edEliminar:
                CentrosActivity.this.items.remove(pos);
                CentrosActivity.this.updateData();
                toret = true;
                break;
            case R.id.verUbi:
                Intent intent = new Intent(CentrosActivity.this, ActForFragmentNameActivity.class);
                startActivity(intent);
                toret = true;
                break;
        }
        return toret;
    }
    //Creamos el menú de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.addcentros_menu, menu);
        invalidateOptionsMenu();

        return true;
    }


    //Añadimos opciones al menú
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        boolean toret = false;
        switch (menuItem.getItemId()) {
            case R.id.Centro1:
                    CentrosActivity.this.itemsAdapter.add(centro1.getName());
                    CentrosActivity.this.updateStatus();

                    toret = true;

                break;
            case R.id.Centro2:
                    CentrosActivity.this.itemsAdapter.add(centro2.getName());
                    CentrosActivity.this.updateStatus();

                toret = true;

                break;
            case R.id.Centro3:
                    CentrosActivity.this.itemsAdapter.add(centro3.getName());
                    CentrosActivity.this.updateStatus();

                toret = true;

                break;
            case R.id.Centro4:
                    CentrosActivity.this.itemsAdapter.add(centro4.getName());
                    CentrosActivity.this.updateStatus();

                toret = true;

                break;
        }
        return toret;
    }
    //Actualizamos el estado
    private void updateStatus() {
        TextView txtNum = (TextView) this.findViewById( R.id.lblNum );
        txtNum.setText( Integer.toString( this.itemsAdapter.getCount() ) );
    }
    //Actualizamos e informamos del cambio en los datos
    private void updateData()
    {

        itemsAdapter.notifyDataSetChanged();

    }



}