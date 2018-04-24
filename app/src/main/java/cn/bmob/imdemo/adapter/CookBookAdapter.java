package cn.bmob.imdemo.adapter;

import android.content.Context;

import java.util.Collection;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.base.BaseRecyclerAdapter;
import cn.bmob.imdemo.adapter.base.BaseRecyclerHolder;
import cn.bmob.imdemo.adapter.base.IMutlipleItem;
import cn.bmob.imdemo.bean.CookBook;

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
    public void bindView(BaseRecyclerHolder holder, CookBook cookBook, int position) {
        StringBuilder stringBuilder = new StringBuilder(cookBook.step).append("\n");
        int size = cookBook.nutrientList.size();
        if(size > 0){
            stringBuilder.append("营养成分：").append("\n");
        }
        for (int i = 0; i < size; i++) {
            stringBuilder.append(cookBook.nutrientList.get(i)).append("\n");
        }

        holder.setText(R.id.tv_info,stringBuilder.toString());
        holder.setImageView(cookBook.imageUrl,R.mipmap.ic_launcher,R.id.iv);
    }

}
