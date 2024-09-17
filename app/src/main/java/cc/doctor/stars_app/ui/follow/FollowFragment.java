package cc.doctor.stars_app.ui.follow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import cc.doctor.stars_app.databinding.FragmentFollowListBinding;
import cc.doctor.stars_app.state.LoginState;

/**
 * A fragment representing a list of Items.
 */
public class FollowFragment extends Fragment {
    private int mColumnCount = 1;
    private FragmentFollowListBinding binding;
    private FollowViewModel followViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFollowListBinding.inflate(LayoutInflater.from(getContext()), container, false);
        followViewModel = new ViewModelProvider(this).get(FollowViewModel.class);
        FollowItemRecyclerViewAdapter followItemRecyclerViewAdapter = new FollowItemRecyclerViewAdapter(getContext(), followViewModel, getViewLifecycleOwner());
        // Set the adapter
        Context context = binding.followList.getContext();
        if (mColumnCount <= 1) {
            binding.followList.setLayoutManager(new LinearLayoutManager(context));
        } else {
            binding.followList.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        binding.followList.setAdapter(followItemRecyclerViewAdapter);
        // search
        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followItemRecyclerViewAdapter.clear();
                followViewModel.searchAuthor(binding.autoCompleteTextView.getText().toString(), LoginState.getInstance(getContext()).token());
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        followViewModel.searchAuthor(null, LoginState.getInstance(getContext()).token());
    }
}