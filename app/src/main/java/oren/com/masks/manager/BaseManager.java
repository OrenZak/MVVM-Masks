package oren.com.masks.manager;


import java.util.ArrayList;
import java.util.List;


public abstract class BaseManager<T> {

    protected List<T>  mList = new ArrayList<>(1);

    public void add(T object){
        mList.add(object);
    }

    public void remove(T object){
        mList.remove(object);
    }

}
