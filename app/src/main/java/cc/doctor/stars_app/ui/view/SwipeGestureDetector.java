package cc.doctor.stars_app.ui.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cc.doctor.stars_app.utils.ToastUtils;

public class SwipeGestureDetector extends GestureDetector {

    private static int swipeX;
    private static int swipeY;
    private static final int VELOCITY = 100;

    public SwipeGestureDetector(@Nullable Context context, GestureHandler handler) {
        super(context, new SimpleOnGestureListener() {

            @Override
            public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
                SwipeDirection direction = null;
                if (e1.getX() - e2.getX() >= swipeX && Math.abs(velocityX) >= VELOCITY) {
                    handler.left();
                    direction = SwipeDirection.LEFT;
                } else if (e1.getX() - e2.getX() <= -swipeX && Math.abs(velocityX) >= VELOCITY) {
                    handler.right();
                    direction = SwipeDirection.RIGHT;
                } else if (e1.getY() - e2.getY() >= swipeY && Math.abs(velocityY) >= VELOCITY) {
                    handler.up();
                    direction = SwipeDirection.UP;
                } else if (e1.getY() - e2.getY() <= -swipeY && Math.abs(velocityY) >= VELOCITY) {
                    handler.down();
                    direction = SwipeDirection.DOWN;
                }
                if (direction != null) {
                    handler.swipe(direction);
                }
                // 任意滑动不触发点击
                return true;
            }
        });
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        swipeX = metrics.widthPixels / 3;
        swipeY = metrics.heightPixels / 3;
    }

    public interface GestureHandler {

        default void up() {
        }

        default void down() {
        }

        default void left() {
        }

        default void right() {
        }

        default void swipe(SwipeDirection direction) {
        }
    }

    public static class ToastGestureHandler implements GestureHandler {

        private Context context;

        public ToastGestureHandler(Context context) {
            this.context = context;
        }

        @Override
        public void swipe(SwipeDirection direction) {
            ToastUtils.info(context, direction.name());
        }
    }

    enum SwipeDirection {
        UP, DOWN, LEFT, RIGHT;
    }
}
