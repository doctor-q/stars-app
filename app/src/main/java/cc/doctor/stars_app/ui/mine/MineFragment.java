package cc.doctor.stars_app.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentMineBinding;
import cc.doctor.stars_app.state.LoginState;

public class MineFragment extends Fragment {

    private FragmentMineBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        LoginState loginState = LoginState.getInstance(root.getContext());
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        if (!loginState.logged()) {
            // 未登录绑定未登录fragment
            fragmentTransaction.replace(R.id.mine_fragment_container, new MineNoLoginFragment());
        } else {
            // 已登录绑定登录fragment
            fragmentTransaction.replace(R.id.mine_fragment_container, new MineLoginFragment());
        }
        fragmentTransaction.commit();
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