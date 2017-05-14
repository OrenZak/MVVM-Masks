package oren.com.masks.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import oren.com.masks.R;
import oren.com.masks.databinding.MasksActivityBinding;
import oren.com.masks.manager.DummyDownloaderManger;
import oren.com.masks.model.Event;
import oren.com.masks.model.Mask;
import oren.com.masks.utill.Constants;
import oren.com.masks.viewmodel.MasksMainViewModel;

public class MasksActivity extends AppCompatActivity {

    private MasksActivityBinding mMasksActivityBinding;
    private MasksMainViewModel mMasksMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        setupListPeopleView(mMasksActivityBinding.masksRecycler);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        mMasksMainViewModel.hideProgress();
        DummyDownloaderManger.getInstance().close();
    }

    private void initDataBinding() {
        mMasksActivityBinding = DataBindingUtil.setContentView(this, R.layout.masks_activity);
        mMasksMainViewModel = new MasksMainViewModel();
        mMasksActivityBinding.setMasksMainViewModel(mMasksMainViewModel);
    }

    private void setupListPeopleView(RecyclerView maskRecycler) {
        MasksRecyclerAdapter adapter = new MasksRecyclerAdapter(mMasksMainViewModel.getMasks());
        maskRecycler.setAdapter(adapter);
        maskRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onShowMask(Mask mask){
        if(mask != null) {
            mMasksMainViewModel.updateMaskRes(mask.imageRes);
        }
        else{
            Toast.makeText(getBaseContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event){
        switch (event.type){
            case Constants.SHOW_PROGRESS:
                mMasksMainViewModel.showProgress();
                break;
        }
    }

}
