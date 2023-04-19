package rs.raf.projekat1.rafplaner.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import rs.raf.projekat1.rafplaner.AppModule;
import rs.raf.projekat1.rafplaner.R;
import rs.raf.projekat1.rafplaner.view.fragments.TaskPreviewFragment;

public class TaskPreviewActivity extends FragmentActivity {


    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    private int index;

    TextView tvTaskTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_preview);

        index = getIntent().getIntExtra("task_index",0);
//        tvTaskTitle = findViewById(R.id.tvTaskTitle);
//        tvTaskTitle.setText("Task " + index);
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new TaskPreviewActivity.ScreenSlidePagerAdapter2(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(index, false);

    }
    @Override
    public void onBackPressed() {
        Log.d("TEST", "onBackPressed: index "+ index+"   current "+viewPager.getCurrentItem());
        if (viewPager.getCurrentItem() == index) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            if(viewPager.getCurrentItem() > index)
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            else
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter2 extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter2(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            return TaskPreviewFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            return AppModule.getInstance(getApplication()).getDailyTasksViewModel().getFilteredTasks().getValue().size();
        }
    }


}