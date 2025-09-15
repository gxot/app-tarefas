package br.equipe07.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "app_tarefas.db";
    private static final int VERSAO_BANCO = 1;

    private static final String TABELA_TAREFAS = "tarefas";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_TITULO = "titulo";
    private static final String COLUNA_DESCRICAO = "descricao";
    private static final String COLUNA_DATA = "data";

    public TarefaDAO(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA_TAREFAS + " (" +
                COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_TITULO + " TEXT, " +
                COLUNA_DESCRICAO + " TEXT, " +
                COLUNA_DATA + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA_TAREFAS;
        db.execSQL(sql);
        onCreate(db);
    }

    public long inserir(String titulo, String descricao, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_TITULO, titulo);
        valores.put(COLUNA_DESCRICAO, descricao);
        valores.put(COLUNA_DATA, data);
        return db.insert(TABELA_TAREFAS, null, valores);
    }

    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_TAREFAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUNA_ID));
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_TITULO));
                String descricao = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_DESCRICAO));
                String data = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_DATA));

                Tarefa tarefa = new Tarefa(id, titulo, descricao);
                tarefas.add(tarefa);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return tarefas;
    }

    public int atualizar(long id, String novoTitulo, String novaDescricao, String novaData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_TITULO, novoTitulo);
        valores.put(COLUNA_DESCRICAO, novaDescricao);
        valores.put(COLUNA_DATA, novaData);
        return db.update(TABELA_TAREFAS, valores, COLUNA_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int deletar(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABELA_TAREFAS, COLUNA_ID + " = ?", new String[]{String.valueOf(id)});
    }
}