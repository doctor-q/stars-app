package cc.doctor.stars_app.ui.video;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cc.doctor.stars_app.databinding.ItemResourceCardBinding;
import cc.doctor.stars_app.http.user.RsCollectResponse;
import cc.doctor.stars_app.http.user.RsResponse;

public class RsCardAdapter extends RecyclerView.Adapter<RsCardAdapter.RsViewHolder> {

    private List<RsResponse> rsList = new ArrayList<>();

    public List<RsResponse> getRsList() {
        return rsList;
    }

    @NonNull
    @Override
    public RsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RsViewHolder(ItemResourceCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public int getItemCount() {
        return rsList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RsViewHolder holder, int position) {
        RsResponse rsResponse = rsList.get(position);
        RsResponse.Aweme aweme = rsResponse.getAweme();
        RsResponse.Author author = rsResponse.getAuthor();
        holder.binding.cover.setUrl(aweme.getAwCoverUrl());
        holder.binding.description.setText(aweme.getAwTitle());
        holder.binding.avatar.setUrl(author.getAvatarUrl());
        holder.binding.author.setText(author.getNickname());
        holder.binding.time.setText(aweme.getAwCreateTime());
    }

    class RsViewHolder extends RecyclerView.ViewHolder {

        private ItemResourceCardBinding binding;

        public RsViewHolder(ItemResourceCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
