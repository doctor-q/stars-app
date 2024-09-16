package cc.doctor.stars_app.ui.view;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import cc.doctor.stars_app.R;

public class TabListener extends ViewPager.SimpleOnPageChangeListener implements View.OnClickListener {

    private ConstraintLayout layout;
    private List<View> tabs;
    private ViewPager viewPager;
    private View cursor;
    private View currentTab;

    public TabListener(ConstraintLayout layout, List<View> tabs, ViewPager viewPager, View cursor) {
        this.layout = layout;
        this.tabs = tabs;
        this.viewPager = viewPager;
        this.cursor = cursor;
        this.currentTab = tabs.get(0);
    }

    public void bind() {
        for (View tab : tabs) {
            tab.setOnClickListener(this);
        }
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == currentTab) {
            return;
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);
        constraintSet.connect(cursor.getId(), ConstraintSet.START, v.getId(), ConstraintSet.START);
        constraintSet.applyTo(layout);
        for (int i = 0; i < tabs.size(); i++) {
            if (v.getId() == tabs.get(i).getId()) {
                viewPager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        View view = tabs.get(position);
        currentTab = view;
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);
        constraintSet.connect(cursor.getId(), ConstraintSet.START, view.getId(), ConstraintSet.START);
        constraintSet.applyTo(layout);
        onTabChange(currentTab);
    }

    public void onTabChange(View currentTab) {}
}