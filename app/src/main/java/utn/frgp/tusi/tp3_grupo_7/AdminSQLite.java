package utn.frgp.tusi.tp3_grupo_7;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLite extends SQLiteOpenHelper {

    public AdminSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BDTP3) {
        BDTP3.execSQL("create table usuarios(id integer primary key autoincrement, nombre text, email text, contrasenia text)");
        BDTP3.execSQL("create table parqueos(id integer primary key autoincrement, patente text, tiempo integer, id_usuario integer, foreign key (id_usuario) references usuarios(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase BDTP3, int i, int i1) {

    }
}
