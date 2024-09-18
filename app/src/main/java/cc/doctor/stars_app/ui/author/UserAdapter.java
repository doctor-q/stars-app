package cc.doctor.stars_app.ui.author;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cc.doctor.stars_app.databinding.ItemUserBinding;
import cc.doctor.stars_app.enums.Role;
import cc.doctor.stars_app.http.user.UserInfo;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private List<UserInfo> userInfos = new ArrayList<>();

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        UserInfo userInfo = userInfos.get(position);
        holder.binding.avatar.setUrl(userInfo.getAvatarUrl());
        holder.binding.name.setText(userInfo.getNickname());
        holder.binding.role.setText(Role.getName(userInfo.getRole()));
    }

    @Override
    public int getItemCount() {
        return userInfos.size();
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private ItemUserBinding binding;

        public UserHolder(ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
