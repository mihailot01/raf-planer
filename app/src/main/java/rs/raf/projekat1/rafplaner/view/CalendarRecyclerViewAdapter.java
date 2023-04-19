package rs.raf.projekat1.rafplaner.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Date;

import rs.raf.projekat1.rafplaner.HelperFunctions;
import rs.raf.projekat1.rafplaner.R;
import rs.raf.projekat1.rafplaner.model.Priority;

public abstract class CalendarRecyclerViewAdapter extends RecyclerView.Adapter<CalendarRecyclerViewAdapter.CalendarViewHolder>{

    private Calendar[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public CalendarRecyclerViewAdapter(Context context, Calendar[] data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed


    @Override
    @NonNull
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.calendar_item, parent, false);
        return new CalendarViewHolder(view);
    }

    public abstract Priority getPriority(Calendar calendar);
    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        Resources res = holder.myTextView.getContext().getResources();
        Calendar calendar = mData[position];
        holder.myTextView.setText(calendar.get(Calendar.DAY_OF_MONTH) + "");
        Priority priority = getPriority(calendar);
        //if(priority != null) {
            holder.myTextView.setBackgroundColor(res.getColor(HelperFunctions.getPriorityColor(priority)));
        //}
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        CalendarViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Calendar getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
