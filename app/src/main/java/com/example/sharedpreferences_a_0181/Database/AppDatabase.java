package com.example.sharedpreferences_a_0181.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.sharedpreferences_a_0181.Dao.TodoDao;
import com.example.sharedpreferences_a_0181.Model.Todo;

@Database(entities = {Todo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TodoDao todoDao();
}