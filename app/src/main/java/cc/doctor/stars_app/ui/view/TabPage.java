package cc.doctor.stars_app.ui.view;

import android.view.View;

public interface TabPage {
    View getView();

    Integer tabId();

    default void onCreateView() {
    }

    ;

    default void onInit() {
    }

    ;
}
