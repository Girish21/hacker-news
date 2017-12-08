package com.girish.hackernews.callbacks;

import android.view.View;

/**
 * Created by Girish on 08-Dec-17.
 */

public interface ClickListener {

    public void onClick (View view, int position);
    public void onLongClick (View view, int position);

}
