package com.example.micita.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.micita.CentrosActivity;
import com.example.micita.Usuario;

public class DBUsuario {

    private DBManager manager;
    private Usuario usuario = new Usuario();

    public DBUsuario(DBManager manager) {
        this.manager = manager;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean insertaItem(String dni, String nombreUsuario, String apellido1, String apellido2, String contrasena, String centro) {
        Cursor cursor = null;
        boolean toret = false;
        SQLiteDatabase db = this.manager.getWritableDatabase();
        ContentValues values = new ContentValues();

        Usuario user = new Usuario();


        user.setDni(dni);
        user.setNombre(nombreUsuario);
        user.setApellido1(apellido1);
        user.setApellido2(apellido2);
        user.setContrasena(contrasena);
        user.setCentro(centro);

        Log.e("1",user.toString());

        values.put(manager.USUARIO_COLUMN_DNI, user.getDni());
        values.put(manager.USUARIO_COLUMN_NOMBREUSUARIO, user.getNombre());
        values.put(manager.USUARIO_COLUMN_APELLIDO1, user.getApellido1());
        values.put(manager.USUARIO_COLUMN_APELLIDO2, user.getApellido2());
        values.put(manager.USUARIO_COLUMN_CONTRASENA, user.getContrasena());
        values.put(manager.USUARIO_COLUMN_CENTRO, user.getCentro());

        Log.e("2",values.toString());

        try {
            db.beginTransaction();

            //Inserta DNI
            cursor = db.query(manager.USUARIO_TABLE_NAME,
                    null,
                    manager.USUARIO_COLUMN_DNI + "=?",
                    new String[]{dni},
                    null, null, null, null);

            db.insert(manager.USUARIO_TABLE_NAME, null, values);

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

    public boolean existeUsuario(String dni) {
        boolean toret = false;

        SQLiteDatabase db = this.manager.getReadableDatabase();

        String selectQuery = "SELECT * FROM usuario where dni  = "+ "'" + dni + "'" ;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            toret = true;
        }
        cursor.close();


        return toret;
    }

    public boolean actualizarPerfil(String dni,String name,String apellido1, String apellido2){
        boolean toret = false;

        Cursor cursor = null;
        SQLiteDatabase db = manager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(manager.USUARIO_COLUMN_APELLIDO1, apellido1);
        values.put(manager.USUARIO_COLUMN_APELLIDO2, apellido2);
        values.put(manager.USUARIO_COLUMN_NOMBREUSUARIO, name);


        try {
            db.beginTransaction();

            String selectQuery = "SELECT * FROM usuario where dni  = "+ "'" + dni + "'";
            cursor = db.rawQuery(selectQuery, null);

            if ( cursor.getCount() > 0 ) {
                db.update( manager.USUARIO_TABLE_NAME,
                        values, manager.USUARIO_COLUMN_DNI + "= ?", new String[]{ dni } );
                toret=true;
            } else {
                Log.e("GetCount actUsuario","No hay usuarios");
                toret=false;
            }
            db.setTransactionSuccessful();
        } catch(SQLException exc) {
            Log.e( "DBUsuario.act", exc.getMessage() );
        } finally {
            if ( cursor != null ) {
                cursor.close();
            }
            db.endTransaction();
        }
        return toret;

    }

    public Usuario devolverUsuario(String dni) {
        Usuario toRet = new Usuario();

        SQLiteDatabase db = this.manager.getReadableDatabase();

        String selectQuery = "SELECT * FROM usuario where dni  = "+ "'" + dni + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);


        Log.e("cursor despues query", Integer.toString(cursor.getCount()));



        if (cursor.moveToFirst()) {

            Log.e("Entro al if", "si");
            Log.e("cosas en el cursor", "si");
            toRet.setDni(cursor.getString(4));
            Log.e("Entro al DNI", cursor.getString(1));

            toRet.setNombre(cursor.getString(1));
            Log.e("Entro al NOMBRE", cursor.getString(2));

            toRet.setApellido1(cursor.getString(2));
            Log.e("Entro al APELLIDO1", cursor.getString(3));

            toRet.setApellido2(cursor.getString(3));
            Log.e("Entro al APELLIDO2", cursor.getString(4));

            toRet.setContrasena(cursor.getString(5));
            Log.e("Entro a CONTRASEÑA", cursor.getString(5));
            toRet.setCentro(cursor.getString(6));
            Log.e("User despue del cursor:", toRet.toString());

        }
        Log.e("User despues del if:", toRet.toString());
        Log.e("Salió del if", "si");


        cursor.close();


        return toRet;
    }


  /*  /**
     * Elimina un elemento de la base de datos
     *
     * @param dni El identificador del elemento.
     * @return true si se pudo eliminar, false en otro caso.
     */
    /*public boolean eliminaItem(String dni) {
        boolean toret = false;
        SQLiteDatabase db = this.manager.getWritableDatabase();

        try {
            db.beginTransaction();
            db.delete(this.manager.USUARIO_TABLE_NAME, this.manager.USUARIO_COLUMN_DNI + "=?", new String[]{dni});
            db.setTransactionSuccessful();
            toret = true;
        } catch (SQLException exc) {
            Log.e("DBManager.elimina.user", exc.getMessage());
        } finally {
            db.endTransaction();
        }

        return toret;
    } */

    public String devolverCentro(String dni){
        Log.e("5","ENTRO");
        Usuario user = devolverUsuario(dni);
        Log.e("5","SALIO");

        return user.getCentro();
    }

    public boolean actualizarCita(String dni,String cita){



        boolean toret = false;

        Cursor cursor = null;
        SQLiteDatabase db = manager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(manager.USUARIO_COLUMN_CITAS, cita);


        try {
            db.beginTransaction();

            String selectQuery = "SELECT * FROM usuario where dni  = "+ "'" + dni + "'";
            cursor = db.rawQuery(selectQuery, null);

            if ( cursor.getCount() > 0 ) {
                db.update( manager.USUARIO_TABLE_NAME,
                        values, manager.USUARIO_COLUMN_DNI + "= ?", new String[]{ dni } );
                toret=true;
            } else {
                Log.e("GetCount actUsuario","No hay usuarios");
                toret=false;
            }
            db.setTransactionSuccessful();
        } catch(SQLException exc) {
            Log.e( "DBUsuario.act", exc.getMessage() );
        } finally {
            if ( cursor != null ) {
                cursor.close();
            }
            db.endTransaction();
        }
        return toret;

    }
}
