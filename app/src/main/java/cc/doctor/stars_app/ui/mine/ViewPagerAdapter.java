package cc.doctor.stars_app.ui.mine;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cc.doctor.stars_app.http.PageResponse;
import cc.doctor.stars_app.http.user.RsCollectResponse;

public class ViewPagerAdapter extends PagerAdapter {

    private String[] titles = new String[]{"收藏", "历史", "关注"};
    private List<View> views = new ArrayList<>();
    private RecyclerView collectView;
    private RsCollectAdapter collectAdapter;
    private RecyclerView historyView;
    private RecyclerView followView;

    public RecyclerView getCollectView() {
        return collectView;
    }

    public RecyclerView getHistoryView() {
        return historyView;
    }

    public RecyclerView getFollowView() {
        return followView;
    }

    public RsCollectAdapter getCollectAdapter() {
        return collectAdapter;
    }

    public ViewPagerAdapter(Context context) {
        // 收藏
        collectView = new RecyclerView(context);
        collectView.setAdapter(new RsCollectAdapter());
        historyView = new RecyclerView(context);
        followView = new RecyclerView(context);
        views.add(collectView);
        views.add(historyView);
        views.add(followView);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        TextView textView = new TextView(container.getContext());
        textView.setText(titles[position]);
        views.add(textView);
        container.addView(textView);
        return textView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(views.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
