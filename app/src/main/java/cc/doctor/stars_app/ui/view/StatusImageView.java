package cc.doctor.stars_app.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.enums.YesNo;

public class StatusImageView extends androidx.appcompat.widget.AppCompatImageView {
    private int drawableYes;
    private int drawableNo;
    private int current;

    public StatusImageView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.StatusView);
        drawableYes = typedArray.getResourceId(R.styleable.StatusView_drawables_yes, 0);
        drawableNo = typedArray.getResourceId(R.styleable.StatusView_drawables_no, 0);
    }

    public void setStatus(Integer status) {
        if (status != null && status == YesNo.YES.getValue()) {
            yes();
        } else {
            no();
        }
    }

    public void yes() {
        current = drawableYes;
        setImageResource(drawableYes);
    }

    public void no() {
        current = drawableNo;
        setImageResource(drawableNo);
    }

    public void reverse() {
        int reverse = current == drawableYes ? drawableNo : drawableYes;
        current = reverse;
        setImageResource(reverse);
    }
}
