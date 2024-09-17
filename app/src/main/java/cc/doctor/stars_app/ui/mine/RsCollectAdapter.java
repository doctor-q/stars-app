package cc.doctor.stars_app.ui.mine;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cc.doctor.stars_app.databinding.ItemResourceCardBinding;
import cc.doctor.stars_app.http.user.RsCollectResponse;
import cc.doctor.stars_app.http.user.RsResponse;

public class RsCollectAdapter extends RecyclerView.Adapter<RsCollectAdapter.RsCollectViewHolder> {

    private List<RsCollectResponse> rsCollectList = new ArrayList<>();

    public List<RsCollectResponse> getRsCollectList() {
        return rsCollectList;
    }

    @NonNull
    @Override
    public RsCollectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RsCollectViewHolder(ItemResourceCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public int getItemCount() {
        return rsCollectList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RsCollectViewHolder holder, int position) {
        RsCollectResponse rsCollect = rsCollectList.get(position);
        RsResponse.Aweme aweme = rsCollect.getAweme();
        RsResponse.Author author = rsCollect.getAuthor();
        holder.binding.cover.setUrl(aweme.getAwCoverUrl());
        holder.binding.description.setText(aweme.getAwTitle());
        holder.binding.avatar.setUrl(author.getAvatarUrl());
        holder.binding.author.setText(author.getNickname());
        holder.binding.time.setText(aweme.getAwCreateTime());
    }

    class RsCollectViewHolder extends RecyclerView.ViewHolder {

        private ItemResourceCardBinding binding;

        public RsCollectViewHolder(ItemResourceCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
