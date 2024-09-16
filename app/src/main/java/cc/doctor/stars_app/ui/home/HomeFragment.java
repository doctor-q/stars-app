package cc.doctor.stars_app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentHomeBinding;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.user.RsDetailResponse;
import cc.doctor.stars_app.state.LoginState;
import cc.doctor.stars_app.ui.video.VideoFragment;
import cc.doctor.stars_app.ui.video.VideoState;
import cc.doctor.stars_app.ui.view.TabListener;
import cc.doctor.stars_app.ui.view.TabPage;
import cc.doctor.stars_app.ui.view.TabPagerAdapter;
import cc.doctor.stars_app.utils.ToastUtils;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_follow);
            }
        });

        // tab切换
        boolean logged = LoginState.getInstance(getContext()).logged();
        List<View> tabs = new ArrayList<>();
        tabs.add(binding.homeDiscover);
        List<VideoFragment> pages = new ArrayList<>();
        pages.add(new VideoFragment(binding.viewPage, binding.homeDiscover.getId()));
        if (logged) {
            tabs.add(binding.homeFollow);
            pages.add(new VideoFragment(binding.viewPage, binding.homeFollow.getId()));
        }
        TabPagerAdapter homePagerAdapter = new TabPagerAdapter(tabs, pages);
        // 收藏
        for (VideoFragment videoFragment : pages) {
            videoFragment.getCollectResponse().observe(getViewLifecycleOwner(), new Observer<Response<Integer>>() {
                @Override
                public void onChanged(Response<Integer> integerResponse) {
                    if (integerResponse.isSuccess()) {
                        videoFragment.onSuccessCollect(integerResponse.getData());
                    } else {
                        ToastUtils.error(getContext(), integerResponse.getMsg());
                    }
                }
            });
        }
        binding.viewPage.setAdapter(homePagerAdapter);
        new TabListener(binding.getRoot(), tabs, binding.viewPage, binding.cursor) {
            @Override
            public void onTabChange(View currentTab) {
                TabPage page = homePagerAdapter.getPage(currentTab);
                VideoFragment videoFragment = (VideoFragment) page;
                if (videoFragment.getVideoState() != null) {
                    videoFragment.play();
                } else {
                    if (currentTab.getId() == binding.homeDiscover.getId()) {
                        homeViewModel.recommend(LoginState.getInstance(getContext()).token());
                    } else {
                        homeViewModel.follow(LoginState.getInstance(getContext()).token());
                    }
                }
            }
        }.bind();
        // 视频数据
        homeViewModel.getRecommend().observe(getViewLifecycleOwner(), new Observer<Response<RsDetailResponse>>() {
            @Override
            public void onChanged(Response<RsDetailResponse> rsDetailResponseResponse) {
                if (rsDetailResponseResponse.isSuccess()) {
                    VideoState videoState = new VideoState(rsDetailResponseResponse.getData());
                    pages.get(0).setVideoState(videoState);
                } else {
                    ToastUtils.error(getContext(), rsDetailResponseResponse.getMsg());
                }
            }
        });
        homeViewModel.getFollow().observe(getViewLifecycleOwner(), new Observer<Response<RsDetailResponse>>() {
            @Override
            public void onChanged(Response<RsDetailResponse> rsDetailResponseResponse) {
                if (rsDetailResponseResponse.isSuccess()) {
                    VideoState videoState = new VideoState(rsDetailResponseResponse.getData());
                    pages.get(1).setVideoState(videoState);
                } else {
                    ToastUtils.error(getContext(), rsDetailResponseResponse.getMsg());
                }
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        homeViewModel.recommend(LoginState.getInstance(getContext()).token());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}