package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "tasks";
    private static final int VERSAO = 1;

    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tabela de usuarios
        db.execSQL("create table usuario(_id integer primary key autoincrement, "
                + "nome text not null, login text not null, senha text not null)");

        //tabala de tarefas
        db.execSQL("create table tarefa(_id integer primary key autoincrement,"
                + "tarefa text not null, dt_criacao datetime default current_timestamp, dt_completado datetime)");

        //cadastrar usuario Admin
        db.execSQL("insert into usuario(nome, login, senha) values('Admin','admin', '123456')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static class Usuarios {
        public static final String TABELA = "usuario";
        public static final String _ID = "_id";
        public static final String NOME = "nome";
        public static final String LOGIN = "login";
        public static final String SENHA = "senha";

        public static final String[] COLUNAS = new String[]{
                _ID, NOME, LOGIN, SENHA
        };
    }

    public static class Pessoa {
        public static final String TABELA = "pessoa";
        public static final String _ID = "_id";
        public static final String TAREFA = "tarefa";
        public static final String DT_CRIACAO = "dt_criacao";
        public static final String DT_COMPLETADO = "dt_completado";

        public static final String[] COLUNAS = new String[]{
                _ID, TAREFA, DT_CRIACAO, DT_COMPLETADO
        };
    }
}