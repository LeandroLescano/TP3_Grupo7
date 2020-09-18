package utn.frgp.tusi.tp3_grupo_7;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText nombre;
    private EditText email;
    private EditText contrasenia;
    private EditText contraseniaRepeat;
    private Toast alertEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nombre = (EditText) findViewById(R.id.edt_nombre);
        email = (EditText) findViewById(R.id.edt_email);
        contrasenia = (EditText) findViewById(R.id.edt_contrasenia);
        contraseniaRepeat = (EditText) findViewById(R.id.edt_repetirContrasenia);
        alertEmpty = Toast.makeText(getApplicationContext(), "Debe completar todos los campos.", Toast.LENGTH_SHORT);
    }

    public void register(View view){
        String nombreUser = nombre.getText().toString();
        String emailUser = email.getText().toString();
        String contraseniaUser = contrasenia.getText().toString();
        String contraseniaRepeatUser = contraseniaRepeat.getText().toString();
        if(!nombreUser.isEmpty() && !emailUser.isEmpty() && !contraseniaUser.isEmpty() && !contraseniaRepeatUser.isEmpty()){
            if(contraseniaUser.equals(contraseniaRepeatUser)){
                AdminSQLite admin = new AdminSQLite(this, "BaseDatosTp3", null, 1);
                SQLiteDatabase BaseDatos = admin.getWritableDatabase();
                try {
                    ContentValues registro = new ContentValues();
                    registro.put("nombre", nombreUser);
                    registro.put("email", emailUser);
                    registro.put("contrasenia", contraseniaUser);
                    BaseDatos.insert("usuarios", null, registro);

                    Toast exito = Toast.makeText(getApplicationContext(), "Registro exitoso.", Toast.LENGTH_LONG);
                    exito.show();
                }catch(Exception e){
                  e.printStackTrace();
                  Toast error = Toast.makeText(getApplicationContext(), "Ha ocurrido un error al intentar realizar el registro.", Toast.LENGTH_LONG);
                  error.show();
                }finally {
                    BaseDatos.close();
                    Intent MainActivity = new Intent(this, MainActivity.class);
                    startActivity(MainActivity);
                }

            }



        }else{
            alertEmpty.show();
        }
    }

}
