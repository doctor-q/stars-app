package cc.doctor.stars_app.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.enums.YesNo;

public class StatusButton extends androidx.appcompat.widget.AppCompatButton {
    private int textYes;
    private int textNo;
    private int current;

    public StatusButton(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.StatusView);
        textYes = typedArray.getResourceId(R.styleable.StatusView_text_yes, 0);
        textNo = typedArray.getResourceId(R.styleable.StatusView_text_no, 0);
    }

    public void setStatus(Integer status) {
        if (status != null && status == YesNo.YES.getValue()) {
            yes();
        } else {
            no();
        }
    }

    public void yes() {
        current = textYes;
        setText(textYes);
    }

    public void no() {
        current = textNo;
        setText(textNo);
    }

    public void reverse() {
        int reverse = current == textYes ? textNo : textYes;
        current = reverse;
        setText(reverse);
    }
}
