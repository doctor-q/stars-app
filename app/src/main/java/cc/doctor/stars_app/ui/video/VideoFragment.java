package cc.doctor.stars_app.ui.video;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import java.io.File;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentVideoBinding;

public class VideoFragment extends Fragment {
    private FragmentVideoBinding binding;

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVideoBinding.inflate(inflater, container, false);
        // 视频
        VideoView videoView = binding.videoView;
        File file = new File(Environment.getExternalStorageDirectory(), "Movies/video.mp4");
        videoView.setVideoURI(Uri.fromFile(file));
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 自动播放视频
                videoView.start();
            }
        });
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.play.setVisibility(View.VISIBLE);
                videoView.pause();
            }
        });
        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.play.setVisibility(View.INVISIBLE);
                videoView.start();
            }
        });
        videoView.start();
        // 收藏
        binding.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.collect.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_star_24, null));
            }
        });
        // 跳转原始链接
        // 进度条
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return binding.getRoot();
    }
}