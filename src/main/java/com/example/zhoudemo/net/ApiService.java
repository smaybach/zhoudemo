package com.example.zhoudemo.net;

import com.example.zhoudemo.bean.NewsBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by FLOWER on 2017/11/19.
 */

public interface ApiService {
    @GET("iYXEPGn4e9c6dafce6e5cdd23287d2bb136ee7e9194d3e9?uri=vedio")
    Observable<NewsBean> getNoParams();
}
