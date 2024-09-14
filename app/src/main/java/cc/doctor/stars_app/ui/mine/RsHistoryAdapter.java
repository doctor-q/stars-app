package cc.doctor.stars_app.ui.mine;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RsHistoryAdapter extends RecyclerView.Adapter<RsHistoryAdapter.RsHistoryViewHolder> {

    @NonNull
    @Override
    public RsHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RsHistoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class RsHistoryViewHolder extends RecyclerView.ViewHolder {

        public RsHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
