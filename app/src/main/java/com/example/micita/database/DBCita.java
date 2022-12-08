package com.example.micita.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.micita.Cita;
import com.example.micita.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBCita {


    private DBManager manager;

    public DBCita (DBManager manager) {
        this.manager=manager;
    }

    public boolean existeCita(String fecha, String dni)
    {
        Cursor cursor = null;
        boolean toret = false;
        SQLiteDatabase db;

        db = this.manager.getReadableDatabase();

        String selectQuery = "SELECT * FROM cita where dni  = "+ "'" + dni + "'" ;

        cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do{
                if(fecha!=cursor.getString(2)){
                    toret=false;
                }else{
                    toret=true;
                }
            }
            while(cursor.moveToNext()&&toret==false);



        }
        cursor.close();


        return toret;


    }

    public boolean insertarCita(String dni, String fecha)
    {

        Cursor cursor = null;
        boolean toret = false;
        SQLiteDatabase db = this.manager.getWritableDatabase();
        ContentValues values = new ContentValues();


        Cita cita = new Cita();

        cita.setDni(dni);
        cita.setFecha(fecha);


        values.put(manager.CITA_COLUMN_DNI,cita.getDni());
        values.put( manager.CITA_COLUMN_FECHA, cita.getFecha() );


            Log.e("cita toString1",cita.toString());

            Log.e("cita toString",values.toString());

            try {
                db.beginTransaction();

                //Inserta DNI
                cursor = db.query(manager.CITA_TABLE_NAME,
                        null,
                        manager.CITA_COLUMN_DNI + "=?",
                        new String[]{dni},
                        null, null, null, null);

                db.insert(manager.CITA_TABLE_NAME, null, values);

                db.setTransactionSuccessful();

                toret = true;

            } catch (SQLException exc) {
                Log.e("DBUsuario.inserta.user", exc.getMessage());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

                db.endTransaction();
            }


        return toret;
        }



    public ArrayList <Cita> devolverCita(String dni) {
        ArrayList <Cita> toRet2 = new ArrayList<>();

        SQLiteDatabase db = this.manager.getReadableDatabase();

        String selectQuery = "SELECT * FROM cita where dni  = "+ "'" + dni + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        Cita cita;

        Log.e("cursor devolverCita", Integer.toString(cursor.getCount()));



        if (cursor.moveToFirst()) {

            do{
                    cita= new Cita(cursor.getString(1),cursor.getString(2));
                Log.e("cursor devolverCita", cursor.getString(1)+cursor.getString(2));
                toRet2.add(cita);



            }while(cursor.moveToNext());

        }


        cursor.close();
        Log.e("cursor devolverCita", toRet2.toString());


        return toRet2;
    }

    /** Elimina un elemento de la base de datos
     * @param fecha El identificador del elemento.
     * @return true si se pudo eliminar, false en otro caso.
     */
    public boolean eliminaItem(String fecha)
    {
        boolean toret = false;
        SQLiteDatabase db = this.manager.getWritableDatabase();

        try {
            db.beginTransaction();
            db.delete( this.manager.CITA_TABLE_NAME, this.manager.CITA_COLUMN_FECHA + "=?", new String[]{ fecha } );
            db.setTransactionSuccessful();
            toret = true;
        } catch(SQLException exc) {
            Log.e( "DBManager.elimina.cita", exc.getMessage() );
        } finally {
            db.endTransaction();
        }

        return toret;
    }

    public boolean existenCitas(String dni)
    {
        Cursor cursor = null;
        boolean toret = false;
        SQLiteDatabase db;

        db = this.manager.getReadableDatabase();

        String selectQuery = "SELECT * FROM cita where dni  = "+ "'" + dni + "'" ;

        cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

           toret =true;

        }else{
            toret=false;
        }
        cursor.close();


        return toret;


    }

}
