package com.br.alexandretrieste.entrega_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TarefaAdapter extends ArrayAdapter<Tarefa> {

    public TarefaAdapter(Context context, ArrayList<Tarefa> tarefas) {
        super(context, 0, tarefas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tarefa tarefa = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tarefa, parent, false);
        }

        TextView tituloTextView = convertView.findViewById(R.id.tituloTextView);
        TextView descricaoTextView = convertView.findViewById(R.id.descricaoTextView);
        TextView prioridadeTextView = convertView.findViewById(R.id.prioridadeTextView);
        TextView concluidaTextView = convertView.findViewById(R.id.concluidaTextView);
        TextView dataTextView = convertView.findViewById(R.id.dataTextView);

        tituloTextView.setText(tarefa.getTitulo());
        descricaoTextView.setText(tarefa.getDescricao());
        prioridadeTextView.setText(getPriorityString(tarefa.getPrioridade()));
        concluidaTextView.setText(tarefa.isConcluida() ? getContext().getString(R.string.concluida) : getContext().getString(R.string.nao_concluida));
        dataTextView.setText(tarefa.getData());

        return convertView;
    }

    private String getPriorityString(String priority) {
        Context context = getContext();
        switch (priority) {
            case "High":
                return context.getString(R.string.Alta);
            case "Medium":
                return context.getString(R.string.Media);
            case "Low":
                return context.getString(R.string.Baixa);
            default:
                return priority;
        }
    }
}
