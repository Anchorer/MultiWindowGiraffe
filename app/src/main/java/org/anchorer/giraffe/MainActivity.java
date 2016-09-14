package org.anchorer.giraffe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        mGoBtn = (Button) findViewById(R.id.bt_go);

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

}
