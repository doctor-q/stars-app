package cc.doctor.stars_app.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cc.doctor.stars_app.databinding.FragmentSettingsNotifyBinding;
import cc.doctor.stars_app.databinding.FragmentSettingsPlayBinding;

public class SettingsNotifyFragment extends Fragment {

    private FragmentSettingsNotifyBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsNotifyBinding.inflate(LayoutInflater.from(getContext()), container, false);
        return binding.getRoot();
    }
}
