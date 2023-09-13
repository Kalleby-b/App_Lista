package com.example.lista;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editText;
    private ListView lstLista;
    private ArrayAdapter<String> adapter;
    private List<String> lista = new ArrayList<>();
    private SQLiteDatabase bancoDados;
    public Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstLista = findViewById(R.id.lstLista);
        botao = findViewById(R.id.btnAdd);

        criarBancoDeDados();
        listarDados();


    }

    public void listarDados(){
        try {
            bancoDados = openOrCreateDatabase("lista", MODE_PRIVATE,null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome FROM coisa", null);

            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1, lista);

            lstLista.setAdapter(adapter);
            meuCursor.moveToFirst();
            while(meuCursor != null){
                lista.add(meuCursor.getString(1));
                meuCursor.moveToNext();

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void criarBancoDeDados(){
        try {
                bancoDados = openOrCreateDatabase("lista", MODE_PRIVATE,null);
                bancoDados.execSQL("CREATE TABLE IF NOT EXISTS coisa ("+
                        "id INTEGER PRIMARY KEY AUTOINCREMENT"+
                        ", nome VARCHAR)");
                bancoDados.close();
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    public void adicionar(View view) {
        bancoDados = openOrCreateDatabase("lista", MODE_PRIVATE, null);
        String sql = "INSERT INTO coisa (nomes) VALUES (?)";
        SQLiteStatement stmt = bancoDados.compileStatement(sql);

        String texto = editText.getText().toString();

        if (!TextUtils.isEmpty(texto)) {
            lista.add(texto);
            adapter.notifyDataSetChanged();
            editText.getText().clear();
        }


    }

}