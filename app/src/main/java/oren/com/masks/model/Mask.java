package oren.com.masks.model;

import java.io.File;

import oren.com.masks.App;

public class Mask {

    public int imageRes;

    public String path;

    public Mask(int imageRes) {
        this.imageRes = imageRes;
        this.path = App.INTERNAL_IMAGES_PATH + File.separatorChar + imageRes + ".png";
    }

    @Override
    public boolean equals(Object obj) {
        Mask mask2 = (Mask)obj;
        return this.imageRes == mask2.imageRes;
    }
}
