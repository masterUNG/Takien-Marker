package suwuttipoj.nantapak.takienfloatingmarket;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.graphics.drawable.AnimationDrawable;

import suwuttipoj.nantapak.takienfloatingmarket.MainActivity;
import suwuttipoj.nantapak.takienfloatingmarket.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // Load the ImageView that will host the animation and
        // set its background to our AnimationDrawable XML resource
        ImageView img = (ImageView)findViewById(R.id.imageView14);
        img.setBackgroundResource(R.drawable.splashlist);
        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
        // Start the animation (looped playback by default).
        frameAnimation.start();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                }
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }
}
//    AnimationDrawable splash;
//
//    private Handler objHandler;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
//        ImageView image = (ImageView) findViewById(R.id.imageView1);
//        splash = (AnimationDrawable) image.getBackground();
//        image.post(new Starter());
//
//
//        objHandler = new Handler();
//        objHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent objIntent = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(objIntent);
//                finish();
//
//            }
//        }, 4000);
//
//    }//oncreate
//
//    class Starter implements Runnable {
//        public void run() {
//            splash.start();
//        }
//    }
//}//MainClass

