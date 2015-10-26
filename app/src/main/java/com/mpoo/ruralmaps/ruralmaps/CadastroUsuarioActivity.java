package com.mpoo.ruralmaps.ruralmaps;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import dao.UsuarioDAO;
import gui.Mensagem;
import negocio.Usuario;



public class CadastroUsuarioActivity extends Activity {

    private EditText edtNome, edtLogin, edtSenha;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;
    private int idUsuario;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);


        usuarioDAO = new UsuarioDAO(this);

        edtNome = (EditText) findViewById(R.id.cadastro_edt_Nome);
        edtLogin = (EditText) findViewById(R.id.cadastro_edt_Login);
        edtSenha = (EditText) findViewById(R.id.cadastro_edt_Senha);
    }

    @Override
    protected void onDestroy(){
        usuarioDAO.fechar();
        super.onDestroy();
    }

    private void cadastrar(){
        boolean validacao = true;
        resources = getResources();

        String nome = edtNome.getText().toString().trim();
        String login = edtLogin.getText().toString().trim();
        String senha = edtSenha.getText().toString();

        if (nome == null || nome.equals("")){
            validacao = false;
            edtNome.setError(getString(R.string.campo_obrigatorio));
        }
        if (login == null || login.equals("")){
            validacao = false;
            edtLogin.setError(getString(R.string.campo_obrigatorio));
        }
        if (senha == null || senha.equals("")){
            validacao = false;
            edtSenha.setError(getString(R.string.campo_obrigatorio));
        }
        if (validacao){
            usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setLogin(login);
            usuario.setSenha(senha);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro_usuario, menu);

        if (idUsuario > 0){
            menu.findItem(R.id.action_menu_salvar).setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_menu_salvar:
                this.cadastrar();
                break;
            case R.id.action_menu_sair:
                    finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
