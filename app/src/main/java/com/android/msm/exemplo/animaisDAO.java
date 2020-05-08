package com.android.msm.exemplo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class animaisDAO {
    //MÉTODOS PAARA TRABALHAR COM A TABELA lotacoes
    private Context ctx;
    private String table_name = "animais";
    private String[] colunas = new String[]{
            "_id", "ranking","especie", "raca","img"};

    public animaisDAO(Context ctx) {
        this.ctx = ctx;
    }
    public boolean insert(animaisVO geo) {
        SQLiteDatabase db     = new DBHelper(ctx).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ranking", geo.getRankig());
        values.put("especie", geo.getEspecie());
        values.put("raca", geo.getRaca());
        values.put("img", geo.getImg());
        if(db.insert(table_name, null, values) > 0){
            db.close();
            return true;
        }else{
            return false;
        } }


    public Cursor buscarTudo() {
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();

        Cursor c = db.query(table_name, colunas,  null, null,  null, null, "_id ASC", null);

        if (c == null) {
            db.close();
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            db.close();
            return null;
        }
        return c;
    }
	public void deleteTudo() {
		SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();

	      db.delete(table_name, null,  null );

	}

    public Cursor buscarStrings(String txt) {
        SQLiteDatabase db = new DBHelper(ctx).getWritableDatabase();
        String[] busca = new String[]{"%"+txt+"%","%"+txt+"%","%"+txt+"%"};

        Cursor c = db.query(table_name, colunas, "ranking LIKE ? or especie LIKE ? or raca LIKE ? ", busca, null, null, "_id ASC", null);

        if (c == null) {
            c.close();
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        return c;
    }


}
