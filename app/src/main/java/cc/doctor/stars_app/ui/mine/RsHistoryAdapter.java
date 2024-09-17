package cc.doctor.stars_app.ui.mine;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cc.doctor.stars_app.databinding.ItemResourceListBinding;
import cc.doctor.stars_app.http.user.RsCollectResponse;
import cc.doctor.stars_app.http.user.RsHisResponse;
import cc.doctor.stars_app.http.user.RsResponse;

public class RsHistoryAdapter extends RecyclerView.Adapter<RsHistoryAdapter.RsHistoryViewHolder> {

    private List<RsHisResponse> rsHisList = new ArrayList<>();

    public List<RsHisResponse> getRsHisList() {
        return rsHisList;
    }

    @NonNull
    @Override
    public RsHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RsHistoryAdapter.RsHistoryViewHolder(ItemResourceListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RsHistoryViewHolder holder, int position) {
        RsHisResponse rsHisResponse = rsHisList.get(position);
        RsResponse.Aweme aweme = rsHisResponse.getAweme();
        RsResponse.Author author = rsHisResponse.getAuthor();
        holder.binding.cover.setUrl(aweme.getAwCoverUrl());
        holder.binding.description.setText(aweme.getAwTitle());
        holder.binding.avatar.setUrl(author.getAvatarUrl());
        holder.binding.author.setText(author.getNickname());
        holder.binding.time.setText(aweme.getAwCreateTime());
    }

    @Override
    public int getItemCount() {
        return rsHisList.size();
    }

    class RsHistoryViewHolder extends RecyclerView.ViewHolder {
        private ItemResourceListBinding binding;

        public RsHistoryViewHolder(ItemResourceListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
