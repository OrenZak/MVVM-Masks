package oren.com.masks.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import oren.com.masks.R;
import oren.com.masks.callback.MaskSelected;
import oren.com.masks.databinding.MaskItemBinding;
import oren.com.masks.model.Mask;
import oren.com.masks.viewmodel.MaskItemViewModel;

public class MasksRecyclerAdapter extends RecyclerView.Adapter<MasksRecyclerAdapter.MasksViewHolder> {

    private List<Mask> mMasks;
    private MasksViewHolder mLastSelected;

    public MasksRecyclerAdapter(List<Mask> masks) {
        this.mMasks = masks;
    }

    @Override
    public MasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MaskItemBinding maskItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.mask_item,
                        parent, false);
        return new MasksViewHolder(maskItemBinding, this);
    }

    @Override
    public void onBindViewHolder(MasksViewHolder holder, int position) {
        holder.bindMask(mMasks.get(position));
    }

    @Override
    public int getItemCount() {
        return mMasks.size();
    }

    public static class MasksViewHolder extends RecyclerView.ViewHolder implements MaskSelected {
        private MaskItemBinding mMaskItemBinding;
        private MasksRecyclerAdapter mAdapter;

        public MasksViewHolder(MaskItemBinding maskItemBinding, MasksRecyclerAdapter adapter) {
            super(maskItemBinding.maskFrame);
            this.mMaskItemBinding = maskItemBinding;
            this.mAdapter = adapter;
        }

        void bindMask(final Mask mask) {
            if (mMaskItemBinding.getMaskItemViewModel() == null) {
                mMaskItemBinding.setMaskItemViewModel(new MaskItemViewModel(mask, this));
            } else {
                mMaskItemBinding.getMaskItemViewModel().setMask(mask);
            }
        }

        @Override
        public void onMaskSelected() {
            if (mAdapter.mLastSelected != null)
                mAdapter.mLastSelected.mMaskItemBinding.getMaskItemViewModel().maskFrame.set(0);
            mAdapter.mLastSelected = this;
            mMaskItemBinding.getMaskItemViewModel().maskFrame.set(R.drawable.stroke_frame);
        }
    }
}
