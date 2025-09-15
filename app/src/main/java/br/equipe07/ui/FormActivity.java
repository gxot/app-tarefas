package br.equipe07.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.equipe07.R;

public class FormActivity extends AppCompatActivity {

    Button btnSalvar;
    EditText etTitulo;
    EditText edtDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSalvar = findViewById(R.id.btnSalvar);
        etTitulo = findViewById(R.id.etTitulo);
        edtDescricao = findViewById(R.id.edtDescricao);

        btnSalvar.setOnClickListener(v -> {
            String titulo = etTitulo.getText().toString();
            String descricao = edtDescricao.getText().toString();
            
            TarefaDAO dao = new TarefaDAO(this);
            dao.inserir(titulo, descricao, "");

            setResult(RESULT_OK);
            finish();
        });
    }
}