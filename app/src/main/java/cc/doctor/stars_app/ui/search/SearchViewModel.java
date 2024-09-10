package cc.doctor.stars_app.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<SearchHis>> searchHis = new MutableLiveData<>();

    public SearchViewModel() {
        List<SearchHis> his = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            his.add(new SearchHis("搜索历史" + i, new Date()));
        }
        searchHis.setValue(his);
    }

    public MutableLiveData<List<SearchHis>> getSearchHis() {
        return searchHis;
    }
}