package com.example.zhoudemo.presenter;

import com.example.zhoudemo.Myview.IView;
import com.example.zhoudemo.bean.NewsBean;
import com.example.zhoudemo.model.IModel;
import com.example.zhoudemo.model.MainModel;

/**
 * Created by FLOWER on 2017/11/19.
 */

public class MainPresenter implements IPresenter{
    IView iView;
    IModel iModel;

    public MainPresenter(IView iView) {
        this.iView = iView;
        iModel = new MainModel(this);
    }

    @Override
    public void getData(NewsBean newsBean) {
        iView.getData(newsBean);
    }

    @Override
    public void setData() {
        iModel.getData();
    }
}
