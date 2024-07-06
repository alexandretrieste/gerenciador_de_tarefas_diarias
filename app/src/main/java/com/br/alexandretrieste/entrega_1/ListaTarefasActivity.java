package com.br.alexandretrieste.entrega_1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ListaTarefasActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADICIONAR_TAREFA = 1;
    private static final int REQUEST_CODE_EDITAR_TAREFA = 2;
    private static final int REQUEST_CODE_CONFIGURACOES = 3;

    private ListView listView;
    private ArrayList<Tarefa> tarefas;
    private TarefaAdapter adapter;
    private AppDatabase db;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        aplicarIdioma(sharedPreferences.getString("language", "en")); // Apply saved language
        setContentView(R.layout.activity_lista_tarefas);

        db = AppDatabase.getInstance(this);

        listView = findViewById(R.id.listView);
        tarefas = new ArrayList<>();
        adapter = new TarefaAdapter(this, tarefas);
        listView.setAdapter(adapter);

        carregarTarefas();

        aplicarConfiguracoes();

        registerForContextMenu(listView);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Tarefa tarefaClicada = tarefas.get(position);
            Toast.makeText(getApplicationContext(), getString(R.string.tarefa_selecionada) + " " + tarefaClicada.getTitulo(), Toast.LENGTH_SHORT).show();
        });
    }

    private void carregarTarefas() {
        new AsyncTask<Void, Void, List<Tarefa>>() {
            @Override
            protected List<Tarefa> doInBackground(Void... voids) {
                return db.tarefaDao().getAllTarefas();
            }

            @Override
            protected void onPostExecute(List<Tarefa> result) {
                tarefas.clear();
                tarefas.addAll(result);
                aplicarConfiguracoes();
                adapter.notifyDataSetChanged();
            }
        }.execute();
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
        } else if (id == R.id.menu_configuracoes) {
            Intent intent = new Intent(ListaTarefasActivity.this, ConfiguracoesActivity.class);
            startActivityForResult(intent, REQUEST_CODE_CONFIGURACOES);
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
            confirmarExclusao(tarefas.get(position));
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void confirmarExclusao(Tarefa tarefa) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirmar_exclusao_titulo)
                .setMessage(R.string.confirmar_exclusao_mensagem)
                .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTask(tarefa);
                    }
                })
                .setNegativeButton(R.string.nao, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteTask(Tarefa tarefa) {
        new AsyncTask<Tarefa, Void, Void>() {
            @Override
            protected Void doInBackground(Tarefa... tarefas) {
                db.tarefaDao().delete(tarefas[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                carregarTarefas();
            }
        }.execute(tarefa);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Tarefa tarefa = (Tarefa) data.getSerializableExtra("tarefa");
            if (requestCode == REQUEST_CODE_ADICIONAR_TAREFA) {
                addTask(tarefa);
            } else if (requestCode == REQUEST_CODE_EDITAR_TAREFA) {
                updateTask(tarefa);
            }
        } else if (requestCode == REQUEST_CODE_CONFIGURACOES) {
            aplicarConfiguracoes();
        }
    }

    private void addTask(Tarefa tarefa) {
        new AsyncTask<Tarefa, Void, Void>() {
            @Override
            protected Void doInBackground(Tarefa... tarefas) {
                db.tarefaDao().insert(tarefas[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                carregarTarefas();
            }
        }.execute(tarefa);
    }

    private void updateTask(Tarefa tarefa) {
        new AsyncTask<Tarefa, Void, Void>() {
            @Override
            protected Void doInBackground(Tarefa... tarefas) {
                db.tarefaDao().update(tarefas[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                carregarTarefas();
            }
        }.execute(tarefa);
    }

    private void aplicarConfiguracoes() {
        boolean isModoNoturno = sharedPreferences.getBoolean("modo_noturno", false);
        String ordenarPor = sharedPreferences.getString("ordenar_por", "alfabetica");

        if (isModoNoturno) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        ordenarTarefas(ordenarPor);
    }

    private void aplicarIdioma(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private void ordenarTarefas(String ordenarPor) {
        if (tarefas == null) return;

        if (ordenarPor.equals("alfabetica")) {
            Collections.sort(tarefas, new Comparator<Tarefa>() {
                @Override
                public int compare(Tarefa t1, Tarefa t2) {
                    return t1.getTitulo().compareTo(t2.getTitulo());
                }
            });
        } else if (ordenarPor.equals("data")) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Collections.sort(tarefas, new Comparator<Tarefa>() {
                @Override
                public int compare(Tarefa t1, Tarefa t2) {
                    try {
                        return sdf.parse(t1.getData()).compareTo(sdf.parse(t2.getData()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });
        }

        adapter.notifyDataSetChanged();
    }
}
