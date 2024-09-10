package cc.doctor.stars_app.ui.mine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentNoLoginMineBinding;

public class NoLoginMineFragment extends Fragment {
    private FragmentNoLoginMineBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoLoginMineBinding.inflate(inflater, container, false);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_login);
            }
        });
        return binding.getRoot();
    }
}