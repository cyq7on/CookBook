package com.cyq7on.cookbook.adapter;

import android.content.Context;

import com.cyq7on.cookbook.adapter.base.BaseRecyclerAdapter;
import com.cyq7on.cookbook.adapter.base.BaseRecyclerHolder;
import com.cyq7on.cookbook.adapter.base.IMutlipleItem;
import com.cyq7on.cookbook.bean.User;

import java.util.Collection;

/**
 * Created by cyq7on on 18-3-22.
 */

public class UserAdapter extends BaseRecyclerAdapter<User> {
    /**
     * 支持一种或多种Item布局
     *
     * @param context
     * @param items
     * @param datas
     */
    public UserAdapter(Context context, IMutlipleItem<User> items, Collection<User> datas) {
        super(context, items, datas);
    }

    @Override
    public void bindView(BaseRecyclerHolder holder, User user, int position) {
        holder.setImageView(user == null ? null : user.getAvatar(), com.cyq7on.cookbook.R.mipmap.head, com.cyq7on.cookbook.R.id.iv_recent_avatar);
        holder.setText(com.cyq7on.cookbook.R.id.tv_recent_name,user==null?"未知":user.getUsername());
    }
}
