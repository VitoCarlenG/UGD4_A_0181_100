package com.example.sharedpreferences_a_0181.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharedpreferences_a_0181.Database.AppDatabase;
import com.example.sharedpreferences_a_0181.Database.DatabaseTodo;
import com.example.sharedpreferences_a_0181.Model.Todo;
import com.example.sharedpreferences_a_0181.R;
import com.example.sharedpreferences_a_0181.RoomActivity;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<Todo> todoList;
    private Context context;

    public TodoAdapter(List<Todo> todoList, Context context) {
        this.todoList=todoList;
        this.context=context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo=todoList.get(position);
        holder.tvTitle.setText(todo.getTitle());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTodo(todo);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAlertDialog(todo, holder.getPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageButton btnDelete, btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            btnDelete=itemView.findViewById(R.id.btnDelete);
            btnEdit=itemView.findViewById(R.id.btnEdit);
        }
    }

    private void deleteTodo(final Todo todo) {
        class DeleteTodo extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseTodo.getInstance(context)
                        .getDatabase()
                        .todoDao()
                        .deleteTodo(todo);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                todoList.remove(todo);
                notifyDataSetChanged();
                Toast.makeText(context, "Berhasil menghapus", Toast.LENGTH_SHORT).show();
            }
        }
        DeleteTodo deleteTodo=new DeleteTodo();
        deleteTodo.execute();
    }

    private void customAlertDialog(Todo todo, int position) {
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_alert_dialog);

        final EditText edit = dialog.findViewById(R.id.edit);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

        edit.setText(todo.getTitle());


        btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    todo.setTitle(edit.getText().toString());
                    updateTodo(todo,position);
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
        });
        dialog.show();
    }

    private void updateTodo(final Todo todo, int position) {
        class UpdateTodo extends AsyncTask<Void, Void, Void> {
            @Override

            protected Void doInBackground(Void... voids) {
                DatabaseTodo.getInstance(context.getApplicationContext())
                        .getDatabase()
                        .todoDao()
                        .updateTodo(todo);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(context.getApplicationContext(), "Berhasil mengubah data", Toast.LENGTH_SHORT).show();
            }
        }
        UpdateTodo updateTodo = new UpdateTodo(  );
        updateTodo.execute();
    }
}
