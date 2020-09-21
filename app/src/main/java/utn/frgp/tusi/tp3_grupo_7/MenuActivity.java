package utn.frgp.tusi.tp3_grupo_7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawwlayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private AlertDialog dialogAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        Integer idUser = preferences.getInt("id", -1);
        if(idUser < 0){
            Intent menu = new Intent(this, MainActivity.class);
            startActivity(menu);
        }else {
             setContentView(R.layout.activity_menuprincipal);
            drawwlayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);
            toolbar = findViewById(R.id.toolbar);

            setSupportActionBar(toolbar);
            getSupportActionBar().setSubtitle("Parqueos");
            navigationView.bringToFront();
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawwlayout, toolbar, R.string.navigation_drawler_open, R.string.navigation_drawler_close);
            drawwlayout.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(this);

            AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
            dialogAccount = builder.create();
            dialogAccount.setTitle("Mi cuenta");
            AdminSQLite admin = new AdminSQLite(this, "BaseDatosTp3", null, 1);
            SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
            Cursor userData = BasedeDatos.rawQuery("select nombre, email from usuarios where id =" + idUser, null);
            if (userData.moveToFirst()) {
                dialogAccount.setMessage("Nombre: " + userData.getString(0) + " \nEmail: " + userData.getString(1));
            } else {
                dialogAccount.setMessage("Ha ocurrido un error.");
            }
        }
    }

    @Override
    public void onBackPressed(){
        if(drawwlayout.isDrawerOpen(GravityCompat.START)){
            drawwlayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_parqueos:
                Intent intent = new Intent(MenuActivity.this , parqueo.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                dialogAccount.show();
                break;
            case R.id.nav_logout:
                logout();
        }
        return true;
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", -1);
        editor.commit();
        Intent inicio = new Intent(this, MainActivity.class);
        startActivity(inicio);
    }
}