package cc.doctor.stars_app.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentSearchBinding;
import cc.doctor.stars_app.databinding.FragmentSettingsBinding;
import cc.doctor.stars_app.state.LoginState;

/**
 * A fragment representing a list of Items.
 */
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        // 账号设置
        binding.account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_settings_user);
            }
        });
        // 反馈
        binding.feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_feedback);
            }
        });
        // 退出登录
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginState.getInstance(getContext()).setLogged(null);
                Navigation.findNavController(v).navigateUp();
            }
        });
        return binding.getRoot();
    }
}