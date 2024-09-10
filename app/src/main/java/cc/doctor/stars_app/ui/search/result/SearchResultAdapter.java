package cc.doctor.stars_app.ui.search.result;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cc.doctor.stars_app.R;

public class SearchResultAdapter extends BaseAdapter {

    private List<SearchResult> searchResultList;

    public SearchResultAdapter(List<SearchResult> searchResultList) {
        this.searchResultList = searchResultList;
    }

    @Override
    public int getCount() {
        return searchResultList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        //如果缓存为空，我们生成新的布局作为1个item
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            //因为getView()返回的对象，adapter会自动赋给ListView
            view = inflater.inflate(R.layout.search_result_item, null);
        } else {
            view = convertView;
        }
        SearchResult searchResult = searchResultList.get(position);
        ((TextView) view.findViewById(R.id.description)).setText(searchResult.getDescription());
        ((TextView) view.findViewById(R.id.author)).setText(searchResult.getAuthor());
        ((TextView) view.findViewById(R.id.time)).setText(searchResult.getCreateTime());
        return view;
    }
}
