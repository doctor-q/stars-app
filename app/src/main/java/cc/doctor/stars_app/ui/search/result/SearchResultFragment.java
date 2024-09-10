package cc.doctor.stars_app.ui.search.result;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentSearchResultBinding;

public class SearchResultFragment extends Fragment {

    private SearchResultViewModel mViewModel;
    private FragmentSearchResultBinding binding;

    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        ((GridView)view.findViewById(R.id.search_result)).setAdapter(new SearchResultAdapter(SearchResult.results()));
        return view;
    }

}