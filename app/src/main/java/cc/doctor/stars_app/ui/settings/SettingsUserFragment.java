package cc.doctor.stars_app.ui.settings;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentSettingsUserBinding;
import cc.doctor.stars_app.enums.Gender;
import cc.doctor.stars_app.enums.Role;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.user.UserInfo;
import cc.doctor.stars_app.state.LoginState;
import cc.doctor.stars_app.utils.ToastUtils;
import cc.doctor.stars_app.utils.UriUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SettingsUserFragment extends Fragment {

    private SettingsUserViewModel mViewModel;
    private FragmentSettingsUserBinding binding;
    private ActivityResultLauncher<String> launcher;

    public static SettingsUserFragment newInstance() {
        return new SettingsUserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsUserBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(SettingsUserViewModel.class);
        mViewModel.getUserInfoResponse().observe(getViewLifecycleOwner(), new Observer<Response<UserInfo>>() {
            @Override
            public void onChanged(Response<UserInfo> userInfoResponse) {
                if (userInfoResponse.isSuccess()) {
                    UserInfo userInfo = userInfoResponse.getData();
                    binding.nickname.setText(userInfo.getNickname());
                    binding.role.setSelection(userInfo.getRole());
                    binding.gender.setSelection(userInfo.getChildGender());
                    binding.birth.setText(userInfo.getChildBirth());
                    if (userInfo.getAvatarUrl() != null) {
                        binding.avatar.setUrl(userInfo.getAvatarUrl());
                    } else {
                        binding.avatar.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_person_outline_24, null));
                    }
                } else {
                    ToastUtils.error(getContext().getApplicationContext(), userInfoResponse.getMsg());
                }
            }
        });
        mViewModel.getUserInfo(LoginState.getInstance(getContext()).token());

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                binding.avatar.setImageURI(uri);
                MultipartBody.Part part = null;
                if (uri != null) {
                    try {
                        byte[] bytes = UriUtils.uriToBytes(getActivity().getContentResolver(), uri);
                        DocumentFile documentFile = DocumentFile.fromSingleUri(getContext(), uri);
                        String name = documentFile.getName();
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), bytes);
                        part = MultipartBody.Part.createFormData("file", name, requestBody);
                        mViewModel.upload(part, LoginState.getInstance(getContext()).token());
                    } catch (IOException e) {
                        ToastUtils.error(getContext(), "文件异常");
                    }
                }
            }
        });
        binding.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
        mViewModel.getUploadResponse().observe(getViewLifecycleOwner(), new Observer<Response<Integer>>() {
            @Override
            public void onChanged(Response<Integer> integerResponse) {
                if (integerResponse.isSuccess()) {
                    binding.avatarId.setText(integerResponse.getData().toString());
                } else {
                    ToastUtils.error(getContext(), integerResponse.getMsg());
                }
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = new UserInfo();
                userInfo.setNickname(binding.nickname.getText().toString());
                userInfo.setRole(Role.getRolePosition(binding.role.getSelectedItemPosition()));
                userInfo.setChildGender(Gender.getGenderPosition(binding.gender.getSelectedItemPosition()));
                userInfo.setChildBirth(binding.birth.getText().toString());
                if (!binding.avatarId.getText().toString().isEmpty()) {
                    userInfo.setAvatar(Integer.parseInt(binding.avatarId.getText().toString()));
                }
                mViewModel.updateUserInfo(userInfo, LoginState.getInstance(getContext()).token());
            }
        });
        mViewModel.getUpdateUserResponse().observe(getViewLifecycleOwner(), new Observer<Response<String>>() {
            @Override
            public void onChanged(Response<String> response) {
                if (response.isSuccess()) {
                    ToastUtils.info(getContext(), "保存成功！");
                } else {
                    ToastUtils.error(getContext(), response.getMsg());
                }
            }
        });
        return binding.getRoot();
    }

}