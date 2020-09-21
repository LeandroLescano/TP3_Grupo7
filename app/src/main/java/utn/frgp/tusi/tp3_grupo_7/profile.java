package utn.frgp.tusi.tp3_grupo_7;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class profile extends AppCompatActivity {

    private AlertDialog dialogAccount, dialogParqueo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void showAccount(View view){
        dialogAccount.show();
    }

   /* SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
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
}*/
}