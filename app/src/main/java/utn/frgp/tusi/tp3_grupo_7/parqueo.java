package utn.frgp.tusi.tp3_grupo_7;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class parqueo extends AppCompatActivity {

    private Button miCuenta;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder builderP;
    private AlertDialog dialogAccount, dialogParqueo;
    private int idUser;
    private Toast alertEmpty, alertExito, alertError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parqueo);

        showParqueo();
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

    public void showParqueo(){
        dialogParqueo.show();
    }
}