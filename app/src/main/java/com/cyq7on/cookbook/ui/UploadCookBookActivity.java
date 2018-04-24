package com.cyq7on.cookbook.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyq7on.cookbook.R;
import com.cyq7on.cookbook.base.ParentWithNaviActivity;
import com.cyq7on.cookbook.bean.CookBook;
import com.orhanobut.logger.Logger;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UploadCookBookActivity extends ParentWithNaviActivity {

    private static final int REQUEST_CODE = 0x1001;
    private CookBook cookBook = new CookBook();
    @Bind(R.id.et_step)
    EditText etStep;
    @Bind(R.id.et_image)
    TextView etImage;
    @Bind(R.id.et_nutrient)
    TextView etNutrient;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.btn_upload)
    Button btnUpload;
    private String[] items = {"能量", "蛋白质", "维生素A", "维生素B", "维生素C", "维生素D", "维生素E", "钙铁锌硒"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_cook_book);
        ButterKnife.bind(this);
        initNaviView();
        etImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });
        if(user.getRole() == 1){
            etNutrient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });
        }else {
            etNutrient.setVisibility(View.GONE);
        }

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String step = etStep.getText().toString();
                if(TextUtils.isEmpty(step)){
                    toast("请填写步骤");
                    return;
                }
                if(cookBook.image != null){
                    cookBook.image.upload(getApplicationContext(), new UploadFileListener() {
                        @Override
                        public void onSuccess() {
                            Logger.d(cookBook.image.getFileUrl(getApplicationContext()));
                            cookBook.creatUserId = user.getObjectId();
                            cookBook.step = step;
                            cookBook.save(getApplicationContext(), new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    toast("上传成功");
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    toast("上传失败");
                                    Logger.d(i + "\n" + s);
                                }
                            });
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            toast("上传失败");
                            Logger.d(i + "\n" + s);
                        }
                    });
                }

            }
        });
    }

    private void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("营养成分")
                .setNegativeButton("取消", null).setPositiveButton("确定", null)
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Logger.d(which + "\n" + isChecked);
                        if(isChecked){
                            cookBook.nutrientList.add(items[which]);
                        }else {
                            cookBook.nutrientList.remove(items[which]);
                        }
                    }
                }).create();
        dialog.show();
    }

    @Override
    protected String title() {
        return "上传菜单";
    }

    private void selectPhoto() {
        int color = getResources().getColor(R.color.colorPrimaryDark);
        // 自由配置选项
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(false)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLUE)
                // 使用沉浸式状态栏
                .statusBarColor(color)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(color)
                // 裁剪大小。needCrop为true的时候配置
//                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                // 最大选择图片数量，默认9
                .maxNum(9)
                .build();

        // 跳转到图片选择器
        ISNav.getInstance().toListActivity(this, config, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            for (String path : pathList) {
                Logger.d(path + "\n");
            }
            if(pathList.size() > 0){
                File file = new File(pathList.get(0));
                cookBook.image = new BmobFile(file);
                image.setImageURI(Uri.fromFile(file));
            }
        }
    }
}
