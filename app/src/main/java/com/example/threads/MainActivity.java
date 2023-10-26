package com.example.threads;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button click;
    public static final String TAG = "MainActivity";
    /*
    If you want to run the on ui thread..
     */
     private Handler handler = new Handler();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        click = findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callThreads();
//                callInterfaceThread();
            }
        });
    }

    private void callInterfaceThread() {
    Example example = new Example(10);
    example.run();
    }

    private void callThreads() {
        Examples examples = new Examples(10);
        examples.start();
    }

    /*
    You are using thread as a class....
     */
    class Examples extends Thread {
        int second;

        public Examples(int second) {
            this.second = second;
        }

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < second; i++) {
                if(i==5){
                    /* This is handler.... and you cann't use handler inside the for loop..
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            click.setText("50%");
                        }
                    });

                     */
                    /*
                    This is another method of runOnUiThread run on ui thread...
                     */
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            click.setText("50%");
                        }
                    });
                }
                try {
                    Log.i(TAG, "msg: " + i);
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    /*
    this is another method to implements the thread..
     */

    class Example implements Runnable {
        int second;

        public Example(int second) {
            this.second = second;
        }

        @Override
        public void run() {
            for (int i = 0; i < second; i++) {

                try {

                    Log.i(TAG, "msg: " + i);
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
}

