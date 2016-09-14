package org.anchorer.giraffe;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Custom Drag Event Listener
 * Created by Anchorer on 16/9/14.
 */
public class GiraffeDragEventListener implements View.OnDragListener {

    private Context mContext;

    public GiraffeDragEventListener(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        switch (dragEvent.getAction()) {

            // if ClipData has text plain data, set color filter to blue, indicating this view supports this type of data
            case DragEvent.ACTION_DRAG_STARTED: {
                if (dragEvent.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    setColorFilterForView(view, Color.BLUE);
                    return true;
                }
                return false;
            }

            // if the dragged shadow enters the bound of this view, set color filter to green
            case DragEvent.ACTION_DRAG_ENTERED: {
                setColorFilterForView(view, Color.GREEN);
                return true;
            }

            // same as started state
            case DragEvent.ACTION_DRAG_EXITED: {
                if (dragEvent.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    setColorFilterForView(view, Color.BLUE);
                    return true;
                }
                return false;
            }

            // ignore this event
            case DragEvent.ACTION_DRAG_LOCATION: {
                return true;
            }

            // when dropped, get data
            case DragEvent.ACTION_DROP: {
                ClipData data = dragEvent.getClipData();
                String url = (String) data.getItemAt(0).getText();

                if (view instanceof WebView) {
                    ((WebView) view).loadUrl(url);
                } else {
                    Toast.makeText(mContext, "Drag Data Dropped:: " + url, Toast.LENGTH_SHORT).show();
                }

                clearColorFilterForView(view);
                return true;
            }

            // deal with end event
            case DragEvent.ACTION_DRAG_ENDED: {
                clearColorFilterForView(view);

                if (dragEvent.getResult()) {
                    Toast.makeText(mContext, "This drag is handled.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "This drag doesn't work.", Toast.LENGTH_SHORT).show();
                }

                return true;
            }

            default: {
                Log.e(Const.LOG, "Unknown drag event by GiraffeDragEventListener.");
                break;
            }
        }

        return false;
    }

    private void setColorFilterForView(View view, int color) {
        Drawable background = view.getBackground();
        if (background != null) {
            background.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
            view.invalidate();
        }
    }

    private void clearColorFilterForView(View view) {
        Drawable background = view.getBackground();
        if (background != null) {
            background.clearColorFilter();
            view.invalidate();
        }
    }

}
