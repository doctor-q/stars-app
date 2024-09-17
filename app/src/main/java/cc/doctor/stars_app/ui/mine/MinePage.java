package cc.doctor.stars_app.ui.mine;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cc.doctor.stars_app.ui.view.TabPage;

public class MinePage<VH extends RecyclerView.ViewHolder> extends RecyclerView implements TabPage {
    private Integer tabId;

    public MinePage(@NonNull Context context, Integer tabId, Adapter<VH> adapter) {
        this(context, tabId, adapter, 1);
    }

    public MinePage(@NonNull Context context, Integer tabId, Adapter<VH> adapter, int columnNum) {
        super(context);
        this.tabId = tabId;
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (columnNum > 1) {
            setLayoutManager(new GridLayoutManager(context, columnNum));
        } else {
            setLayoutManager(new LinearLayoutManager(context));
        }
        this.setAdapter(adapter);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public Integer tabId() {
        return tabId;
    }
}
