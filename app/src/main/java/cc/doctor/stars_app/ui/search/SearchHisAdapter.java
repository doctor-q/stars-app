package cc.doctor.stars_app.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.http.user.SearchHisResponse;

public class SearchHisAdapter extends BaseAdapter {

    private List<SearchHisResponse> searchHisList = new ArrayList<>();

    public SearchHisAdapter() {
    }

    public void setSearchHisList(List<SearchHisResponse> searchHisList) {
        this.searchHisList = searchHisList;
    }

    @Override
    public int getCount() {
        return searchHisList.size();
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
            view = inflater.inflate(R.layout.item_search_his, null);
        } else {
            view = convertView;
        }
        SearchHisResponse searchHis = searchHisList.get(position);
        TextView textView = (TextView) view.findViewById(R.id.search_his_text);
        textView.setText(searchHis.getKeywords());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 搜索结果页
                Bundle bundle = new Bundle();
                bundle.putString("keywords", textView.getText().toString());
                Navigation.findNavController(parent).navigate(R.id.action_navigation_search_result, bundle);
            }
        });
        return view;
    }
}
