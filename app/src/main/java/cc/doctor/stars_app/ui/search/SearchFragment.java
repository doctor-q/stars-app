package cc.doctor.stars_app.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.Arrays;
import java.util.List;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentSearchBinding;
import cc.doctor.stars_app.http.PageResponse;
import cc.doctor.stars_app.http.user.SearchHisResponse;
import cc.doctor.stars_app.state.LoginState;
import cc.doctor.stars_app.ui.view.TabListener;
import cc.doctor.stars_app.utils.ToastUtils;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // 搜索历史
        SearchHisAdapter searchHisAdapter = new SearchHisAdapter();
        binding.searchHisList.setAdapter(searchHisAdapter);
        searchViewModel.getSearchHisResponse().observe(getViewLifecycleOwner(), new Observer<PageResponse<SearchHisResponse>>() {
            @Override
            public void onChanged(PageResponse<SearchHisResponse> response) {
                if (response.isSuccess()) {
                    List<SearchHisResponse> data = response.getData();
                    if (data.size() > 3) {
                        searchViewModel.getSearchHis().setValue(data.subList(0, 3));
                    } else {
                        searchViewModel.getSearchHis().setValue(data);
                    }
                } else {
                    ToastUtils.error(getContext(), response.getMsg());
                }
            }
        });
        searchViewModel.getSearchHis().observe(getViewLifecycleOwner(), new Observer<List<SearchHisResponse>>() {
            @Override
            public void onChanged(List<SearchHisResponse> searchHisResponses) {
                searchHisAdapter.setSearchHisList(searchHisResponses);
                searchHisAdapter.notifyDataSetChanged();
            }
        });
        searchViewModel.searchHis(LoginState.getInstance(getContext()).token());
        binding.searchHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageResponse<SearchHisResponse> response = searchViewModel.getSearchHisResponse().getValue();
                if (response != null && response.isSuccess()) {
                    List<SearchHisResponse> data = response.getData();
                    searchViewModel.getSearchHis().setValue(data);
                }
            }
        });
        // 自动完成文本
        binding.autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // 标签云
        binding.searchLabelCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_label_cloud);
            }
        });
        // 搜索
        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("keywords", binding.autoCompleteTextView.getText().toString());
                Navigation.findNavController(v).navigate(R.id.action_navigation_search_result, bundle);
            }
        });
        // 猜你想搜
        new TabListener(binding.getRoot(), Arrays.asList(binding.searchGuess, binding.searchOthers),
                binding.viewPage, binding.cursor).bind();
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