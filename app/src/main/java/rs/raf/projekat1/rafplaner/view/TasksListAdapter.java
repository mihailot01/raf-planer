package rs.raf.projekat1.rafplaner.view;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import rs.raf.projekat1.rafplaner.model.Task;

import rs.raf.projekat1.rafplaner.R;
import rs.raf.projekat1.rafplaner.view.activities.TaskPreviewActivity;
import rs.raf.projekat1.rafplaner.view.fragments.TaskPreviewFragment;

public abstract class TasksListAdapter extends ListAdapter<Task, TasksListAdapter.MyViewHolder> {

//    List<Task> tasks;

    public TasksListAdapter() {
        super(DIFF_CALLBACK);
    }



    public static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Task>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Task oldTask, @NonNull Task newTask) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldTask.getId() == newTask.getId();
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Task oldTask, @NonNull Task newTask) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldTask.equals(newTask);
                }
            };

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task task = getItem(position);
        holder.tvTitle.setText(task.getTitle());
        holder.getRoot().setOnClickListener(v -> {
            this.onTaskClick(position);
        });
    }

    public abstract void onTaskClick(int position);


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private View root;
        public MyViewHolder(View view) {
            super(view);
            root = view.findViewById(R.id.taskListRoot);
            tvTitle = view.findViewById(R.id.txtTaskListItemTitle);
        }

        public View getRoot() {
            return root;
        }
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_item, parent, false);

        return new MyViewHolder(view);
    }


}
