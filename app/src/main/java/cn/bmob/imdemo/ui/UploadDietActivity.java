package cn.bmob.imdemo.ui;

import android.os.Bundle;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviActivity;

public class UploadDietActivity extends ParentWithNaviActivity {

    @Override
    protected String title() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_diet);
    }
}
