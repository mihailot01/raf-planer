package rs.raf.projekat1.rafplaner.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import rs.raf.projekat1.rafplaner.HelperFunctions;
import rs.raf.projekat1.rafplaner.model.Task;

import rs.raf.projekat1.rafplaner.R;
import rs.raf.projekat1.rafplaner.view.activities.TaskFormActivity;
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
                    //return false;
                    return oldTask.equals(newTask);
                }
            };

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();
        Task task = getItem(position);
        holder.tvTitle.setText(task.getTitle());
        holder.tvTime.setText(HelperFunctions.getFormattedTime(task.getStartTime()) + " - " + HelperFunctions.getFormattedTime(task.getEndTime()));
//        holder.ivPriority.setBackgroundColor(HelperFunctions.getPriorityColor(task.getPriority()));
//        ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(Color.parseColor("#80000000");
        holder.ivPriority.setImageTintList(null);
        holder.ivPriority.setImageTintList(ColorStateList.valueOf(res.getColor(HelperFunctions.getPriorityColor(task.getPriority()))));
        holder.ivPriority.setBackgroundTintList(ColorStateList.valueOf(HelperFunctions.getPriorityColor(task.getPriority())));

        holder.root.setBackgroundColor(res.getColor(R.color.white));
        if(task.isFinished())
            holder.root.setBackgroundColor(res.getColor(R.color.gray));
//            holder.root.setBackgroundTintList(ColorStateList.valueOf(res.getColor(R.color.gray)));

        holder.getRoot().setOnClickListener(v -> {
            this.onTaskClick(task);
        });

        holder.btnDelete.setOnClickListener(v -> {
            this.onDeleteClick(task);
        });
        holder.btnEdit.setOnClickListener(v -> {
            this.onEditClick(task);
        });
    }

    public abstract void onTaskClick(Task task);

    public abstract void onDeleteClick(Task task);

    public abstract void onEditClick(Task task);

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvTime;
        private ImageView ivPriority;

        private ImageButton btnDelete;
        private ImageButton btnEdit;
        private View root;
        public MyViewHolder(View view) {
            super(view);
            root = view.findViewById(R.id.taskListRoot);
            tvTitle = view.findViewById(R.id.txtTaskListItemTitle);
            tvTime = view.findViewById(R.id.txtTaskListItemTime);
            ivPriority = view.findViewById(R.id.taskImg);
            btnDelete = view.findViewById(R.id.deleteBtn);
            btnEdit = view.findViewById(R.id.editBtn);
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
