package cc.doctor.stars_app.ui.author;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.doctor.stars_app.databinding.FragmentAuthorBinding;
import cc.doctor.stars_app.http.PageResponse;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.user.AuthorDetailResponse;
import cc.doctor.stars_app.http.user.RsResponse;
import cc.doctor.stars_app.http.user.UserInfo;
import cc.doctor.stars_app.state.LoginState;
import cc.doctor.stars_app.ui.mine.MinePage;
import cc.doctor.stars_app.ui.mine.RsHistoryAdapter;
import cc.doctor.stars_app.ui.video.RsCardAdapter;
import cc.doctor.stars_app.ui.view.TabListener;
import cc.doctor.stars_app.ui.view.TabPagerAdapter;
import cc.doctor.stars_app.utils.ToastUtils;

public class AuthorFragment extends Fragment {

    private AuthorViewModel mViewModel;
    private FragmentAuthorBinding binding;

    public static AuthorFragment newInstance() {
        return new AuthorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthorBinding.inflate(LayoutInflater.from(getContext()), container, false);
        mViewModel = new ViewModelProvider(this).get(AuthorViewModel.class);

        // tab
        List<View> tabs = Arrays.asList(binding.production, binding.followUsers);
        List<MinePage<?>> pages = new ArrayList<>();
        RsCardAdapter rsCardAdapter = new RsCardAdapter();
        pages.add(new MinePage<>(getContext(), binding.production.getId(), rsCardAdapter, 2));
        UserAdapter userAdapter = new UserAdapter();
        pages.add(new MinePage<>(getContext(), binding.followUsers.getId(), userAdapter));
        binding.viewPage.setAdapter(new TabPagerAdapter(tabs, pages));
        new TabListener(binding.getRoot(), tabs, binding.viewPage, binding.cursor).bind();

        mViewModel.getAuthorDetailResponse().observe(getViewLifecycleOwner(), new Observer<Response<AuthorDetailResponse>>() {
            @Override
            public void onChanged(Response<AuthorDetailResponse> authorDetailResponseResponse) {
                if (authorDetailResponseResponse.isSuccess()) {
                    AuthorDetailResponse authorDetail = authorDetailResponseResponse.getData();
                    binding.avatar.setUrl(authorDetail.getAvatarUrl());
                    binding.nickname.setText(authorDetail.getNickName());
                    binding.description.setText(authorDetail.getDescription());
                    binding.followButton.setStatus(authorDetail.getFollowStatus());

                    PageResponse<RsResponse> rsPage = authorDetail.getRsPage();
                    if (rsPage.isSuccess() && !rsPage.getData().isEmpty()) {
                        int position = rsCardAdapter.getRsList().size();
                        rsCardAdapter.getRsList().addAll(rsPage.getData());
                        rsCardAdapter.notifyItemRangeInserted(position, rsPage.getData().size());
                    }
                    PageResponse<UserInfo> userPage = authorDetail.getUserPage();
                    if (userPage.isSuccess() && !userPage.getData().isEmpty()) {
                        int position = userAdapter.getUserInfos().size();
                        userAdapter.getUserInfos().addAll(userPage.getData());
                        userAdapter.notifyItemRangeInserted(position, userPage.getData().size());
                    }
                } else {
                    ToastUtils.error(getContext(), authorDetailResponseResponse.getMsg());
                }
            }
        });
        Bundle arguments = getArguments();
        if (arguments != null) {
            Integer authorId = arguments.getInt("authorId");
            mViewModel.getAuthorDetail(authorId, LoginState.getInstance(getContext()).token());
        }
        return binding.getRoot();
    }

}