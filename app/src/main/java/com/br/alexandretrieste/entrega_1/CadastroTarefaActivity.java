package com.br.alexandretrieste.entrega_1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CadastroTarefaActivity extends AppCompatActivity {

    private EditText tituloEditText, descricaoEditText, dataEditText;
    private RadioGroup prioridadeRadioGroup;
    private RadioButton radioButtonAlta, radioButtonMedia, radioButtonBaixa;
    private CheckBox concluidaCheckBox;
    private Spinner categoriaSpinner;
    private boolean isEditMode = false;
    private int position = -1;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tarefa);

        tituloEditText = findViewById(R.id.tituloEditText);
        descricaoEditText = findViewById(R.id.descricaoEditText);
        dataEditText = findViewById(R.id.dataEditText);
        prioridadeRadioGroup = findViewById(R.id.prioridadeRadioGroup);
        radioButtonAlta = findViewById(R.id.radioButtonAlta);
        radioButtonMedia = findViewById(R.id.radioButtonMedia);
        radioButtonBaixa = findViewById(R.id.radioButtonBaixa);
        concluidaCheckBox = findViewById(R.id.concluidaCheckBox);
        categoriaSpinner = findViewById(R.id.categoriaSpinner);

        calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dataEditText.setText(sdf.format(calendar.getTime()));

        dataEditText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(CadastroTarefaActivity.this, (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dataEditText.setText(sdf.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        List<String> categorias = new ArrayList<>();
        categorias.add(getString(R.string.casa));
        categorias.add(getString(R.string.estudos));
        categorias.add(getString(R.string.trabalho));

        ArrayAdapter<String> categoriaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriaSpinner.setAdapter(categoriaAdapter);

        Intent intent = getIntent();
        if (intent.hasExtra("tarefa")) {
            Tarefa tarefa = (Tarefa) intent.getSerializableExtra("tarefa");
            fillForm(tarefa);
            isEditMode = true;
            position = intent.getIntExtra("position", -1);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void fillForm(Tarefa tarefa) {
        tituloEditText.setText(tarefa.getTitulo());
        descricaoEditText.setText(tarefa.getDescricao());
        dataEditText.setText(tarefa.getData());
        prioridadeRadioGroup.check(findRadioButtonId(tarefa.getPrioridade()));
        concluidaCheckBox.setChecked(tarefa.isConcluida());
        int spinnerPosition = ((ArrayAdapter<String>) categoriaSpinner.getAdapter()).getPosition(tarefa.getCategoria());
        categoriaSpinner.setSelection(spinnerPosition);
    }

    private int findRadioButtonId(String priority) {
        if (getString(R.string.Alta).equals(priority)) {
            return R.id.radioButtonAlta;
        } else if (getString(R.string.Media).equals(priority)) {
            return R.id.radioButtonMedia;
        } else {
            return R.id.radioButtonBaixa;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_salvar) {
            salvarTarefa();
            return true;
        } else if (item.getItemId() == R.id.menu_limpar) {
            limparCampos();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvarTarefa() {
        String titulo = tituloEditText.getText().toString();
        String descricao = descricaoEditText.getText().toString();
        String data = dataEditText.getText().toString();

        // Verifica se algum RadioButton está selecionado
        int selectedRadioButtonId = prioridadeRadioGroup.getCheckedRadioButtonId();
        if (titulo.isEmpty() || selectedRadioButtonId == -1) {
            Toast.makeText(this, R.string.preencha_todos_os_campos_obrigatorios, Toast.LENGTH_SHORT).show();
            if (titulo.isEmpty()) {
                tituloEditText.requestFocus();
            } else {
                prioridadeRadioGroup.requestFocus();
            }
            return; // Retorna se algum campo obrigatório estiver vazio
        }

        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String prioridade = selectedRadioButton.getText().toString();
        boolean concluida = concluidaCheckBox.isChecked();
        String categoria = categoriaSpinner.getSelectedItem().toString();

        prioridade = getPriorityEnglish(prioridade);

        Tarefa tarefa = new Tarefa(titulo, descricao, prioridade, concluida, categoria, data);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("tarefa", tarefa);
        if (isEditMode) {
            resultIntent.putExtra("position", position);
        }
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private String getPriorityEnglish(String priority) {
        if (priority.equals(getString(R.string.Alta))) {
            return "High";
        } else if (priority.equals(getString(R.string.Media))) {
            return "Medium";
        } else if (priority.equals(getString(R.string.Baixa))) {
            return "Low";
        }
        return priority;
    }

    private void limparCampos() {
        tituloEditText.setText("");
        descricaoEditText.setText("");
        dataEditText.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.getTime()));
        prioridadeRadioGroup.clearCheck();
        concluidaCheckBox.setChecked(false);
        categoriaSpinner.setSelection(0);
        Toast.makeText(this, R.string.campos_limpos, Toast.LENGTH_SHORT).show();
    }
}
