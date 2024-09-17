package cc.doctor.stars_app.ui.mine;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cc.doctor.stars_app.databinding.ItemFollowBinding;
import cc.doctor.stars_app.http.user.AuthorFollowResponse;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FollowViewHolder> {

    private List<AuthorFollowResponse> authorList = new ArrayList<>();

    public List<AuthorFollowResponse> getAuthorList() {
        return authorList;
    }

    @NonNull
    @Override
    public FollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FollowAdapter.FollowViewHolder(ItemFollowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FollowViewHolder holder, int position) {
        AuthorFollowResponse author = authorList.get(position);
        holder.binding.avatar.setUrl(author.getAvatarUrl());
        holder.binding.description.setText(author.getDescription());
        holder.binding.name.setText(author.getNickName());
        holder.binding.followButton.setStatus(author.getFollowStatus());
    }

    @Override
    public int getItemCount() {
        return authorList.size();
    }

    class FollowViewHolder extends RecyclerView.ViewHolder {
        private ItemFollowBinding binding;

        public FollowViewHolder(ItemFollowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
