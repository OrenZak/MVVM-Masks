package oren.com.masks;

import android.app.Application;

import java.io.File;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class App extends Application {

    public static String INTERNAL_IMAGES_PATH;

//    private boolean clear = true;

    @Override
    public void onCreate() {
        super.onCreate();

        INTERNAL_IMAGES_PATH = getFilesDir().getAbsolutePath() + File.separatorChar + "ExampleImages";

        if (!new File(INTERNAL_IMAGES_PATH).exists())
            new File(INTERNAL_IMAGES_PATH).mkdir();

//        if (clear) {
//            File[] files = new File(INTERNAL_IMAGES_PATH).listFiles();
//            for(File file : files){
//                file.delete();
//            }
//        }
    }
}
