package cn.bmob.imdemo.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * @Description: 菜谱
 * @author: cyq7on
 * @date: 2018/4/24 16:03
 * @version: V1.0
 */
public class CookBook extends BmobObject {
    public String name;
    public String step;
    public String createUserId;
    public List<String> nutrientList = new ArrayList<>();
    public String imageUrl;
    public BmobRelation collectUsers = new BmobRelation();
}
