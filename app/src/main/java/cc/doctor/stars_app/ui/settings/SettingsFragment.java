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