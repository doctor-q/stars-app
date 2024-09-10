package cc.doctor.stars_app.ui.login;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.Calendar;
import java.util.Locale;

import cc.doctor.stars_app.databinding.FragmentRegisterBinding;
import cc.doctor.stars_app.enums.Gender;
import cc.doctor.stars_app.enums.Role;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.utils.ToastUtils;

public class RegisterFragment extends Fragment {
    private RegisterViewModel registerViewModel;
    private FragmentRegisterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        registerViewModel.getRegisterResponse().observe(getViewLifecycleOwner(), new Observer<Response<?>>() {
            @Override
            public void onChanged(Response<?> response) {
                if (response.isSuccess()) {
                    ToastUtils.tips(getContext().getApplicationContext(), "注册成功");
                    // 回到登录页面
                    Navigation.findNavController(binding.getRoot()).navigateUp();
                } else {
                    ToastUtils.error(getContext().getApplicationContext(), response.getMsg());
                }
            }
        });
        registerViewModel.getEmailVerifyResponse().observe(getViewLifecycleOwner(), new Observer<Response<?>>() {
            @Override
            public void onChanged(Response<?> response) {
                if (response.isSuccess()) {
                    ToastUtils.info(getContext().getApplicationContext(), "验证码已发送，请前往邮箱验证！");
                } else {
                    ToastUtils.error(getContext().getApplicationContext(), response.getMsg());
                }
            }
        });
        // Inflate the layout for this fragment
        binding.birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        binding.birth.setText(String.format(Locale.CHINESE, "%d-%02d-%02d", year, month + 1, dayOfMonth));
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        // 验证邮箱
        binding.sendVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerViewModel.verifyEmail(binding.username.getText().toString());
            }
        });
        // 注册
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerViewModel.register(binding.username.getText().toString(), binding.verifyCode.getText().toString(), binding.password.getText().toString(),
                        binding.nickname.getText().toString(), Role.getRolePosition(binding.role.getSelectedItemPosition()),
                        Gender.getGenderPosition(binding.gender.getSelectedItemPosition()), binding.birth.getText().toString());
            }
        });
        return binding.getRoot();
    }
}