package cc.doctor.stars_app.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentMineNoLoginBinding;

public class MineNoLoginFragment extends Fragment {
    private FragmentMineNoLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMineNoLoginBinding.inflate(inflater, container, false);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_login);
            }
        });
        return binding.getRoot();
    }
}