package oren.com.masks.manager;


import android.os.SystemClock;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import oren.com.masks.model.Mask;


public final class DummyDownloaderManger extends BaseManager<Mask>{
    private static final String TAG = DummyDownloaderManger.class.getSimpleName();

    private static DummyDownloaderManger sInstance;

    private Mask mLastMaskAdded;
    private CompositeDisposable compositeDisposable;

    private DummyDownloaderManger() {
    }

    public static DummyDownloaderManger getInstance() {
        synchronized (DummyDownloaderManger.class) {
            if (sInstance == null) {
                sInstance = new DummyDownloaderManger();
            }
            return sInstance;
        }
    }

    @Override
    public void add(Mask mask) {
        super.add(mask);
        mLastMaskAdded = mask;
        if(compositeDisposable == null)
            compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(getSaveMaskDisposable(mLastMaskAdded));
    }

    @Override
    public void remove(Mask mask) {
        super.remove(mask);
        if(mList != null && mList.size() == 0) {
            close();
        }
    }

    /**
     * Creates Disposable with the {@link Mask} object that
     * we want to observe and accept the last object that came in
     * to out Disposal.
     * @param mask object we should observe on.
     * @return Disposal object to out main CompositeDisposal.
     */
    private Disposable getSaveMaskDisposable(Mask mask) {
        return getSaveMaskObservable(mask)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Mask>() {
                               @Override
                               public void accept(Mask mask) throws Exception {
                                   if(mask == mLastMaskAdded) {
                                       EventBus.getDefault().postSticky(mask);
                                       Log.d(TAG, "Accept mask");
                                   }
                                   remove(mask);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(TAG,"Error on save mask");
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                Log.d(TAG,"Save mask completed");
                            }
                        });
    }


    /**
     * Create an Observable of {@link Mask}
     * @param mask object we should observe on.
     * @return Observable of {@link Mask}
     */
    private Observable<Mask> getSaveMaskObservable(Mask mask) {
        return Observable.just(mask)
                .map(new Function<Mask, Mask>() {
                    @Override
                    public Mask apply(Mask mask) throws Exception {
                        return saveMask(mask);
                    }
                });
    }

//    private void startDownloadTask(Mask mask) {
//        new DummyDownloaderThread(mask, this).start();
//    }

    /**
     * Save a dummy file to the {@link Mask} path.
     * Check first if the file exists,
     * @param mask we should save.
     * @return {@link Mask} object if it was saved correctly,
     *         else returns null.
     */
    private Mask saveMask(Mask mask) {

        if(new File(mask.path).exists())
            return mask;

        SystemClock.sleep(3000);
        String string = "Dummy File :)";
        FileOutputStream outputStream;

        // Create a file to save the image
        File file = new File(mask.path);

        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(string.getBytes());
            outputStream.close();
            return mask;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close(){
        if(compositeDisposable != null && !compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
            compositeDisposable = null;
            mLastMaskAdded = null;
            Log.d(TAG,"compositeDisposable have been disposed");
        }
    }
}
