package com.example.zhoudemo.model;

import android.util.Log;

import com.example.zhoudemo.RetrofitUtils;
import com.example.zhoudemo.bean.NewsBean;
import com.example.zhoudemo.net.Api;
import com.example.zhoudemo.net.ApiService;
import com.example.zhoudemo.presenter.IPresenter;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by FLOWER on 2017/11/19.
 */

public class MainModel implements IModel{

    IPresenter iPresenter;

    public MainModel(IPresenter iPresenter) {
        this.iPresenter = iPresenter;
    }

    @Override
    public void getData() {
        ApiService apiService = new RetrofitUtils().getApiService(Api.NEWS,ApiService.class);
        Observable<NewsBean> noparams = apiService.getNoParams();
        noparams.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        Log.i("xxx",newsBean.toString());
                        iPresenter.getData(newsBean);
                    }
                });
    }
}
