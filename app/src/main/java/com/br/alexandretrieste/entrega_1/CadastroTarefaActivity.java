package com.br.alexandretrieste.entrega_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
<<<<<<< HEAD
import android.view.MenuInflater;
import android.view.MenuItem;
=======
import android.view.MenuItem;
import android.widget.ArrayAdapter;
>>>>>>> 3652e9a45ecbf82e6eb14ecb7cc1cdce791d806a
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
<<<<<<< HEAD

=======
import androidx.annotation.NonNull;
>>>>>>> 3652e9a45ecbf82e6eb14ecb7cc1cdce791d806a
import androidx.appcompat.app.AppCompatActivity;

public class CadastroTarefaActivity extends AppCompatActivity {
    private EditText tituloEditText;
    private EditText descricaoEditText;
    private RadioGroup prioridadeRadioGroup;
    private CheckBox concluidaCheckBox;
<<<<<<< HEAD
    private Tarefa tarefa;
=======
    private Spinner categoriaSpinner;
    private boolean isEditMode = false;
    private int position = -1;
>>>>>>> 3652e9a45ecbf82e6eb14ecb7cc1cdce791d806a

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tarefa);

        tituloEditText = findViewById(R.id.tituloEditText);
        descricaoEditText = findViewById(R.id.descricaoEditText);
        prioridadeRadioGroup = findViewById(R.id.prioridadeRadioGroup);
        concluidaCheckBox = findViewById(R.id.concluidaCheckBox);
<<<<<<< HEAD
=======
        categoriaSpinner = findViewById(R.id.categoriaSpinner);
>>>>>>> 3652e9a45ecbf82e6eb14ecb7cc1cdce791d806a

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra("tarefa")) {
            tarefa = (Tarefa) intent.getSerializableExtra("tarefa");
            preencherCampos(tarefa);
        } else {
            tarefa = new Tarefa();
        }
    }

<<<<<<< HEAD
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro_tarefa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_salvar:
                salvarTarefa();
                return true;
            case R.id.menu_limpar:
                limparCampos();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void preencherCampos(Tarefa tarefa) {
        tituloEditText.setText(tarefa.getTitulo());
        descricaoEditText.setText(tarefa.getDescricao());
        // Defina a prioridade e o status concluído de acordo com a tarefa
    }

    private void salvarTarefa() {
        tarefa.setTitulo(tituloEditText.getText().toString());
        tarefa.setDescricao(descricaoEditText.getText().toString());
        // Obtenha a prioridade e o status concluído dos campos

        Intent resultIntent = new Intent();
        resultIntent.putExtra("tarefa", tarefa);
        setResult(RESULT_OK, resultIntent);
        finish();
=======
        Intent intent = getIntent();
        if (intent.hasExtra("tarefa")) {
            Tarefa tarefa = (Tarefa) intent.getSerializableExtra("tarefa");
            tituloEditText.setText(tarefa.getTitulo());
            descricaoEditText.setText(tarefa.getDescricao());
            switch (tarefa.getPrioridade()) {
                case "Alta":
                    radioButtonAlta.setChecked(true);
                    break;
                case "Média":
                    radioButtonMedia.setChecked(true);
                    break;
                case "Baixa":
                    radioButtonBaixa.setChecked(true);
                    break;
            }
            concluidaCheckBox.setChecked(tarefa.isConcluida());
            int categoriaPosition = categoriaAdapter.getPosition(tarefa.getCategoria());
            categoriaSpinner.setSelection(categoriaPosition);

            isEditMode = true;
            position = intent.getIntExtra("position", -1);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_salvar) {
            salvarTarefa();
            return true;
        } else if (id == R.id.menu_limpar) {
            limparCampos();
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvarTarefa() {
        String titulo = tituloEditText.getText().toString();
        String descricao = descricaoEditText.getText().toString();
        String prioridade = "";
        if (radioButtonAlta.isChecked()) {
            prioridade = "Alta";
        } else if (radioButtonMedia.isChecked()) {
            prioridade = "Média";
        } else if (radioButtonBaixa.isChecked()) {
            prioridade = "Baixa";
        }
        boolean concluida = concluidaCheckBox.isChecked();
        String categoria = categoriaSpinner.getSelectedItem().toString();

        if (titulo.isEmpty() || prioridade.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
            if (titulo.isEmpty()) {
                tituloEditText.requestFocus();
            } else if (prioridade.isEmpty()) {
                radioButtonAlta.requestFocus();
            }
        } else {
            Tarefa tarefa = new Tarefa(titulo, descricao, prioridade, concluida, categoria);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("tarefa", tarefa);
            if (isEditMode) {
                resultIntent.putExtra("position", position);
            }
            setResult(RESULT_OK, resultIntent);
            finish();
        }
>>>>>>> 3652e9a45ecbf82e6eb14ecb7cc1cdce791d806a
    }

    private void limparCampos() {
        tituloEditText.setText("");
        descricaoEditText.setText("");
        prioridadeRadioGroup.clearCheck();
        concluidaCheckBox.setChecked(false);
<<<<<<< HEAD
        Toast.makeText(this, "Campos limpos", Toast.LENGTH_SHORT).show();
=======
        categoriaSpinner.setSelection(0);
        Toast.makeText(getApplicationContext(), "Campos limpos", Toast.LENGTH_SHORT).show();
>>>>>>> 3652e9a45ecbf82e6eb14ecb7cc1cdce791d806a
    }
}
