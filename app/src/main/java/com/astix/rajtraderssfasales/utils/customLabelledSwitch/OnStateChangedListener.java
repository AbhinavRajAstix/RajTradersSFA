package com.astix.rajtraderssfasales.utils.customLabelledSwitch;

import android.view.View;

/**
 * <p>
 * Created by Angad Singh on 25/2/18.
 * </p>
 * <p>
 * Interface definition for a callback to be invoked when state of switch is changed.
 *
 * <p>This is a <a href="package-summary.html">event listener</a>
 * whose event method is {@link #onStateChanged(View, int)}.
 *
 * @since 1.1.0
 */
public interface OnStateChangedListener {

    /**
     * Called when a view changes it's state.
     *
     * @param view  The view whose state was changed.
     * @param state The state of the view.
     */
    void onStateChanged(View view, int state);
}