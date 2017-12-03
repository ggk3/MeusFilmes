package br.com.pinoti.meusfilmes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.pinoti.meusfilmes.model.Filme;


public class FilmeDAO extends SQLiteOpenHelper{

    private final String KEY_TABLE = "filmes";
    private final String KEY_ID = "id";
    private final String KEY_TITLE = "title";
    private final String KEY_OVERVIEW = "overview";
    private final String KEY_RELEASE_DATE = "release_date";
    private final String KEY_VOTE_AVERAGE = "vote_average";
    private final Context context;

    public FilmeDAO(Context context) {
        super(context, "meusfilmes", null, 1);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + KEY_TABLE + " ( " +
                KEY_ID  +" INTEGER PRIMARY KEY, " +
                KEY_TITLE + " TEXT NOT NULL, " +
                KEY_OVERVIEW + " TEXT, " +
                KEY_RELEASE_DATE + " DATE, " +
                KEY_VOTE_AVERAGE + " REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insereFilme(Filme filme) {

        if(existeNoBanco(filme)){
            return false;
        }else{

            SQLiteDatabase db = getWritableDatabase();

            ContentValues dados = getContentValues(filme);

            db.insert(KEY_TABLE, null, dados);

            db.close();

            return true;
        }

    }

    private ContentValues getContentValues(Filme filme) {
        ContentValues dados = new ContentValues();
        dados.put(KEY_ID, filme.getId());
        dados.put(KEY_TITLE, filme.getTitle());
        dados.put(KEY_OVERVIEW, filme.getOverview());
        dados.put(KEY_RELEASE_DATE, filme.getRelease_date());
        dados.put(KEY_VOTE_AVERAGE, filme.getVote_average());
        return dados;
    }

    public List<Filme> listaFilmes(boolean ordenaData) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "";
        if(ordenaData){
            sql = "SELECT * FROM " + KEY_TABLE + " ORDER BY "+ KEY_RELEASE_DATE;
        }else{
            sql = "SELECT * FROM " + KEY_TABLE + " ORDER BY "+ KEY_TITLE;
        }

        Cursor c = db.rawQuery(sql, null);
        List<Filme> alunos = populaFilmes(c);
        c.close();
        return alunos;
    }

    private List<Filme> populaFilmes(Cursor c) {
        List<Filme> filmes = new ArrayList<>();

        while (c.moveToNext()) {
            Filme filme = new Filme();

            filme.setId(c.getLong(c.getColumnIndex(KEY_ID)));
            filme.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            filme.setOverview(c.getString(c.getColumnIndex(KEY_OVERVIEW)));
            filme.setRelease_date(c.getString(c.getColumnIndex(KEY_RELEASE_DATE)));
            filme.setVote_average(c.getDouble(c.getColumnIndex(KEY_VOTE_AVERAGE)));
            filmes.add(filme);
            Log.i("Filme: ", filme.getTitle());
        }
        return filmes;
    }

    private boolean existeNoBanco(Filme filme) {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT id FROM "+KEY_TABLE+" WHERE id=? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{ String.valueOf(filme.getId())});
        int quantidade = cursor.getCount();
        cursor.close();
        return quantidade > 0;
    }
}
