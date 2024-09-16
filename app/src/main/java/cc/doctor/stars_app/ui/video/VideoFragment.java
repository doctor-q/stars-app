package cc.doctor.stars_app.ui.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.OptIn;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import java.util.Objects;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.databinding.FragmentVideoBinding;
import cc.doctor.stars_app.enums.YesNo;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.RetrofitFactory;
import cc.doctor.stars_app.http.user.RsCollectRequest;
import cc.doctor.stars_app.http.user.RsDetailResponse;
import cc.doctor.stars_app.state.LoginState;
import cc.doctor.stars_app.ui.view.TabPage;
import retrofit2.Call;

public class VideoFragment implements TabPage {
    private ViewGroup container;
    private Integer tabId;

    private VideoState videoState;
    private FragmentVideoBinding binding;
    public MutableLiveData<Response<Integer>> collectResponse = new MutableLiveData<>();

    public VideoFragment(ViewGroup container, Integer tabId) {
        this.container = container;
        this.tabId = tabId;
    }

    public VideoState getVideoState() {
        return videoState;
    }

    public void setVideoState(VideoState videoState) {
        this.videoState = videoState;
        play();
    }

    public MutableLiveData<Response<Integer>> getCollectResponse() {
        return collectResponse;
    }

    public void onSuccessCollect(Integer id) {
        RsDetailResponse rsDetail = videoState.getRsDetail();
        if (Objects.equals(rsDetail.getId(), id)) {
            int starId;
            if (rsDetail.getCollectStatus() == YesNo.NO.getValue()) {
                starId = R.drawable.baseline_star_24;
            } else {
                starId = R.drawable.baseline_star_border_24;
            }
            binding.collect.setImageDrawable(ResourcesCompat.getDrawable(container.getResources(), starId, null));
            rsDetail.setCollectStatus(YesNo.reverse(rsDetail.getCollectStatus()));
        }
    }

    @Override
    public void onCreateView() {
        binding = FragmentVideoBinding.inflate(LayoutInflater.from(container.getContext()));
        if (!LoginState.getInstance(container.getContext()).logged()) {
            binding.collect.setVisibility(View.INVISIBLE);
        }

        PlayerView videoView = binding.videoView;
        videoView.setPlayer(new ExoPlayer.Builder(container.getContext()).build());
        // 播放暂停
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.play.setVisibility(View.VISIBLE);
                videoView.getPlayer().pause();
            }
        });
        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.play.setVisibility(View.INVISIBLE);
                videoView.getPlayer().play();
            }
        });
        // 收藏
        binding.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RsDetailResponse rsDetail = videoState.getRsDetail();
                Call<Response<Integer>> call = RetrofitFactory.resourceApi.collect(new RsCollectRequest(rsDetail.getId(), YesNo.reverse(rsDetail.getCollectStatus())),
                        LoginState.getInstance(v.getContext()).token());
                call.enqueue(new RetrofitFactory.ResponseCallback<>(collectResponse));
            }
        });
        // 跳转原始链接
        // 打开字幕
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
    }

    @OptIn(markerClass = UnstableApi.class)
    public void play() {
        if (binding == null) {
            return;
        }

        RsDetailResponse rsDetail = videoState.getRsDetail();
        RsDetailResponse.AwemeDetail aweme = rsDetail.getAwemeDetail();
        binding.avatar.setUrl(rsDetail.getAuthor().getAvatarUrl());
        binding.author.setText("@" + rsDetail.getAuthor().getNickname());
        binding.title.setText(aweme.getAwTitle());
        // 收藏按钮
        int starId;
        if (rsDetail.getCollectStatus() == YesNo.NO.getValue()) {
            starId = R.drawable.baseline_star_border_24;
        } else {
            starId = R.drawable.baseline_star_24;
        }
        binding.collect.setImageDrawable(ResourcesCompat.getDrawable(binding.getRoot().getResources(), starId, null));
        // 关注图标
        int followId;
        if (rsDetail.getFollowStatus() == YesNo.NO.getValue()) {
            followId = R.drawable.baseline_add_box_24;
        } else {
            followId = R.drawable.baseline_check_circle_24;
        }
        binding.follow.setImageDrawable(ResourcesCompat.getDrawable(binding.getRoot().getResources(), followId, null));
        // 视频
        PlayerView videoView = binding.videoView;
        if (videoView.getPlayer().isPlaying()) {
            videoView.getPlayer().stop();
        }
        // 缓冲进度
        videoView.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING);
        videoView.getPlayer().setMediaItem(MediaItem.fromUri(aweme.getPlayUrl()));
        videoView.getPlayer().prepare();
        videoView.getPlayer().play();
        videoView.getPlayer().addListener(new Player.Listener() {
            @Override
            public void onEvents(Player player, Player.Events events) {
                Player.Listener.super.onEvents(player, events);
            }
        });
    }

    @Override
    public View getView() {
        return binding.getRoot();
    }

    @Override
    public Integer tabId() {
        return tabId;
    }

    @Override
    public void onSelected(Context context) {

    }
}
