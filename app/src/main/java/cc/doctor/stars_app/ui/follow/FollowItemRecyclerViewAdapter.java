package cc.doctor.stars_app.ui.follow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cc.doctor.stars_app.databinding.ItemFollowBinding;
import cc.doctor.stars_app.enums.YesNo;
import cc.doctor.stars_app.http.PageResponse;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.user.AuthorFollowResponse;
import cc.doctor.stars_app.state.LoginState;
import cc.doctor.stars_app.ui.view.SquareImageView;
import cc.doctor.stars_app.ui.view.StatusButton;
import cc.doctor.stars_app.utils.ToastUtils;

public class FollowItemRecyclerViewAdapter extends RecyclerView.Adapter<FollowItemRecyclerViewAdapter.ViewHolder> {

    private final List<AuthorFollowResponse> mValues = new ArrayList<>();
    private final FollowViewModel viewModel;

    public List<AuthorFollowResponse> getmValues() {
        return mValues;
    }

    public FollowItemRecyclerViewAdapter(Context context, FollowViewModel followViewModel, LifecycleOwner owner) {
        this.viewModel = followViewModel;
        followViewModel.getFollowResponse().observe(owner, new Observer<Response<Integer>>() {
            @Override
            public void onChanged(Response<Integer> response) {
                if (response.isSuccess()) {
                    for (int i = 0; i < mValues.size(); i++) {
                        AuthorFollowResponse followResponse = mValues.get(i);
                        if (Objects.equals(response.getData(), followResponse.getId())) {
                            followResponse.setFollowStatus(YesNo.reverse(followResponse.getFollowStatus()));
                            notifyItemChanged(i);
                            break;
                        }
                    }
                } else {
                    ToastUtils.error(context, response.getMsg());
                }
            }
        });
        followViewModel.getAuthorFollowResponse().observe(owner, new Observer<PageResponse<AuthorFollowResponse>>() {
            @Override
            public void onChanged(PageResponse<AuthorFollowResponse> authorFollowResponsePageResponse) {
                if (authorFollowResponsePageResponse.isSuccess()) {
                    List<AuthorFollowResponse> data = authorFollowResponsePageResponse.getData();
                    if (!data.isEmpty()) {
                        mValues.addAll(data);
                        notifyItemRangeInserted(mValues.size(), data.size());
                    }
                } else {
                    ToastUtils.error(context, authorFollowResponsePageResponse.getMsg());
                }
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(ItemFollowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        AuthorFollowResponse authorFollow = mValues.get(position);
        holder.mItem = authorFollow;
        holder.avatar.setUrl(authorFollow.getAvatarUrl());
        holder.name.setText(authorFollow.getNickName());
        holder.description.setText(authorFollow.getDescription());
        holder.followButton.setStatus(authorFollow.getFollowStatus());
        holder.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.follow(authorFollow.getId(), YesNo.reverse(authorFollow.getFollowStatus()), LoginState.getInstance(v.getContext()).token());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void clear() {
        mValues.clear();
        notifyItemRemoved(0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public AuthorFollowResponse mItem;
        public final SquareImageView avatar;
        public final TextView name;
        public final TextView description;
        public final StatusButton followButton;

        public ViewHolder(ItemFollowBinding binding) {
            super(binding.getRoot());
            avatar = binding.avatar;
            name = binding.name;
            description = binding.description;
            followButton = binding.followButton;
        }
    }
}