package com.br.alexandretrieste.entrega_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListaTarefasActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADICIONAR_TAREFA = 1;

    private ListView listView;
    private ArrayList<Tarefa> tarefas;
    private TarefaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tarefas);

        listView = findViewById(R.id.listView);
        Button adicionarButton = findViewById(R.id.adicionarButton);
        Button sobreButton = findViewById(R.id.sobreButton);

        tarefas = new ArrayList<>();
        adapter = new TarefaAdapter(this, tarefas);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Tarefa tarefaClicada = tarefas.get(position);
            Toast.makeText(getApplicationContext(), getString(R.string.tarefa_selecionada) + tarefaClicada.getTitulo(), Toast.LENGTH_SHORT).show();
        });

        adicionarButton.setOnClickListener(v -> {
            Intent intent = new Intent(ListaTarefasActivity.this, CadastroTarefaActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADICIONAR_TAREFA);
        });

        sobreButton.setOnClickListener(v -> {
            Intent intent = new Intent(ListaTarefasActivity.this, SobreActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String titulo = data.getStringExtra("titulo");
            String descricao = data.getStringExtra("descricao");
            String prioridade = data.getStringExtra("prioridade");
            boolean concluida = data.getBooleanExtra("concluida", false);
            String categoria = data.getStringExtra("categoria"); // Pega a categoria

            Tarefa novaTarefa = new Tarefa(titulo, descricao, prioridade, concluida, categoria); // Construtor atualizado
            tarefas.add(novaTarefa);
            adapter.notifyDataSetChanged();
        }
    }
}
