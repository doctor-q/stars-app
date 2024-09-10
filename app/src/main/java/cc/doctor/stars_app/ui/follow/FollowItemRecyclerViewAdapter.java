package cc.doctor.stars_app.ui.follow;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cc.doctor.stars_app.databinding.FragmentFollowBinding;

public class FollowItemRecyclerViewAdapter extends RecyclerView.Adapter<FollowItemRecyclerViewAdapter.ViewHolder> {

    private final List<FollowResult> mValues;

    public FollowItemRecyclerViewAdapter(List<FollowResult> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentFollowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.name.setText(mValues.get(position).getName());
        holder.description.setText(mValues.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FollowResult mItem;
        public final ImageView avatar;
        public final TextView name;
        public final TextView description;

        public ViewHolder(FragmentFollowBinding binding) {
            super(binding.getRoot());
            avatar = binding.avatar;
            name = binding.name;
            description = binding.description;
        }
    }
}