package br.equipe07.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import br.equipe07.R;

public class TarefasAdapter extends ArrayAdapter<Tarefa> {

    private final LayoutInflater inflater;

    public TarefasAdapter(@NonNull Context context, @NonNull List<Tarefa> itens) {
        super(context, 0, itens);
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_tarefa, parent, false);
        }

        TextView tvTitulo = view.findViewById(R.id.tvTitulo);
        TextView tvDescricao = view.findViewById(R.id.tvDescricao);

        Tarefa tarefa = getItem(position);
        if (tarefa != null) {
            tvTitulo.setText(confereNull(tarefa.getTitulo()));
            tvDescricao.setText(confereNull(tarefa.getDescricao()));
        }

        return view;
    }

    private String confereNull(String s) {
        return s == null ? "" : s;
    }
}

