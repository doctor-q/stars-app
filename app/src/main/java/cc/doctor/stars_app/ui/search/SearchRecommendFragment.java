package cc.doctor.stars_app.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.Arrays;
import java.util.List;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentSearchRecommendBinding;
import cc.doctor.stars_app.http.PageResponse;
import cc.doctor.stars_app.http.user.SearchHisResponse;
import cc.doctor.stars_app.state.LoginState;
import cc.doctor.stars_app.ui.search.result.SearchResultFragment;
import cc.doctor.stars_app.ui.view.TabListener;
import cc.doctor.stars_app.utils.ToastUtils;

public class SearchRecommendFragment extends Fragment {

    private FragmentSearchRecommendBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchRecommendBinding.inflate(inflater, container, false);
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
        binding.searchHisList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.search_his_text);
                String keywords = textView.getText().toString();
                ((SearchFragment) getParentFragment()).getBinding().autoCompleteTextView.setText(keywords);
                FragmentTransaction fragmentTransaction = getParentFragment().getChildFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("keywords", keywords);
                fragmentTransaction.replace(R.id.search_fragment_container, SearchResultFragment.class, bundle);
                fragmentTransaction.commit();
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