package cc.doctor.stars_app.ui.mine;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cc.doctor.stars_app.ui.view.TabPagerAdapter;

public class MinePagerAdapter extends TabPagerAdapter {

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

    public MinePagerAdapter(Context context, List<View> tabs) {
        super(tabs);
        // 收藏
        collectView = new RecyclerView(context);
        collectView.setAdapter(new RsCollectAdapter());
        historyView = new RecyclerView(context);
        followView = new RecyclerView(context);
        views.add(collectView);
        views.add(historyView);
        views.add(followView);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return null;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(views.get(position));
    }
}
