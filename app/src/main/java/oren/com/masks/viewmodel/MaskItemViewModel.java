package oren.com.masks.viewmodel;

import android.databinding.ObservableInt;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import oren.com.masks.callback.MaskSelected;
import oren.com.masks.manager.DummyDownloaderManger;
import oren.com.masks.model.Event;
import oren.com.masks.model.Mask;
import oren.com.masks.utill.Constants;

public class MaskItemViewModel {

    private Mask mMask;
    private MaskSelected mMaskSelected;

    public ObservableInt maskFrame;

    public MaskItemViewModel(Mask mask, MaskSelected maskSelected) {
        this.mMask = mask;
        this.maskFrame = new ObservableInt(0);
        this.mMaskSelected = maskSelected;
    }

    public int getImageRes() {
        return this.mMask.imageRes;
    }

    public void getMask(View view) {
        EventBus.getDefault().post(new Event(Constants.SHOW_PROGRESS));
        if (mMaskSelected != null)
            mMaskSelected.onMaskSelected();

        DummyDownloaderManger.getInstance().add(mMask);
    }

    public void setMask(Mask mask) {
        this.mMask = mask;
    }

}
