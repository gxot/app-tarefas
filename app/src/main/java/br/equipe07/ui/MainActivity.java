package br.equipe07.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.equipe07.R;

public class MainActivity extends AppCompatActivity {

    ListView lvTarefas;
    Button btnNovaTarefa;

    ArrayList<Tarefa> lista = new ArrayList<>();
    TarefasAdapter adapter;

    TarefaDAO dao;

    private ActivityResultLauncher<Intent> novaTarefaLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvTarefas = findViewById(R.id.lvTarefas);
        btnNovaTarefa = findViewById(R.id.btnNovaTarefa);

        dao = new TarefaDAO(this);

        adapter = new TarefasAdapter(this, lista);
        lvTarefas.setAdapter(adapter);

        recarregarLista();

        novaTarefaLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        recarregarLista();
                        Toast.makeText(this, "Tarefa salva", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Incluir: abre o formulário
        btnNovaTarefa.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormActivity.class);
            novaTarefaLauncher.launch(intent);
        });

        lvTarefas.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < lista.size()) {
                Tarefa t = lista.get(position);
                String novoTitulo = t.getTitulo() + " (editada)";
                String novaDescricao = "Atualizada pelo clique";

                int linhas = dao.atualizar(t.getId(), novoTitulo, novaDescricao, "");
                if (linhas > 0) {
                    recarregarLista();
                    Toast.makeText(this, "Tarefa editada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Falha ao editar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lvTarefas.setOnItemLongClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < lista.size()) {
                Tarefa t = lista.get(position);
                int linhas = dao.deletar(t.getId());
                if (linhas > 0) {
                    recarregarLista();
                    Toast.makeText(this, "Tarefa excluída", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Falha ao excluir", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        });
    }

    private void recarregarLista() {
        List<Tarefa> banco = dao.listar();
        lista.clear();
        lista.addAll(banco);
        adapter.notifyDataSetChanged();
    }
}