package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import negocio.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public TarefaDAO(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDatabase(){
        if (database == null){
            database = databaseHelper.getWritableDatabase();
        }
        return  database;
    }

    private Tarefa criarTarefa(Cursor cursor){
        Tarefa negocio = new Tarefa(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Tarefa._ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Tarefa.TAREFA)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Tarefa.DT_CRIACAO)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Tarefa.DT_COMPLETADO))
        );
        return negocio;
    }

    public List<Tarefa> listarTarefa(){
        Cursor cursor = getDatabase().query(DatabaseHelper.Tarefa.TABELA,
                DatabaseHelper.Tarefa.COLUNAS, null, null, null, null, null);

        List<Tarefa> tarefa = new ArrayList<Tarefa>();
        while (cursor.moveToNext()){
            Tarefa negocio = criarTarefa(cursor);
            tarefa.add(negocio);
        }
        cursor.close();
        return tarefa;
    }

    public long salvarTarefa(Tarefa negocio){
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.Tarefa.TAREFA, negocio.getTarefa());

        if (negocio.get_id() != null){
            return getDatabase().update(DatabaseHelper.Tarefa.TABELA, valores, "_id = ?",
                    new String[]{negocio.get_id().toString()});
        }
        return getDatabase().insert(DatabaseHelper.Tarefa.TABELA, null, valores);
    }

    public boolean removerTarefa(int id){
        return getDatabase().delete(DatabaseHelper.Tarefa.TABELA,"_id = ?",
                new String[]{Integer.toString(id)}) > 0;
    }

    public Tarefa buscarTarefaPorId(int id){
        Cursor cursor = getDatabase().query(DatabaseHelper.Tarefa.TABELA,
                DatabaseHelper.Tarefa.COLUNAS,"_id = ?", new String[]{Integer.toString(id)},null, null, null);

        if (cursor.moveToNext()){
            Tarefa negocio = criarTarefa(cursor);
            cursor.close();
            return negocio;
        }
        return null;
    }

    public void fechar(){
        databaseHelper.close();
        database = null;
    }
}
