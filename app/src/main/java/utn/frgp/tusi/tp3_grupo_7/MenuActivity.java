package utn.frgp.tusi.tp3_grupo_7;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button miCuenta;
    private AlertDialog.Builder builder;
    private AlertDialog dialogAccount;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprincipal);
        idUser = Integer.parseInt(getIntent().getStringExtra("id_user"));
        miCuenta = (Button) findViewById(R.id.btn_profile);
        builder = new AlertDialog.Builder(MenuActivity.this);
        dialogAccount = builder.create();
        dialogAccount.setTitle("Mi cuenta");

        AdminSQLite admin = new AdminSQLite(this, "BaseDatosTp3", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        Cursor userData = BasedeDatos.rawQuery("select nombre, email from usuarios where id ="+ idUser , null);
        if(userData.moveToFirst()){
            dialogAccount.setMessage("Nombre: "+userData.getString(0)+" \nEmail: " + userData.getString(1));
        }else{
            dialogAccount.setMessage("Ha ocurrido un error.");
        }
    }

    public void showAccount(View view){
        dialogAccount.show();
    }
}