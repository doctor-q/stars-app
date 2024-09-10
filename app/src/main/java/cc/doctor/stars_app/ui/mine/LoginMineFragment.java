package cc.doctor.stars_app.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.Arrays;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentLoginMineBinding;
import cc.doctor.stars_app.ui.view.TabListener;

public class LoginMineFragment extends Fragment {

    private FragmentLoginMineBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginMineBinding.inflate(inflater, container, false);
        binding.viewPage.setAdapter(new ViewPagerAdapter());
        new TabListener(binding.getRoot(), Arrays.asList(binding.collect, binding.history, binding.follow),
                binding.viewPage, binding.cursor).bind();
        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_settings);
            }
        });
        return binding.getRoot();
    }


}