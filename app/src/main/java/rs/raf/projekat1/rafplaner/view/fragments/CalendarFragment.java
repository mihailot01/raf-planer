package rs.raf.projekat1.rafplaner.view.fragments;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rs.raf.projekat1.rafplaner.AppModule;
import rs.raf.projekat1.rafplaner.HelperFunctions;
import rs.raf.projekat1.rafplaner.R;

import rs.raf.projekat1.rafplaner.databinding.FragmentCalendarBinding;
import rs.raf.projekat1.rafplaner.model.Priority;
import rs.raf.projekat1.rafplaner.model.Task;
import rs.raf.projekat1.rafplaner.view.CalendarRecyclerViewAdapter;
import rs.raf.projekat1.rafplaner.view.activities.DailyPlanHolderActivity;
import rs.raf.projekat1.rafplaner.view.activities.TaskPreviewActivity;


public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;

    private RecyclerView rvCalendar;
    private CalendarRecyclerViewAdapter adapter;

    private int clickPosition = 0;

    private TextView tvMonth;
    public CalendarFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rvCalendar = binding.rvCalendar;
        tvMonth = binding.tvMonth;
        rvCalendar.setLayoutManager(new GridLayoutManager(getContext(), 7));

        int todayPosition = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(Long.parseLong("1641212068000")));
        Calendar[] data = new Calendar[1000];
        for (int i = 0; i < 1000; i++) {
            data[i] = Calendar.getInstance();
            data[i].setTime(cal.getTime());
            if(HelperFunctions.trimDate(cal.getTime()).equals(HelperFunctions.trimDate(new Date())))
                todayPosition = i;
            cal.add(Calendar.DAY_OF_MONTH,1);
        }

        Application application = requireActivity().getApplication();
        // set up the RecyclerView
        adapter = new CalendarRecyclerViewAdapter(getContext(), data) {

            @Override
            public Priority getPriority(Calendar calendar) {
                List<Task> list =  AppModule.getInstance(application).getDb().getTasksByDate(calendar.getTime());
                if(list.size() == 0)
                    return null;
                for(Task t: list){
                    if (t.getPriority() == Priority.HIGH)
                        return Priority.HIGH;
                }
                for(Task t: list)
                    if (t.getPriority() == Priority.MEDIUM)
                        return Priority.MEDIUM;
                return Priority.LOW;
            }

        };

        Context context = getContext();
        adapter.setClickListener(new CalendarRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("TEST", "onItemClick: "+data[position].getTime());
                clickPosition = position;
                Intent intent = new Intent(context, DailyPlanHolderActivity.class);
                intent.putExtra("date", data[position].getTime());
                startActivity(intent);

            }
        });


//        adapter.setClickListener(this);
        rvCalendar.setAdapter(adapter);

        rvCalendar.getLayoutManager().scrollToPosition(todayPosition);
        tvMonth.setText(data[500].getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.forLanguageTag("sr-Latn-RS"))+" "+data[500].get(Calendar.YEAR));
        rvCalendar.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = rvCalendar.getChildCount();
                int totalItemCount = rvCalendar.getLayoutManager().getItemCount();
                int firstVisibleItem = ((GridLayoutManager) rvCalendar.getLayoutManager()).findFirstVisibleItemPosition();
                Log.d("TEST", "onScrolled: "+visibleItemCount+" "+totalItemCount+" "+firstVisibleItem);
                tvMonth.setText(data[firstVisibleItem].getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.forLanguageTag("sr-Latn-RS"))+" "+data[firstVisibleItem].get(Calendar.YEAR));

//                visibleItemCount = rvCalendar.getChildCount();

                //                totalItemCount = rvCalendar.getItemCount();
//                firstVisibleItem = rvCalendar.getLfindFirstVisibleItemPosition();

//                if (loading) {
//                    if (totalItemCount > previousTotal) {
//                        loading = false;
//                        previousTotal = totalItemCount;
//                    }
//                }
//                if (!loading && (totalItemCount - visibleItemCount)
//                        <= (firstVisibleItem + visibleThreshold)) {
//                    // End has been reached
//
//                    Log.i("Yaeye!", "end called");
//
//                    // Do something
//
//                    loading = true;
//                }
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter.notifyItemChanged(clickPosition);
    }
}