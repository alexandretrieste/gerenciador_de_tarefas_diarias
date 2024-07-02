package com.br.alexandretrieste.entrega_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListaTarefasActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADICIONAR_TAREFA = 1;
    private static final int REQUEST_CODE_EDITAR_TAREFA = 2;

    private ListView listView;
    private ArrayList<Tarefa> tarefas;
    private TarefaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tarefas);

        listView = findViewById(R.id.listView);
        tarefas = new ArrayList<>();
        adapter = new TarefaAdapter(this, tarefas);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Tarefa tarefaClicada = tarefas.get(position);
            Toast.makeText(getApplicationContext(), getString(R.string.tarefa_selecionada) + tarefaClicada.getTitulo(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_tarefas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_adicionar) {
            Intent intent = new Intent(ListaTarefasActivity.this, CadastroTarefaActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADICIONAR_TAREFA);
            return true;
        } else if (id == R.id.menu_sobre) {
            Intent intent = new Intent(ListaTarefasActivity.this, SobreActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        int id = item.getItemId();
        if (id == R.id.menu_editar) {
            Intent intent = new Intent(ListaTarefasActivity.this, CadastroTarefaActivity.class);
            intent.putExtra("tarefa", tarefas.get(position));
            intent.putExtra("position", position);
            startActivityForResult(intent, REQUEST_CODE_EDITAR_TAREFA);
            return true;
        } else if (id == R.id.menu_excluir) {
            tarefas.remove(position);
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Tarefa tarefa = (Tarefa) data.getSerializableExtra("tarefa");
            if (requestCode == REQUEST_CODE_ADICIONAR_TAREFA) {
                tarefas.add(tarefa);
                adapter.notifyDataSetChanged();
            } else if (requestCode == REQUEST_CODE_EDITAR_TAREFA) {
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    tarefas.set(position, tarefa);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
