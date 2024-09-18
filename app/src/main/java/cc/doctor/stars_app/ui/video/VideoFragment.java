package cc.doctor.stars_app.ui.video;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.OptIn;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import java.util.ArrayList;
import java.util.List;

import cc.doctor.stars_app.databinding.FragmentVideoBinding;
import cc.doctor.stars_app.enums.YesNo;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.user.RsDetailResponse;
import cc.doctor.stars_app.state.LoginState;
import cc.doctor.stars_app.ui.view.SwipeGestureDetector;
import cc.doctor.stars_app.ui.view.TabPage;
import cc.doctor.stars_app.utils.ToastUtils;

public abstract class VideoFragment implements TabPage {
    private final LifecycleOwner owner;
    private final ViewGroup container;
    private final Integer tabId;

    private FragmentVideoBinding binding;
    private VideoViewModel viewModel;
    private List<RsDetailResponse> rsList = new ArrayList<>();

    public VideoFragment(LifecycleOwner owner, ViewGroup container, Integer tabId) {
        this.owner = owner;
        this.container = container;
        this.tabId = tabId;
    }

    abstract public void load(String token, MutableLiveData<Response<List<RsDetailResponse>>> data);

    @Override
    public void onCreateView() {
        binding = FragmentVideoBinding.inflate(LayoutInflater.from(container.getContext()), container, false);
        viewModel = new VideoViewModel();
        viewModel.getCurrent().observe(owner, new Observer<RsDetailResponse>() {
            @Override
            public void onChanged(RsDetailResponse rsDetailResponse) {
                play();
            }
        });
        viewModel.getCollectResponse().observe(owner, new Observer<Response<Integer>>() {
            @Override
            public void onChanged(Response<Integer> integerResponse) {
                if (integerResponse.isSuccess()) {
                    binding.collect.reverse();
                    RsDetailResponse rsDetail = viewModel.getCurrent().getValue();
                    rsDetail.setCollectStatus(YesNo.reverse(rsDetail.getCollectStatus()));
                } else {
                    ToastUtils.error(container.getContext(), integerResponse.getMsg());
                }
            }
        });
        viewModel.getRsDetailList().observe(owner, new Observer<Response<List<RsDetailResponse>>>() {
            @Override
            public void onChanged(Response<List<RsDetailResponse>> listResponse) {
                if (listResponse.isSuccess()) {
                    List<RsDetailResponse> data = listResponse.getData();
                    if (!data.isEmpty()) {
                        viewModel.getCurrent().setValue(data.get(0));
                        rsList.addAll(data);
                    }
                } else {
                    ToastUtils.error(container.getContext(), listResponse.getMsg());
                }
            }
        });
        if (!LoginState.getInstance(container.getContext()).logged()) {
            binding.collect.setVisibility(View.INVISIBLE);
        }

        PlayerView videoView = binding.videoView;
        videoView.setPlayer(new ExoPlayer.Builder(container.getContext()).build());
        Player player = videoView.getPlayer();
        // 播放暂停
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()) {
                    binding.play.setVisibility(View.VISIBLE);
                    player.pause();
                }
            }
        });

        SwipeGestureDetector gestureDetector = new SwipeGestureDetector(binding.getRoot().getContext(), new SwipeGestureDetector.GestureHandler() {
            @Override
            public void down() {
                // 播放上一个视频
                int i = rsList.indexOf(viewModel.getCurrent().getValue());
                if (i > 0) {
                    viewModel.getCurrent().setValue(rsList.get(i - 1));
                }
            }

            @Override
            public void up() {
                // 播放下一个视频
                int i = rsList.indexOf(viewModel.getCurrent().getValue());
                if (i + 1 == rsList.size()) {
                    load(LoginState.getInstance(container.getContext()).token(), viewModel.getRsDetailList());
                } else {
                    viewModel.getCurrent().setValue(rsList.get(i + 1));
                }
            }
        });
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player.isPlaying()) {
                    binding.play.setVisibility(View.INVISIBLE);
                    player.play();
                }
            }
        });
        // 收藏
        binding.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.collect(LoginState.getInstance(v.getContext()).token());
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
        RsDetailResponse rsDetail = viewModel.getCurrent().getValue();
        if (rsDetail == null) {
            load(LoginState.getInstance(container.getContext()).token(), viewModel.getRsDetailList());
            return;
        }
        RsDetailResponse.AwemeDetail aweme = rsDetail.getAwemeDetail();
        binding.avatar.setUrl(rsDetail.getAuthor().getAvatarUrl());
        binding.avatar.setAuthorId(rsDetail.getAuthor().getId());
        binding.author.setText(String.format("@%s", rsDetail.getAuthor().getNickname()));
        binding.title.setText(aweme.getAwTitle());
        // 收藏按钮
        binding.collect.setStatus(rsDetail.getCollectStatus());
        // 关注图标
        binding.follow.setStatus(rsDetail.getFollowStatus());
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
    public void onInit() {
        play();
    }
}
