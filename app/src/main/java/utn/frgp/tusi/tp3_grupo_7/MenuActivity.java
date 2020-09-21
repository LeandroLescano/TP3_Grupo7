package utn.frgp.tusi.tp3_grupo_7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity {

    private DrawerLayout drawlayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private AlertDialog dialogAccount;
    private AlertDialog.Builder builder;
    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle toggle;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        idUser = preferences.getInt("id", -1);
        if(idUser < 0){
            Intent menu = new Intent(this, MainActivity.class);
            startActivity(menu);
        }else {
            setContentView(R.layout.activity_menuprincipal);
            drawlayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);
            toolbar = findViewById(R.id.toolbar);

            setSupportActionBar(toolbar);
            getSupportActionBar().setSubtitle("Parqueos");
            navigationView.bringToFront();
            toggle = new ActionBarDrawerToggle(this, drawlayout, toolbar, R.string.navigation_drawler_open, R.string.navigation_drawler_close);
            drawlayout.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.nav_parqueos:
                                drawlayout.close();
                                break;
                            case R.id.nav_profile:
                                dialogAccount.show();
                                break;
                            case R.id.nav_logout:
                                logout();
                        }
                        return true;
                    }
            });

            /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_parqueos).setDrawerLayout(drawlayout).build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);*/

            //Creación del Dialog "Mi Cuenta"
            builder = new AlertDialog.Builder(MenuActivity.this);
            dialogAccount = builder.create();
            dialogAccount.setTitle("Mi cuenta");
            AdminSQLite admin = new AdminSQLite(this, "BaseDatosTp3", null, 1);
            SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
            Cursor userData = BasedeDatos.rawQuery("select nombre, email from usuarios where id =" + idUser, null);
            if (userData.moveToFirst()) {
                dialogAccount.setMessage("Nombre: " + userData.getString(0) + " \nEmail: " + userData.getString(1));

                //Nombre e Email en Navigation Header
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                TextView navUsername = (TextView) headerView.findViewById(R.id.header_user);
                TextView navEmail = (TextView) headerView.findViewById(R.id.header_email);
                navUsername.setText(userData.getString(0));
                navEmail.setText(userData.getString(0).toLowerCase().replace(" ", "") + "@parking.com");
            } else {
                dialogAccount.setMessage("Ha ocurrido un error.");
            }
        }
    }

    @Override
    public void onBackPressed(){
        if(drawlayout.isDrawerOpen(GravityCompat.START)){
            drawlayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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