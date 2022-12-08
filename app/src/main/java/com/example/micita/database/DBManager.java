package com.example.micita.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public  class DBManager extends SQLiteOpenHelper {

  public static String MICITA_DATABASE_NAME = "micita_db";
    private static int MICITA_DATABASE_VERSION =10;
    //CITA
    public static final String CITA_TABLE_NAME = "cita";
    public static final String CITA_COLUMN_ID = "_id";
    public static final String CITA_COLUMN_DNI = "dni";

    public static final String CITA_COLUMN_FECHA = "fecha";
    //USUARIO
    public static final String USUARIO_TABLE_NAME = "usuario";
    public static final String USUARIO_COLUMN_ID = "_id";
    public static final String USUARIO_COLUMN_DNI = "dni";
    public static final String USUARIO_COLUMN_NOMBREUSUARIO = "nombreUsuario";
    public static final String USUARIO_COLUMN_APELLIDO1 = "apellido1";
    public static final String USUARIO_COLUMN_APELLIDO2 = "apellido2";
    public static final String USUARIO_COLUMN_CONTRASENA = "contrasena";
    public static final String USUARIO_COLUMN_CENTRO = "nombre_centro";
    public static final String USUARIO_COLUMN_CITAS = "citas";


    //CENTRO
    public static final String CENTRO_TABLE_NAME = "centro";
    public static final String CENTRO_COLUMN_ID = "_id";
    public static final String CENTRO_COLUMN_NOMBRECENTRO = "nombreCentro";
    public static final String CENTRO_COLUMN_LOCALIZACION = "localizacion";

    public DBManager(@Nullable Context context) {
        super(context, MICITA_DATABASE_NAME, null, MICITA_DATABASE_VERSION);
        Log.e("Version nueva",MICITA_DATABASE_VERSION+"");
    }

    //DATEPICKERDIALOG
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS " + CITA_TABLE_NAME + "(" +

                    CITA_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    CITA_COLUMN_DNI + " TEXT NOT NULL UNIQUE," +
                    CITA_COLUMN_FECHA + " TEXT NOT NULL" +
                            //"PRIMARY KEY" + (CITA_COLUMN_ID +   CITA_COLUMN_DNI + CITA_COLUMN_FECHA) +

                    ")");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + USUARIO_TABLE_NAME + "(" +
                    USUARIO_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    USUARIO_COLUMN_NOMBREUSUARIO + " TEXT NOT NULL," +
                    USUARIO_COLUMN_APELLIDO1 + " INTEGER NOT NULL," +
                    USUARIO_COLUMN_APELLIDO2 + " TEXT NOT NULL," +
                    USUARIO_COLUMN_DNI + " CHAR NOT NULL UNIQUE," +
                    USUARIO_COLUMN_CONTRASENA + " TEXT NOT NULL" +

                    ")");
           /* db.execSQL("CREATE TABLE IF NOT EXISTS " + CENTRO_TABLE_NAME + "(" +
                    CENTRO_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    CENTRO_COLUMN_NOMBRECENTRO + "TEXT NOT NULL," +
                    CENTRO_COLUMN_LOCALIZACION + " TEXT NOT NULL" +
                    ")");*/
            db.setTransactionSuccessful();

        } catch (SQLException exception) {
            Log.e(DBManager.class.getName(), "onCreate", exception);
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i( "DBManager", "DB: " + MICITA_DATABASE_NAME + ": v" + oldVersion + " -> v" + newVersion );


        try {
            db.beginTransaction();
            db.execSQL( "DROP TABLE IF EXISTS " + CITA_TABLE_NAME );
            db.execSQL( "DROP TABLE IF EXISTS " + USUARIO_TABLE_NAME );
            db.execSQL( "DROP TABLE IF EXISTS " + CENTRO_TABLE_NAME );
            db.setTransactionSuccessful();

            db.execSQL("CREATE TABLE IF NOT EXISTS " + CITA_TABLE_NAME + "(" +

                    CITA_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    CITA_COLUMN_DNI + " TEXT NOT NULL," +
                    CITA_COLUMN_FECHA + " TEXT NOT NULL" +
                    ")");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + USUARIO_TABLE_NAME + "(" +
                    USUARIO_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    USUARIO_COLUMN_NOMBREUSUARIO + " TEXT NOT NULL," +
                    USUARIO_COLUMN_APELLIDO1 + " INTEGER NOT NULL," +
                    USUARIO_COLUMN_APELLIDO2 + " TEXT NOT NULL," +
                    USUARIO_COLUMN_DNI + " CHAR NOT NULL UNIQUE," +
                    USUARIO_COLUMN_CONTRASENA + " TEXT NOT NULL," +
                    USUARIO_COLUMN_CENTRO + " TEXT NOT NULL," +
                    USUARIO_COLUMN_CITAS + " INTEGER REFERENCES "+CITA_TABLE_NAME+" ); ");

        } catch(SQLException exc) {
            Log.e( "DBManager.onUpgrade", exc.getMessage() );
        } finally {
            db.endTransaction();
        }
        this.onCreate( db );
    }



}


/*

     @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(  "DBManager",
                "DB: " + MICITA_DATABASE_NAME + ": v" + oldVersion + " -> v" + newVersion );

        try {
            db.beginTransaction();
            //REVISAR DROPS
            db.execSQL( "DROP TABLE IF EXISTS " + CITA_TABLE_NAME );
            db.execSQL( "DROP TABLE IF EXISTS " + USUARIO_TABLE_NAME )
            db.execSQL( "DROP TABLE IF EXISTS " + CENTRO_TABLE_NAME );
            ;

            db.setTransactionSuccessful();
        }  catch(SQLException exc) {
            Log.e( "DBManager.onUpgrade", exc.getMessage() );
        }
        finally {
            db.endTransaction();
        }

        this.onCreate( db );
    }
}
     */

