package com.example.micita;

import android.app.Application;

import com.example.micita.database.DBCita;
import com.example.micita.database.DBManager;
import com.example.micita.database.DBUsuario;

public class ControladorPrincipal extends Application {
// nos permite acceder a a los DBManager, DBCita y DBUsuario
 private DBManager manager;
 private DBCita cita;
 private DBUsuario usuario;

 @Override
 public void onCreate()
 {
  super.onCreate();
  this.manager = new DBManager( this.getApplicationContext() );
  this.usuario = new DBUsuario( this.getDBManager() );
  this.cita = new DBCita( this.getDBManager() );
 }

 public DBManager getDBManager() {
  return this.manager;
 }

 public DBCita getDBCita() {
  return cita;
 }

 public DBUsuario getDBUsuario() {
  return usuario;
 }
}