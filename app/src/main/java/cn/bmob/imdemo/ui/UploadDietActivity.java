package cn.bmob.imdemo.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviActivity;

public class UploadDietActivity extends ParentWithNaviActivity {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_breakfast)
    EditText etBreakfast;
    @Bind(R.id.et_launch)
    EditText etLaunch;
    @Bind(R.id.et_dinner)
    EditText etDinner;
    @Bind(R.id.et_nutrient)
    TextView etNutrient;
    @Bind(R.id.btn_upload)
    Button btnUpload;

    @Override
    protected String title() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_diet);
        ButterKnife.bind(this);
    }
}
