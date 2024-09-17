package cc.doctor.stars_app.ui.view;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class TabPagerAdapter extends PagerAdapter {

    private List<View> tabs;
    private List<? extends TabPage> pages;

    public TabPagerAdapter(List<View> tabs) {
        this.tabs = tabs;
    }

    public TabPagerAdapter(List<View> tabs, List<? extends TabPage> pages) {
        this.tabs = tabs;
        this.pages = pages;
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = tabs.get(position);
        for (TabPage page : pages) {
            if (page.tabId() == view.getId()) {
                page.onCreateView();
                container.addView(page.getView());
                return page.getView();
            }
        }
        container.addView(pages.get(0).getView());
        return pages.get(0).getView();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeViewAt(position);
    }

    public TabPage getPage(View tab) {
        for (TabPage page : pages) {
            if (page.tabId() == tab.getId()) {
                return page;
            }
        }
        return null;
    }

    public void onInit() {
        pages.get(0).onInit();
    }
}
