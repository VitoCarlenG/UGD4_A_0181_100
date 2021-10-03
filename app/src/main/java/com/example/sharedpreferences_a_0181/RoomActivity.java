package com.example.sharedpreferences_a_0181;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sharedpreferences_a_0181.Adapter.TodoAdapter;
import com.example.sharedpreferences_a_0181.Database.DatabaseTodo;
import com.example.sharedpreferences_a_0181.Model.Todo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {
    private EditText edt_todo;
    private Button btnReset, btnAdd;
    private RecyclerView rv_todoList;

    private List<Todo> todoList;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.todo_recycler);

        edt_todo=findViewById(R.id.edt_todo);
        btnReset=findViewById(R.id.btnReset);
        btnAdd=findViewById(R.id.btnAdd);
        rv_todoList=findViewById(R.id.rv_todoList);

        rv_todoList.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setSelectedItemId(R.id.page2);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Deprecated
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page1:
                        startActivity(new Intent(RoomActivity.this, MainActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.page2:
                        return true;
                }
                return false;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edt_todo.getText().toString().isEmpty()) {
                    addTodo();
                }else {
                    Toast.makeText(RoomActivity.this, "Belum diisi tuh", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_todo.setText("");
            }
        });

        getTodos();

        todoList=new ArrayList<>();
    }

    private void addTodo() {
        final String title=edt_todo.getText().toString();

        class AddTodo extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Todo todo = new Todo();
                todo.setTitle(title);

                DatabaseTodo.getInstance(getApplicationContext())
                        .getDatabase()
                        .todoDao()
                        .insertTodo(todo);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(RoomActivity.this, "Berhasil menambahkan data", Toast.LENGTH_SHORT).show();
                edt_todo.setText("");
                getTodos();
            }
        }
        AddTodo addTodo=new AddTodo(  );
        addTodo.execute();
    }

    private void getTodos() {
        class GetTodos extends AsyncTask<Void, Void, List<Todo>> {
            @Override
            protected List<Todo> doInBackground(Void... voids) {
                List<Todo> todoList=DatabaseTodo.getInstance(getApplicationContext())
                        .getDatabase()
                        .todoDao()
                        .getAll();
                return todoList;
            }

            @Override
            protected void onPostExecute(List<Todo> todos) {
                super.onPostExecute(todos);
                todoAdapter=new TodoAdapter(todos, RoomActivity.this);
                rv_todoList.setAdapter(todoAdapter);
            }
        }

        GetTodos getTodos=new GetTodos();
        getTodos.execute();
    }
}