package com.example.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListImagesActivity extends ListActivity {

    String[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Handler handler = new Handler();

        Thread th = new Thread(new Runnable() {
            public void run() {

                try {

                    final String[] images = ImageManager.ListImages();

                    handler.post(new Runnable() {

                        public void run() {
                            ListImagesActivity.this.images = images;

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListImagesActivity.this, R.layout.text_view_item, images);
                            setListAdapter(adapter);
                        }
                    });
                }
                catch(Exception ex) {
                    final String exceptionMessage = ex.getMessage();
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(ListImagesActivity.this, exceptionMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }});
        th.start();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getBaseContext(), ImageActivity.class);
        intent.putExtra("image", images[position]);

        startActivity(intent);
    }

}
