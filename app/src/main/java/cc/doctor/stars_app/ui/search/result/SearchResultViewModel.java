package cc.doctor.stars_app.ui.search.result;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SearchResultViewModel extends ViewModel {
    private MutableLiveData<List<SearchResult>> searchResultList;

    public SearchResultViewModel() {
        this.searchResultList = new MutableLiveData<>();
        this.searchResultList.setValue(SearchResult.results());
    }

    public MutableLiveData<List<SearchResult>> getSearchResultList() {
        return searchResultList;
    }
}