package cn.bmob.imdemo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.Collection;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.base.BaseRecyclerAdapter;
import cn.bmob.imdemo.adapter.base.BaseRecyclerHolder;
import cn.bmob.imdemo.adapter.base.IMutlipleItem;
import cn.bmob.imdemo.bean.CookBook;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**联系人
 * 一种简洁的Adapter实现方式，可用于多种Item布局的recycleView实现，不用再写ViewHolder啦
 * @author :smile
 * @project:ContactNewAdapter
 * @date :2016-04-27-14:18
 */
public class CookBookAdapter extends BaseRecyclerAdapter<CookBook> {


    public CookBookAdapter(Context context, IMutlipleItem<CookBook> items, Collection<CookBook> datas) {
        super(context,items,datas);
    }

    @Override
    public void bindView(BaseRecyclerHolder holder, final CookBook cookBook, int position) {
        final BmobUser u = BmobUser.getCurrentUser();
        StringBuilder stringBuilder = new StringBuilder("菜名：").append(cookBook.name).append("\n")
                .append("步骤：").append(cookBook.step).append("\n");
        int size = cookBook.nutrientList.size();
        if(size > 0){
            stringBuilder.append("营养成分：").append("\n");
        }
        for (int i = 0; i < size; i++) {
            stringBuilder.append(cookBook.nutrientList.get(i)).append("\n");
        }

        holder.setText(R.id.tv_info,stringBuilder.toString());
        holder.setImageView(cookBook.imageUrl,R.mipmap.ic_launcher,R.id.iv);
        final TextView collect = holder.getView(R.id.tvCollect);
        final BmobRelation collectUsers = cookBook.collectUsers;
        boolean contains = collectUsers.getObjects().contains(u);
        Logger.d(contains + "");
        if(contains){
            collect.setText("已收藏");
        }else {
            collect.setText("未收藏");
            collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    collectUsers.add(u);
                    cookBook.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                toast("收藏成功");
                                collect.setText("已收藏");
                            }else {
                                toast("收藏失败");
                                Logger.e(e);
                            }
                        }
                    });
                }
            });
        }
    }

}
