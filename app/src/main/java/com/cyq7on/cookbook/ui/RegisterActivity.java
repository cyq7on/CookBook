package com.cyq7on.cookbook.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.cyq7on.cookbook.R;
import com.cyq7on.cookbook.base.ParentWithNaviActivity;
import com.cyq7on.cookbook.event.FinishEvent;
import com.cyq7on.cookbook.model.BaseModel;
import com.cyq7on.cookbook.model.UserModel;
import com.cyq7on.cookbook.util.SPUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * 注册界面
 *
 * @author :smile
 * @project:RegisterActivity
 * @date :2016-01-15-18:23
 */
public class RegisterActivity extends ParentWithNaviActivity {

    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.btn_register)
    Button btn_register;

    @Bind(R.id.et_password_again)
    EditText et_password_again;
    @Bind(R.id.rgSex)
    RadioGroup rgSex;
    private byte sex = 1;
    private String[] items = {"能量", "蛋白质", "维生素A","维生素B","维生素C","维生素D","维生素E","钙铁锌硒"};


    @Override
    protected String title() {
        return "注册";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initNaviView();
    }

    @Override
    protected void initView() {
        super.initView();
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //这一组i从3开始
                sex = (byte) (i - 3);
                Logger.d("rgSex: " + i);
            }
        });
    }

    /*@OnClick(R.id.et_dep)
    public void onClick(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("营养成分")
                .setNegativeButton("取消", null).setPositiveButton("确定", null)
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Logger.d(which + "\n" + isChecked);
                    }
                }).create();
        dialog.show();
    }*/

    @OnClick(R.id.btn_register)
    public void onRegisterClick(View view) {
        LogInListener logInListener = new LogInListener() {
            @Override
            public void done(Object o, BmobException e) {
                if (e == null) {
                    toast("注册成功");
                    String user = et_username.getText().toString();
                    String pwd = et_password.getText().toString();
                    SPUtil.putAndApply(getApplicationContext(), "pwd", pwd);
                    UserModel.getInstance().login(user, pwd, new LogInListener() {
                        @Override
                        public void done(Object o, BmobException e) {
                            if (e == null) {
                                EventBus.getDefault().post(new FinishEvent());
                                startActivity(MainActivity.class, null, true);
                            } else {
                                toast("登录失败");
                                finish();
                            }
                        }
                    });

                } else {
                    if (e.getErrorCode() == BaseModel.CODE_NOT_EQUAL) {
                        et_password_again.setText("");
                    }
                    toast(e.getMessage() + "(" + e.getErrorCode() + ")");
                }
            }
        };
        UserModel.getInstance().register(et_username.getText().toString(),
                et_password.getText().toString(), et_password_again.getText().toString(),logInListener);
    }

}
