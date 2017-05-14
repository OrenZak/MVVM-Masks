package oren.com.masks.utill;


import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

public class DataBindingAdapter {

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    @BindingAdapter("android:background")
    public static void setBackground(View view, int resource){
        view.setBackgroundResource(resource);
    }

}
