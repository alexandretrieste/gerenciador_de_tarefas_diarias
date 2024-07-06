package com.br.alexandretrieste.entrega_1;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TarefaDao {
    @Insert
    void insert(Tarefa tarefa);

    @Update
    void update(Tarefa tarefa);

    @Delete
    void delete(Tarefa tarefa);

    @Query("SELECT * FROM tarefas")
    List<Tarefa> getAllTarefas();
}
