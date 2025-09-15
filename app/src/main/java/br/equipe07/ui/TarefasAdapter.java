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

    public TarefasAdapter(@NonNull Context context, @NonNull List<Tarefa> itens) {
        super(context, 0, itens);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.item_tarefa, parent, false);
        }
        Tarefa t = getItem(position);

        TextView tvTitulo = v.findViewById(R.id.tvTitulo);
        TextView tvDescricao = v.findViewById(R.id.tvDescricao);

        if (t != null) {
            tvTitulo.setText(t.getTitulo());
            tvDescricao.setText(t.getDescricao());
        }
        return v;
    }
}

