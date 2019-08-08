package com.android.msm.exemplo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE animais(_id integer PRIMARY KEY AUTOINCREMENT, ranking integer, especie VARCHAR(200), " +
                "raca VARCHAR(250),img VARCHAR(300))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        if (oldV == 1) {
            if (newV == 2) {
               /*modifique seu database*/
                if (!isFieldExist(db, "animais", "img")) {
                    db.execSQL("ALTER TABLE animais ADD COLUMN  img VARCHAR(300)");
                } else {
                    Log.d("database", "a Coluna já existe");
                }
            }
        }

    }


    //usado para verificar se uma coluna existe em uma tabela, pois a mesma pode ter sido colocada em versões antigas e removidas
    private boolean isFieldExist(SQLiteDatabase db, String tableName, String fieldName) {
        boolean isExist = false;

        Cursor res = null;

        try {

            res = db.rawQuery("Select * from " + tableName + " limit 1", null);

            int colIndex = res.getColumnIndex(fieldName);
            if (colIndex != -1) {
                isExist = true;
            }

        } catch (Exception e) {
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (Exception e1) {
            }
        }

        return isExist;
    }

}