package cc.doctor.stars_app.ui.settings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FeedbackItemBinding;
import cc.doctor.stars_app.databinding.FragmentFeedbackBinding;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.user.FeedbackResponse;
import cc.doctor.stars_app.state.LoginState;
import cc.doctor.stars_app.ui.view.SquareImageView;
import cc.doctor.stars_app.utils.ToastUtils;
import cc.doctor.stars_app.utils.UriUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FeedbackFragment extends Fragment {

    private FragmentFeedbackBinding binding;
    private FeedbackViewModel viewModel;
    private ActivityResultLauncher<String> launcher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);
        binding = FragmentFeedbackBinding.inflate(inflater, container, false);

        ImageAdapter imageAdapter = new ImageAdapter();
        binding.image.setAdapter(imageAdapter);
        binding.image.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return false;
                }
                imageAdapter.uriList.remove(position - 1);
                imageAdapter.notifyDataSetChanged();
                return true;
            }
        });
        launcher = registerForActivityResult(new ActivityResultContracts.GetMultipleContents(), new ActivityResultCallback<List<Uri>>() {
            @Override
            public void onActivityResult(List<Uri> list) {
                imageAdapter.uriList.addAll(list);
                imageAdapter.notifyDataSetChanged();
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MultipartBody.Part> parts = new ArrayList<>();
                if (!imageAdapter.uriList.isEmpty()) {
                    for (Uri uri : imageAdapter.uriList) {
                        byte[] bytes = null;
                        try {
                            bytes = UriUtils.uriToBytes(getActivity().getContentResolver(), uri);
                        } catch (IOException e) {
                            ToastUtils.error(getContext(), "文件异常");
                            return;
                        }
                        DocumentFile documentFile = DocumentFile.fromSingleUri(getContext(), uri);
                        String name = documentFile.getName();
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), bytes);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("fileList", name, requestBody);
                        parts.add(part);
                    }
                }
                RequestBody feedback = RequestBody.create(MediaType.parse("text/plain"), binding.editTextTextMultiLine.getText().toString());
                viewModel.feedback(feedback, parts, LoginState.getInstance(getContext()).token());
            }
        });
        viewModel.getResponse().observe(getViewLifecycleOwner(), new Observer<Response<String>>() {
            @Override
            public void onChanged(Response<String> response) {
                if (response.isSuccess()) {
                    ToastUtils.info(getContext(), "反馈已提交！");
                } else {
                    ToastUtils.error(getContext(), response.getMsg());
                }
            }
        });
        FeedbackHisAdapter feedbackHisAdapter = new FeedbackHisAdapter();
        viewModel.getFeedbackResponse().observe(getViewLifecycleOwner(), new Observer<Response<List<FeedbackResponse>>>() {
            @Override
            public void onChanged(Response<List<FeedbackResponse>> listResponse) {
                if (listResponse.isSuccess() && !listResponse.getData().isEmpty()) {
                    feedbackHisAdapter.feedbacks = listResponse.getData();
                    feedbackHisAdapter.notifyItemInserted(listResponse.getData().size() - 1);
                } else {
                    ToastUtils.error(getContext(), listResponse.getMsg());
                }
            }
        });
        binding.feedbackHis.setAdapter(feedbackHisAdapter);
        binding.feedbackHis.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.getFeedback(LoginState.getInstance(getContext()).token());
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private View createView(ViewGroup parent, Uri uri, Integer resourceId) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.image_item, null);
        ImageView imageView = view.findViewById(R.id.image);
        if (uri != null) {
            DocumentFile documentFile = DocumentFile.fromSingleUri(getContext(), uri);
            String name = documentFile.getName();
            TextView textView = view.findViewById(R.id.filename);
            textView.setText(name);
            imageView.setImageURI(uri);
        } else {
            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), resourceId, null));
        }
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return view;
    }

    public View selectImageView(ViewGroup parent) {
        View view = createView(parent, null, R.drawable.baseline_add_photo_alternate_24);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
        return view;
    }

    class ImageAdapter extends BaseAdapter {

        private List<Uri> uriList = new ArrayList<>();

        public ImageAdapter() {
        }

        @Override
        public int getCount() {
            return uriList.size() + 1;
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
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position == 0) {
                return selectImageView(parent);
            } else {
                Uri uri = uriList.get(position - 1);
                return createView(parent, uri, null);
            }
        }
    }

    class FeedbackHisAdapter extends RecyclerView.Adapter<FeedbackItemViewHolder> {

        private List<FeedbackResponse> feedbacks = new ArrayList<>();

        @NonNull
        @Override
        public FeedbackItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FeedbackItemViewHolder(FeedbackItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull FeedbackItemViewHolder holder, int position) {
            FeedbackResponse feedbackResponse = feedbacks.get(position);
            holder.feedback.setText(feedbackResponse.getFeedback());
            List<String> urls = feedbackResponse.getUrls();
            if (urls != null) {
                if (!urls.isEmpty()) {
                    holder.image1.setUrl(urls.get(0));
                }
                if (urls.size() >= 2) {
                    holder.image2.setUrl(urls.get(1));
                }
            }
        }

        @Override
        public int getItemCount() {
            return feedbacks.size();
        }
    }

    class FeedbackItemViewHolder extends RecyclerView.ViewHolder {

        private TextView feedback;
        private SquareImageView image1;
        private SquareImageView image2;

        public FeedbackItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public FeedbackItemViewHolder(FeedbackItemBinding binding) {
            super(binding.getRoot());
            this.feedback = binding.feedback;
            this.image1 = binding.image1;
            this.image2 = binding.image2;
        }
    }

}