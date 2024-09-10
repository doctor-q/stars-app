package cc.doctor.stars_app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import java.util.Arrays;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentHomeBinding;
import cc.doctor.stars_app.ui.video.VideoFragment;
import cc.doctor.stars_app.ui.view.TabListener;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_follow);
            }
        });

        // 嵌入video fragment
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment_container, new VideoFragment());
        fragmentTransaction.commit();
        // tab切换
        new TabListener(binding.getRoot(), Arrays.asList(binding.homeDiscover, binding.homeFollow),
                binding.viewPage, binding.cursor).bind();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}