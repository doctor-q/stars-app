package cc.doctor.stars_app.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import cc.doctor.stars_app.state.LoginState;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SquareImageView extends AppCompatImageView {

    private String url;

    public void setUrl(String url) {
        this.url = url;
        setRemoteImage(url, this);
    }

    private void setRemoteImage(String url, ImageView imageView) {
        //加载网络图片
        final OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
        //设置请求头，在同一个session
        okHttpClient.interceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("token", LoginState.getInstance(getContext()).token());
                return chain.proceed(builder.build());
            }
        });
        Picasso picasso = new Picasso.Builder(getContext()).downloader(new OkHttp3Downloader(okHttpClient.build())).build();
        picasso.load(url).into(imageView);
        Picasso.get().load(url).into(imageView);
    }

    public SquareImageView(@NonNull Context context) {
        super(context);
    }

    public SquareImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 设置View宽高的测量值
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        // 只有setMeasuredDimension调用之后，才能使用getMeasuredWidth()和getMeasuredHeight()来获取视图测量出的宽高，以此之前调用这两个方法得到的值都会是0
        int childWidthSize = getMeasuredWidth();

        // 高度和宽度一样
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
