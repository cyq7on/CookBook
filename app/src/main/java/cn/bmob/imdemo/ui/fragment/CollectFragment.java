package cn.bmob.imdemo.ui.fragment;

import com.orhanobut.logger.Logger;

import java.util.List;

import cn.bmob.imdemo.bean.CookBook;
import cn.bmob.imdemo.bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CollectFragment extends RecommendFragment {
    @Override
    protected String title() {
        return "收藏";
    }

    @Override
    protected void query() {
        BmobQuery<CookBook> query = new BmobQuery<>();
        BmobQuery<User> innerQuery = new BmobQuery<>();
//        innerQuery.addWhereExists("image", true);
        query.addWhereMatchesQuery("collectUsers", "_User", innerQuery);
        query.order("-updatedAt");
        query.findObjects(new FindListener<CookBook>() {
            @Override
            public void done(List<CookBook> list, BmobException e) {
                swRefresh.setRefreshing(false);
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        adapter.bindDatas(list);
                    } else {
                        toast("暂无信息");
                    }
                } else {
                    toast("获取信息出错");
                    Logger.e(e);
                }
            }
        });
    }
}
