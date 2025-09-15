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

    long tarefaId = -1L;

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

        if (getIntent() != null && getIntent().hasExtra("id")) {
            tarefaId = getIntent().getLongExtra("id", -1L);
            String titulo = getIntent().getStringExtra("titulo");
            String descricao = getIntent().getStringExtra("descricao");
            if (titulo != null) etTitulo.setText(titulo);
            if (descricao != null) edtDescricao.setText(descricao);
            btnSalvar.setText("Atualizar");
        } else {
            btnSalvar.setText("Salvar");
        }

        btnSalvar.setOnClickListener(v -> {
            String titulo = etTitulo.getText().toString();
            String descricao = edtDescricao.getText().toString();

            TarefaDAO dao = new TarefaDAO(this);

            if (tarefaId >= 0) {
                dao.atualizar(tarefaId, titulo, descricao, "");
            } else {
                dao.inserir(titulo, descricao, "");
            }

            setResult(RESULT_OK);
            finish();
        });
    }
}