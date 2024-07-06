package com.br.alexandretrieste.entrega_1;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.br.alexandretrieste.entrega_1.Tarefa;
import com.br.alexandretrieste.entrega_1.TarefaDao;

@Database(entities = {Tarefa.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TarefaDao tarefaDao();

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "tarefa_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
