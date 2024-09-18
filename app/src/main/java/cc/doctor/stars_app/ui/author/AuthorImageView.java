package cc.doctor.stars_app.ui.author;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import cc.doctor.stars_app.R;
import cc.doctor.stars_app.ui.view.SquareImageView;

public class AuthorImageView extends SquareImageView {
    private Integer authorId;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public AuthorImageView(@NonNull Context context) {
        this(context, null);
    }

    public AuthorImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authorId == null) {
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putInt("authorId", authorId);
                Navigation.findNavController(v).navigate(R.id.navigation_author_detail, bundle);
            }
        });
    }
}
