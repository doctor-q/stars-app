package cc.doctor.stars_app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
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
import cc.doctor.stars_app.ui.view.TabListener;
import cc.doctor.stars_app.ui.view.TabPage;
import cc.doctor.stars_app.ui.view.TabPagerAdapter;

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
        pages.add(new VideoFragment(getViewLifecycleOwner(), binding.viewPage, binding.homeDiscover.getId()) {

            @Override
            public void load(String token, MutableLiveData<Response<List<RsDetailResponse>>> data) {
                homeViewModel.recommend(token, data);
            }
        });
        if (logged) {
            tabs.add(binding.homeFollow);
            pages.add(new VideoFragment(getViewLifecycleOwner(), binding.viewPage, binding.homeFollow.getId()) {

                @Override
                public void load(String token, MutableLiveData<Response<List<RsDetailResponse>>> data) {
                    homeViewModel.follow(token, data);
                }
            });
        }
        TabPagerAdapter homePagerAdapter = new TabPagerAdapter(tabs, pages);
        binding.viewPage.setAdapter(homePagerAdapter);
        new TabListener(binding.getRoot(), tabs, binding.viewPage, binding.cursor) {
            @Override
            public void onTabChange(View currentTab) {
                TabPage page = homePagerAdapter.getPage(currentTab);
                VideoFragment videoFragment = (VideoFragment) page;
                videoFragment.play();
            }
        }.bind();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}