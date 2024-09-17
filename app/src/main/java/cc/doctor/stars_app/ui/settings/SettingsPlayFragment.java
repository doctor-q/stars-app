package cc.doctor.stars_app.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cc.doctor.stars_app.databinding.FragmentSettingsPlayBinding;

public class SettingsPlayFragment extends Fragment {

    private FragmentSettingsPlayBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsPlayBinding.inflate(LayoutInflater.from(getContext()), container, false);
        return binding.getRoot();
    }
}
