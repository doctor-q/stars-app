package cc.doctor.stars_app.ui.search.result;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;

import java.util.List;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentSearchResultBinding;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.user.RsResponse;
import cc.doctor.stars_app.state.LoginState;
import cc.doctor.stars_app.ui.video.RsCardAdapter;
import cc.doctor.stars_app.utils.ToastUtils;

public class SearchResultFragment extends Fragment {

    private final int RESULT_COLUMNS = 2;

    private SearchResultViewModel mViewModel;
    private FragmentSearchResultBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchResultBinding.inflate(LayoutInflater.from(getContext()), container, false);
        mViewModel = new ViewModelProvider(this).get(SearchResultViewModel.class);
        RsCardAdapter rsCardAdapter = new RsCardAdapter();
        String keywords = null;
        if (getArguments() != null) {
            keywords = getArguments().getString("keywords");
            if (keywords != null) {
                binding.autoCompleteTextView.setText(keywords);
            }
        }
        mViewModel.getSearchRsResponse().observe(getViewLifecycleOwner(), new Observer<Response<List<RsResponse>>>() {
            @Override
            public void onChanged(Response<List<RsResponse>> listResponse) {
                if (listResponse.isSuccess()) {
                    rsCardAdapter.getRsList().addAll(listResponse.getData());
                    rsCardAdapter.notifyItemRangeInserted(rsCardAdapter.getRsList().size(), listResponse.getData().size());
                } else {
                    ToastUtils.error(getContext(), listResponse.getMsg());
                }
            }
        });
        binding.searchResult.setLayoutManager(new GridLayoutManager(getContext(), RESULT_COLUMNS));
        binding.searchResult.setAdapter(rsCardAdapter);
        mViewModel.searchRs(keywords, LoginState.getInstance(getContext()).token());
        return binding.getRoot();
    }

}