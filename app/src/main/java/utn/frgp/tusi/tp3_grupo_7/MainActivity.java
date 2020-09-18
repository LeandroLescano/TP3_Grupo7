package utn.frgp.tusi.tp3_grupo_7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Toast alertEmpty;
    private Toast alertErrorUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.edt_nombre);
        password = (EditText) findViewById(R.id.edt_contrasenia);
        alertEmpty = Toast.makeText(getApplicationContext(),"Ambos campos deben estar completos.",Toast.LENGTH_SHORT);
        alertErrorUser = Toast.makeText(getApplicationContext(), "Nombre de usuario no encontrado.", Toast.LENGTH_SHORT);
    }

    public void login(View view){

        if(username.getText().length() > 0 && password.getText().length() > 0){
            AdminSQLite admin = new AdminSQLite(this, "BaseDatosTp3", null, 1);
            SQLiteDatabase BasedeDatos = admin.getWritableDatabase();

            String Username = username.getText().toString();
            String Password = password.getText().toString();
            Cursor fila = BasedeDatos.rawQuery("select nombre , contrasenia from usuarios where nombre ='"+Username +"'" , null);
            if(fila.moveToFirst()){
                Cursor fila2 = BasedeDatos.rawQuery("select nombre, contrasenia from usuarios where contrasenia ='" + Password+"'", null);
                if(fila2.moveToFirst()){
                    BasedeDatos.close();
                    Intent menu = new Intent(this, MenuActivity.class);
                    startActivity(menu);
                }
            }
            //if para chequear usuario en BD
                //Si existe chequear la contraseña
                    //Ingresar en la app
                //Si no coincide, mostrar Toast
            //Si no existe, mostrar Toast (alertErrorUser)
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