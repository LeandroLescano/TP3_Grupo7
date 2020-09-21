package utn.frgp.tusi.tp3_grupo_7.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dominio.Parqueo;
import utn.frgp.tusi.tp3_grupo_7.AdminSQLite;
import utn.frgp.tusi.tp3_grupo_7.MenuActivity;
import utn.frgp.tusi.tp3_grupo_7.R;
import utn.frgp.tusi.tp3_grupo_7.adapter.ParqueoAdapter;

public class ParqueoFragment extends Fragment {

    private ParqueoViewModel parqueoViewModel;
    private AlertDialog dialogParqueo;
    private AlertDialog.Builder builderP;
    private Toast alertEmpty, alertExito, alertError;
    private Integer idUser;
    private ParqueoAdapter adapter;
    private GridView grid;
    private ArrayList<Parqueo> lista;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        parqueoViewModel = new ViewModelProvider(this).get(ParqueoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_parqueo, container, false);

        builderP = new AlertDialog.Builder(getActivity());
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_layout, null);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("usuario", Context.MODE_PRIVATE);
        idUser = preferences.getInt("id", -1);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.btn_AgregarParqueo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showParqueo();
            }
        });

        adapter = new ParqueoAdapter(root.getContext(), loadParqueos());
        grid = (GridView) root.findViewById(R.id.gv_parqueos);
        grid.setAdapter(adapter);

        alertEmpty = Toast.makeText(getActivity(), "Debe completar todos los campos.", Toast.LENGTH_SHORT);
        alertExito = Toast.makeText(getActivity(), "Parqueo registrado exitosamente", Toast.LENGTH_SHORT);
        alertError = Toast.makeText(getActivity(), "Ha ocurrido un error al intentar registrar el parqueo.", Toast.LENGTH_LONG);
        builderP.setView(customLayout);
        builderP.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText mat = customLayout.findViewById(R.id.matricula);
                EditText tie = customLayout.findViewById(R.id.tiempo);

                if (!mat.getText().toString().isEmpty() && !tie.getText().toString().isEmpty()) {
                    if (cargarParqueo(mat.getText().toString(), Integer.parseInt(tie.getText().toString()))) {
                        alertExito.show();
                    } else {
                        alertError.show();
                    }
                }
            }
        });
        builderP.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        dialogParqueo = builderP.create();
        dialogParqueo.setTitle("Cargar parqueo");

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){

            }
        });

        return root;
    }

    public boolean cargarParqueo(String matriculaCargada, int tiempoCargado) {
        String matricula = matriculaCargada;
        int tiempo = tiempoCargado;
        if (!matricula.isEmpty() && tiempo > 0) {
            AdminSQLite admin = new AdminSQLite(getActivity(), "BaseDatosTp3", null, 1);
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

    public ArrayList<Parqueo> loadParqueos(){
        AdminSQLite admin = new AdminSQLite(getContext(), "BaseDatosTp3", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();
        lista = new ArrayList<>();

        Cursor parqueos = BasedeDatos.rawQuery("select id, patente, tiempo from parqueos where id_usuario ="+ idUser , null);
        if(parqueos.moveToFirst()){
            while(parqueos.moveToNext()){
                lista.add(new Parqueo(Integer.parseInt(parqueos.getString(0)), parqueos.getString(1), Integer.parseInt(parqueos.getString(2))));
            }
        }
        return lista;
    }
}
