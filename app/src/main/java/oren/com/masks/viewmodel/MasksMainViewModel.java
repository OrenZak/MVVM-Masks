package oren.com.masks.viewmodel;

import android.databinding.ObservableInt;
import android.view.View;

import java.util.ArrayList;

import oren.com.masks.R;
import oren.com.masks.model.Mask;

public class MasksMainViewModel {

    private ArrayList<Mask> mMasks = new ArrayList<>(8);

    public ObservableInt maskRes;
    public ObservableInt maskProgress;

    public MasksMainViewModel() {
        this.maskRes = new ObservableInt();
        this.maskProgress = new ObservableInt(View.GONE);
        createMaskListFromRes();

    }

    public void showProgress(){
        maskProgress.set(View.VISIBLE);
        maskRes.set(0);
    }

    public void hideProgress(){
        maskProgress.set(View.GONE);
    }

    public void updateMaskRes(int resId){
        maskRes.set(resId);
        hideProgress();
    }

    private void createMaskListFromRes() {
        mMasks.add(new Mask(R.drawable.mask_1));
        mMasks.add(new Mask(R.drawable.mask_2));
        mMasks.add(new Mask(R.drawable.mask_3));
        mMasks.add(new Mask(R.drawable.mask_4));
        mMasks.add(new Mask(R.drawable.mask_5));
        mMasks.add(new Mask(R.drawable.mask_6));
        mMasks.add(new Mask(R.drawable.mask_7));
        mMasks.add(new Mask(R.drawable.mask_8));

    }

    public ArrayList<Mask> getMasks() {
        return mMasks;
    }
}
