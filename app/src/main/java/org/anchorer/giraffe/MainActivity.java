package org.anchorer.giraffe;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mUrlEditText;
    private Button mGoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUrlEditText = (EditText) findViewById(R.id.et_url);
        setDragData();

        mGoBtn = (Button) findViewById(R.id.bt_go);
        mGoBtn.setOnDragListener(new GiraffeDragEventListener(this));
        mGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = mUrlEditText.getText().toString();

                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra(WebActivity.FIELD_URL, url);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (isInMultiWindowMode()) {
                    // launch this activity in another split window next to the current one
                    intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT);
                }
                startActivity(intent);
            }
        });
    }

    /**
     * Set url as drag data for EditText
     */
    private void setDragData() {
        mUrlEditText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // create ClipData Object
                ClipData.Item item = new ClipData.Item(mUrlEditText.getText().toString());
                ClipData data = new ClipData("LABEL", new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);

                // start drag
                view.startDragAndDrop(data, new GiraffeDragShadowBuilder(view), null, View.DRAG_FLAG_GLOBAL);

                return false;
            }
        });
    }

    /**
     * Custom Drag Shadow Builder
     */
    static class GiraffeDragShadowBuilder extends View.DragShadowBuilder {

        private static Drawable mShadow;

        public GiraffeDragShadowBuilder(View v) {
            super(v);
            mShadow = new ColorDrawable(Color.LTGRAY);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            mShadow.draw(canvas);
        }

        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            int width = getView().getWidth();
            int height = getView().getHeight();

            mShadow.setBounds(0, 0, width, height);
            outShadowSize.set(width, height);
            outShadowTouchPoint.set(width / 2, height / 2);
        }
    }
}
