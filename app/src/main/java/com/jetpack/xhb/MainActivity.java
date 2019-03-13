package com.jetpack.xhb;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.paging.PositionalDataSource;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jetpack.xhb.jetpack.AppDataBase;
import com.jetpack.xhb.jetpack.DataBean;
import com.jetpack.xhb.jetpack.MainPresenter;
import com.jetpack.xhb.jetpack.UserAdapter;
import com.jetpack.xhb.jetpack.UserRoom;
import com.jetpack.xhb.jetpack.UserRoomDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Lifecycle
 * ViewModel
 * LiveData
 * Room
 * Paging
 */
public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();

    private MainPresenter observer;
    private UserRoomDao userRoomDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        observer = new MainPresenter();
        getLifecycle().addObserver(observer);

        AppDataBase db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "jecpack_db").allowMainThreadQueries().build();

        userRoomDao = db.userRoomDao();

        userRoomDao.insert(new UserRoom(System.currentTimeMillis(), "User002", 16));
        List<UserRoom> userRooms = userRoomDao.getUserRooms();
        Log.e("UserRoom", userRooms.size() + "");
        if (userRooms.size() > 0) {
            Log.e("UserRoom", userRooms.get(0).name + userRooms.get(0).age);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        observer.userModel.getName().observe(MainActivity.this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
                ((TextView) findViewById(R.id.ac_main_tv)).setText(stringFromJNI());
//            }
//        });

        findViewById(R.id.ac_main_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observer.userModel.getName().setValue("UserModel" + System.currentTimeMillis()/10000);
            }
        });

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(10)                         //配置分页加载的数量
                .setEnablePlaceholders(false)     //配置是否启动PlaceHolders
                .setInitialLoadSizeHint(10)              //初始化加载的数量
                .build();

        RecyclerView recyclerView = findViewById(R.id.ac_main_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final UserAdapter adapter = new UserAdapter(this, mDiffCallback);
        recyclerView.setAdapter(adapter);

        LiveData<PagedList<DataBean>> liveData = new LivePagedListBuilder(new MyDataSourceFactory(), config)
//                .setBoundaryCallback(null)
//                .setFetchExecutor(null)
                .build();
        liveData.observe(this, new Observer<PagedList<DataBean>>() {
            @Override
            public void onChanged(@Nullable PagedList<DataBean> dataBeans) {
                adapter.submitList(dataBeans);
            }
        });
    }


    private DiffUtil.ItemCallback<DataBean> mDiffCallback = new DiffUtil.ItemCallback<DataBean>() {
        @Override
        public boolean areItemsTheSame(@NonNull DataBean oldItem, @NonNull DataBean newItem) {
            Log.d("DiffCallback", "areItemsTheSame");
            return oldItem.name.equals(newItem.name);
        }

        @Override
        public boolean areContentsTheSame(@NonNull DataBean oldItem, @NonNull DataBean newItem) {
            Log.d("DiffCallback", "areContentsTheSame");
            return (oldItem == newItem);
        }
    };

    private class MyDataSourceFactory extends DataSource.Factory<Integer, DataBean> {
        @Override
        public DataSource<Integer, DataBean> create() {
            return new MyDataSource();
        }
    }

    private class MyDataSource extends PositionalDataSource<DataBean> {

        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<DataBean> callback) {
            callback.onResult(loadData(0, 10), 0, 10);
        }

        @Override
        public void loadRange(@NonNull LoadRangeParams params
                , @NonNull LoadRangeCallback<DataBean> callback) {
            callback.onResult(loadData(params.startPosition, 10));
        }

        /**
         * 假设这里需要做一些后台线程的数据加载任务。
         *
         * @param startPosition
         * @param count
         * @return
         */
        private List<DataBean> loadData(int startPosition, int count) {
            List<DataBean> list = new ArrayList<>();

            for (int i = startPosition; i < startPosition+count; i++) {
                DataBean data = new DataBean();
                data.age = startPosition + i;
                data.name = "测试的内容=" + i;
                list.add(data);
            }

            return list;
        }

    }
}
