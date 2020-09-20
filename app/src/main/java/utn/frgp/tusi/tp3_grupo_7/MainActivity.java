package utn.frgp.tusi.tp3_grupo_7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private Toast alertEmpty, alertErrorPass, alertErrorUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        Integer idUser = preferences.getInt("id", -1);
        if(idUser > 0){
            Intent menu = new Intent(this, MenuActivity.class);
            startActivity(menu);
        }else{
            setContentView(R.layout.activity_main);
            username = (EditText) findViewById(R.id.edt_nombre);
            password = (EditText) findViewById(R.id.edt_contrasenia);
            alertEmpty = Toast.makeText(getApplicationContext(),"Ambos campos deben estar completos.",Toast.LENGTH_SHORT);
            alertErrorUser = Toast.makeText(getApplicationContext(), "Nombre de usuario no encontrado.", Toast.LENGTH_SHORT);
            alertErrorPass = Toast.makeText(getApplicationContext(), "Nombre de usuario o contraseña incorrecta.", Toast.LENGTH_SHORT);
        }
    }

    public void login(View view){
        if(username.getText().length() > 0 && password.getText().length() > 0){
            AdminSQLite admin = new AdminSQLite(this, "BaseDatosTp3", null, 1);
            SQLiteDatabase BasedeDatos = admin.getWritableDatabase();

            String Username = username.getText().toString();
            String Password = password.getText().toString();
            Cursor fila = BasedeDatos.rawQuery("select nombre from usuarios where nombre ='"+ Username +"'" , null);
            if(fila.moveToFirst()){
                Cursor fila2 = BasedeDatos.rawQuery("select id from usuarios where contrasenia ='" + Password +"' and nombre = '"+Username+"'", null);
                if(fila2.moveToFirst()){
                    BasedeDatos.close();
                    SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("id", Integer.parseInt(fila2.getString(0)));
                    editor.commit();
                    Intent menu = new Intent(this, MenuActivity.class);
                    startActivity(menu);
                }else{
                    alertErrorPass.show();
                }
            }else{
                alertErrorUser.show();
            }
        }else{
            if(!alertEmpty.getView().isShown()){
                alertEmpty.show();
            }
        }
    }

    public void redirectRegister(View view){
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }
}