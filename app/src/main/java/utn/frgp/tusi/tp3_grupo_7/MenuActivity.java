package utn.frgp.tusi.tp3_grupo_7;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private Button miCuenta;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder builderP;
    private AlertDialog dialogAccount, dialogParqueo;
    private int idUser;
    private Toast alertEmpty, alertExito, alertError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprincipal);
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        Integer idUser = preferences.getInt("id", -1);
        if(idUser < 0){
            Intent menu = new Intent(this, MainActivity.class);
            startActivity(menu);
        }else {
            miCuenta = (Button) findViewById(R.id.btn_profile);
            builder = new AlertDialog.Builder(MenuActivity.this);
            builderP = new AlertDialog.Builder(MenuActivity.this);
            alertEmpty = Toast.makeText(getApplicationContext(), "Debe completar todos los campos.", Toast.LENGTH_SHORT);
            alertExito = Toast.makeText(getApplicationContext(), "Parqueo registrado exitosamente", Toast.LENGTH_SHORT);
            alertError = Toast.makeText(getApplicationContext(), "Ha ocurrido un error al intentar registrar el parqueo.", Toast.LENGTH_LONG);

            dialogAccount = builder.create();
            dialogAccount.setTitle("Mi cuenta");

            builderP.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //Cambiar parametros
                    if (cargarParqueo("a",1))
                    {
                        alertExito.show();
                    }
                    else
                    {
                        alertError.show();
                    }
                }
            });
            builderP.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });

            dialogParqueo = builderP.create();
            dialogParqueo.setTitle("Cargar parqueo");

            AdminSQLite admin = new AdminSQLite(this, "BaseDatosTp3", null, 1);
            SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
            Cursor userData = BasedeDatos.rawQuery("select nombre, email from usuarios where id ="+ idUser , null);
            if(userData.moveToFirst()){
                dialogAccount.setMessage("Nombre: "+userData.getString(0)+" \nEmail: " + userData.getString(1));
            }else{
                dialogAccount.setMessage("Ha ocurrido un error.");
            }
        }
    }


    public boolean cargarParqueo(String matriculaCargada, int tiempoCargado) {
        String matricula = matriculaCargada;
        int tiempo = tiempoCargado;


        if (!matricula.isEmpty() && tiempo > 0) {

            AdminSQLite admin = new AdminSQLite(this, "BaseDatosTp3", null, 1);
            SQLiteDatabase BaseDatos = admin.getWritableDatabase();
            try {
                ContentValues registro = new ContentValues();
                registro.put("patente", matriculaCargada);
                registro.put("tiempo", tiempoCargado);
                registro.put("id_usuario", idUser);
                BaseDatos.insert("parqueos", null, registro);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                BaseDatos.close();

            }
        }

        return true;
    }

    public void showAccount(View view){
        dialogAccount.show();
    }

    public void showParqueo(View view){
        dialogParqueo.show();
    }

    public void logout(View view ){
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", -1);
        editor.commit();
        Intent inicio = new Intent(this, MainActivity.class);
        startActivity(inicio);
    }
}