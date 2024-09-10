package cc.doctor.stars_app.ui.labelcloud;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cc.doctor.stars_app.R;

public class LabelCloudFragment extends Fragment {

    private LabelCloudViewModel mViewModel;

    public static LabelCloudFragment newInstance() {
        return new LabelCloudFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_label_cloud, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LabelCloudViewModel.class);
        // TODO: Use the ViewModel
    }

}