package cc.doctor.stars_app.ui.follow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;
import java.util.Objects;

import cc.doctor.stars_app.databinding.FragmentFollowListBinding;
import cc.doctor.stars_app.enums.YesNo;
import cc.doctor.stars_app.http.PageResponse;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.user.AuthorFollowResponse;
import cc.doctor.stars_app.state.LoginState;
import cc.doctor.stars_app.utils.ToastUtils;

/**
 * A fragment representing a list of Items.
 */
public class FollowFragment extends Fragment {
    private int mColumnCount = 1;
    private FragmentFollowListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFollowListBinding.inflate(LayoutInflater.from(getContext()), container, false);
        FollowItemRecyclerViewAdapter followItemRecyclerViewAdapter = new FollowItemRecyclerViewAdapter();
        followItemRecyclerViewAdapter.getFollowResponse().observe(getViewLifecycleOwner(), new Observer<Response<Integer>>() {
            @Override
            public void onChanged(Response<Integer> response) {
                if (response.isSuccess()) {
                    List<AuthorFollowResponse> followResponses = followItemRecyclerViewAdapter.getmValues();
                    for (int i = 0; i < followResponses.size(); i++) {
                        AuthorFollowResponse followResponse = followResponses.get(i);
                        if (Objects.equals(response.getData(), followResponse.getId())) {
                            followResponse.setFollowStatus(YesNo.reverse(followResponse.getFollowStatus()));
                            followItemRecyclerViewAdapter.notifyItemChanged(i);
                            break;
                        }
                    }
                } else {
                    ToastUtils.error(getContext(), response.getMsg());
                }
            }
        });
        FollowViewModel followViewModel = new ViewModelProvider(this).get(FollowViewModel.class);
        followViewModel.getAuthorFollowResponse().observe(getViewLifecycleOwner(), new Observer<PageResponse<AuthorFollowResponse>>() {
            @Override
            public void onChanged(PageResponse<AuthorFollowResponse> authorFollowResponsePageResponse) {
                if (authorFollowResponsePageResponse.isSuccess()) {
                    followItemRecyclerViewAdapter.getmValues().addAll(authorFollowResponsePageResponse.getData());
                    followItemRecyclerViewAdapter.notifyItemInserted(0);
                } else {
                    ToastUtils.error(getContext(), authorFollowResponsePageResponse.getMsg());
                }
            }
        });

        followViewModel.searchAuthor(null, LoginState.getInstance(getContext()).token());
        // Set the adapter
        Context context = binding.followList.getContext();
        if (mColumnCount <= 1) {
            binding.followList.setLayoutManager(new LinearLayoutManager(context));
        } else {
            binding.followList.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        binding.followList.setAdapter(followItemRecyclerViewAdapter);
        // search
        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followItemRecyclerViewAdapter.clear();
                followViewModel.searchAuthor(binding.autoCompleteTextView.getText().toString(), LoginState.getInstance(getContext()).token());
            }
        });
        return binding.getRoot();
    }
}