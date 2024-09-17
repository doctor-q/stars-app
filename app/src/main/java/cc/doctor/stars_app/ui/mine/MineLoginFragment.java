package cc.doctor.stars_app.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentMineLoginBinding;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.user.AuthorFollowResponse;
import cc.doctor.stars_app.http.user.RsCollectResponse;
import cc.doctor.stars_app.http.user.RsHisResponse;
import cc.doctor.stars_app.http.user.UserDetailResponse;
import cc.doctor.stars_app.state.LoginState;
import cc.doctor.stars_app.ui.follow.FollowItemRecyclerViewAdapter;
import cc.doctor.stars_app.ui.follow.FollowViewModel;
import cc.doctor.stars_app.ui.video.RsCardAdapter;
import cc.doctor.stars_app.ui.view.TabListener;
import cc.doctor.stars_app.ui.view.TabPagerAdapter;
import cc.doctor.stars_app.utils.ToastUtils;

public class MineLoginFragment extends Fragment {

    private FragmentMineLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MineViewModel mineViewModel = new ViewModelProvider(this).get(MineViewModel.class);
        binding = FragmentMineLoginBinding.inflate(inflater, container, false);

        // tab
        List<View> tabs = Arrays.asList(binding.collect, binding.history, binding.follow);
        List<MinePage<?>> pages = new ArrayList<>();
        RsCardAdapter rsCardAdapter = new RsCardAdapter();
        pages.add(new MinePage<>(getContext(), binding.collect.getId(), rsCardAdapter, 2));
        RsHistoryAdapter rsHistoryAdapter = new RsHistoryAdapter();
        pages.add(new MinePage<>(getContext(), binding.history.getId(), rsHistoryAdapter));
        FollowItemRecyclerViewAdapter followAdapter = new FollowItemRecyclerViewAdapter(getContext(), new ViewModelProvider(this).get(FollowViewModel.class), getViewLifecycleOwner());
        pages.add(new MinePage<>(getContext(), binding.follow.getId(), followAdapter));
        TabPagerAdapter minePagerAdapter = new TabPagerAdapter(tabs, pages);
        binding.viewPage.setAdapter(minePagerAdapter);
        new TabListener(binding.getRoot(), tabs, binding.viewPage, binding.cursor).bind();
        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_settings);
            }
        });

        // 个人信息
        mineViewModel.getUserDetailResponse().observe(getViewLifecycleOwner(), new Observer<Response<UserDetailResponse>>() {
            @Override
            public void onChanged(Response<UserDetailResponse> userDetailResponseResponse) {
                if (userDetailResponseResponse.isSuccess()) {
                    UserDetailResponse userDetail = userDetailResponseResponse.getData();
                    if (userDetail.getAvatarUrl() != null) {
                        binding.avatar.setUrl(userDetail.getAvatarUrl());
                    } else {
                        binding.avatar.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_person_outline_24, null));
                    }
                    binding.nickname.setText(userDetail.getNickname());
                    binding.role.setText(userDetail.getRoleName());
                    binding.age.setText(userDetail.getChildAge());
                    // tab
                    List<RsCollectResponse> collectResponses = userDetail.getRsCollectPage().getData();
                    if (collectResponses != null && !collectResponses.isEmpty()) {
                        int position = rsCardAdapter.getRsList().size();
                        rsCardAdapter.getRsList().addAll(collectResponses);
                        rsCardAdapter.notifyItemRangeInserted(position, collectResponses.size());
                    }
                    List<RsHisResponse> hisResponses = userDetail.getRsHisPage().getData();
                    if (hisResponses != null && !hisResponses.isEmpty()) {
                        int position = rsHistoryAdapter.getRsHisList().size();
                        rsHistoryAdapter.getRsHisList().addAll(hisResponses);
                        rsHistoryAdapter.notifyItemRangeInserted(position, hisResponses.size());
                    }
                    List<AuthorFollowResponse> followResponses = userDetail.getFollowPage().getData();
                    if (followResponses != null && !followResponses.isEmpty()) {
                        int position = followAdapter.getmValues().size();
                        followAdapter.getmValues().addAll(followResponses);
                        followAdapter.notifyItemRangeInserted(position, followResponses.size());
                    }
                } else {
                    ToastUtils.error(getContext(), userDetailResponseResponse.getMsg());
                }
            }
        });
        mineViewModel.getUserDetail(LoginState.getInstance(getContext()).token());
        return binding.getRoot();
    }


}