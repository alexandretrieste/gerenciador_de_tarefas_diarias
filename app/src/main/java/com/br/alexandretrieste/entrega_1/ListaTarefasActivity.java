package com.br.alexandretrieste.entrega_1;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;

public class ListaTarefasActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Tarefa> tarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tarefas);

        // Inicializar a ListView
        listView = findViewById(R.id.listView);

        // Carregar os dados do arrays.xml
        String[] titulosArray = getResources().getStringArray(R.array.titulos);
        String[] descricoesArray = getResources().getStringArray(R.array.descricoes);
        String[] prioridadesArray = getResources().getStringArray(R.array.prioridades);
        String[] concluidasStringArray = getResources().getStringArray(R.array.concluidas);
        boolean[] concluidasArray = new boolean[concluidasStringArray.length];
        for (int i = 0; i < concluidasStringArray.length; i++) {
            concluidasArray[i] = Boolean.parseBoolean(concluidasStringArray[i]);
        }

        // Criar a lista de tarefas com base nos arrays carregados
        tarefas = new ArrayList<>();
        for (int i = 0; i < titulosArray.length; i++) {
            Tarefa tarefa = new Tarefa(titulosArray[i], descricoesArray[i], prioridadesArray[i], concluidasArray[i]);
            tarefas.add(tarefa);
        }

        // Configurar o adapter personalizado para a ListView
        TarefaAdapter adapter = new TarefaAdapter(this, tarefas);
        listView.setAdapter(adapter);

        // Configurar o clique nos itens da lista
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Tarefa tarefaClicada = tarefas.get(position);
            Toast.makeText(getApplicationContext(), getString(R.string.tarefa_selecionada) + tarefaClicada.getTitulo(), Toast.LENGTH_SHORT).show();
        });
    }
}
