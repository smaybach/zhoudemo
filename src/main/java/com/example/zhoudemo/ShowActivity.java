package com.example.zhoudemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.zhoudemo.Myview.IView;
import com.example.zhoudemo.adapter.MyAdapter;
import com.example.zhoudemo.app.MyApp;
import com.example.zhoudemo.bean.NewsBean;
import com.example.zhoudemo.bean.User;
import com.example.zhoudemo.gen.DaoMaster;
import com.example.zhoudemo.gen.DaoSession;
import com.example.zhoudemo.gen.UserDao;
import com.example.zhoudemo.presenter.IPresenter;
import com.example.zhoudemo.presenter.MainPresenter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zlc.season.rxdownload.DownloadStatus;
import zlc.season.rxdownload.RxDownload;

public class ShowActivity extends AppCompatActivity implements IView{

    private Banner ban;
    private IPresenter iPresenter;
    private List<String> list = new ArrayList<>();
    private RecyclerView rlv;
    private ProgressBar pb;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DownloadStatus downloadStatus = (DownloadStatus) msg.obj;
            Long totalSize = downloadStatus.getTotalSize();
            Long downLoader = downloadStatus.getDownloadSize();
            String percent = downloadStatus.getPercent();

            Long t = totalSize/100;
            int tt = (int) (downLoader/t);
            if (downLoader<=totalSize){
                pb.setProgress(tt);
            }
            if(downLoader == totalSize){
                pb.setProgress(0);
            }
        }
    };
    private int formDownloadSiz;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        ban = (Banner)findViewById(R.id.ban);
        rlv = (RecyclerView)findViewById(R.id.rlv);
        pb = (ProgressBar)findViewById(R.id.pb);
        rlv.setLayoutManager(new LinearLayoutManager(this));

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApp.getContext(),"my-db",null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();

        iPresenter = new MainPresenter(this);
        iPresenter.setData();

    }
  /*  private void getBanner(){
        ban.setImageLoader(new Image());
        list = new ArrayList<>();
   *//*     list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=594559231,2167829292&fm=27&gp=0.jpg");
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1466285027,1159439966&fm=27&gp=0.jpg");
        list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3494603701,704844093&fm=27&gp=0.jpg");*//*

        ban.setImages(list);
        ban.start();
    }*/

    @Override
    public void getData(NewsBean newsBean) {
        //Toast.makeText(ShowActivity.this,"xxxx",Toast.LENGTH_SHORT).show();
        final List<NewsBean.DataBean> dataBeen = newsBean.getData();
        for (int i = 0;i<dataBeen.size();i++){
            String image_url = dataBeen.get(i).getImage_url();
            list.add(image_url);
        }
        ban.setImageLoader(new Image());
        ban.setImages(list);
        ban.start();
        MyAdapter adapter = new MyAdapter(this,dataBeen);
        rlv.setAdapter(adapter);

        adapter.setOnClickListener(new MyAdapter.OnClickListener() {
            @Override
            public void OnItemClick(int position) {
                String vedio_url = dataBeen.get(position).getVedio_url();
                String title = dataBeen.get(position).getTitle();
                Subscription subscription = RxDownload.getInstance()
                        .maxThread(4)
                        .download(vedio_url,title+".mp4",null)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<DownloadStatus>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(DownloadStatus downloadStatus) {
                                formDownloadSiz = (int)downloadStatus.getDownloadSize();
                                Message message = new Message();
                                message.obj = downloadStatus;
                                handler.sendMessageDelayed(message,100);
                            }
                        });
            }

            @Override
            public void OnLongItemClick(int position) {
                User user = new User(null,formDownloadSiz+"");
                userDao.insert(user);

                String name = user.getName();
                Log.i("xxx",name);
            }
        });
    }
}
