package cc.doctor.stars_app.ui.follow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cc.doctor.stars_app.databinding.ItemFollowBinding;
import cc.doctor.stars_app.enums.YesNo;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.RetrofitFactory;
import cc.doctor.stars_app.http.user.AuthorFollowRequest;
import cc.doctor.stars_app.http.user.AuthorFollowResponse;
import cc.doctor.stars_app.state.LoginState;
import cc.doctor.stars_app.ui.view.SquareImageView;
import cc.doctor.stars_app.ui.view.StatusButton;
import retrofit2.Call;

public class FollowItemRecyclerViewAdapter extends RecyclerView.Adapter<FollowItemRecyclerViewAdapter.ViewHolder> {

    private final List<AuthorFollowResponse> mValues = new ArrayList<>();
    private MutableLiveData<Response<Integer>> followResponse = new MutableLiveData<>();

    public List<AuthorFollowResponse> getmValues() {
        return mValues;
    }

    public MutableLiveData<Response<Integer>> getFollowResponse() {
        return followResponse;
    }

    public FollowItemRecyclerViewAdapter() {
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
                Call<Response<Integer>> call = RetrofitFactory.userApi.follow(new AuthorFollowRequest(authorFollow.getId(), YesNo.reverse(authorFollow.getFollowStatus())),
                        LoginState.getInstance(v.getContext()).token());
                call.enqueue(new RetrofitFactory.ResponseCallback<>(followResponse));
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