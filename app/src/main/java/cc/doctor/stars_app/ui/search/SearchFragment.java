package cc.doctor.stars_app.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.List;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentSearchBinding;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.state.LoginState;
import cc.doctor.stars_app.ui.search.result.SearchResultFragment;
import cc.doctor.stars_app.utils.ToastUtils;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    public FragmentSearchBinding getBinding() {
        return binding;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // 自动完成文本
        searchViewModel.getSearchSuggest().observe(getViewLifecycleOwner(), new Observer<Response<List<String>>>() {
            @Override
            public void onChanged(Response<List<String>> listResponse) {
                if (listResponse.isSuccess()) {
                    // 自动完成文本
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, listResponse.getData());
                    binding.autoCompleteTextView.setAdapter(adapter);
                    binding.autoCompleteTextView.showDropDown();
                } else {
                    ToastUtils.error(getContext(), listResponse.getMsg());
                }
            }
        });
        binding.autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!binding.autoCompleteTextView.hasFocus()) {
                    return;
                }
                if (binding.autoCompleteTextView.isPerformingCompletion()) {
                    return;
                }
                if (s.length() == 0 && !binding.searchFragmentContainer.getFragment().getClass().equals(SearchRecommendFragment.class)) {
                    FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.search_fragment_container, SearchRecommendFragment.class, null);
                    fragmentTransaction.commit();
                }
                if (s.length() > 0) {
                    searchViewModel.searchSuggest(s.toString(), LoginState.getInstance(getContext()).token());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String keywords = ((TextView) view).getText().toString();
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("keywords", keywords);
                fragmentTransaction.replace(R.id.search_fragment_container, SearchResultFragment.class, bundle);
                fragmentTransaction.commit();
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
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("keywords", binding.autoCompleteTextView.getText().toString());
                fragmentTransaction.replace(R.id.search_fragment_container, SearchResultFragment.class, bundle);
                fragmentTransaction.commit();
            }
        });
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.search_fragment_container, SearchRecommendFragment.class, null);
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