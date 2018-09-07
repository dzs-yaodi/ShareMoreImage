package com.xu.sharemoreimage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.xu.my_library.ShareManager;
import com.xu.my_library.Tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_share_person;
    private Button btn_share_circle;
    private Button btn_share_qq;
    private Button btn_share_qzone;
    private Button btn_file;
//    private Button btn_camera;
    private Context mContext;
    private String[] stringItem = {"https://gd2.alicdn.com/imgextra/i1/2259324182/TB2sdjGm0BopuFjSZPcXXc9EpXa_!!2259324182.jpg",
    "http://img4.tbcdn.cn/tfscom/i1/2259324182/TB2ISF_hKtTMeFjSZFOXXaTiVXa_!!2259324182.jpg",
    "http://img2.tbcdn.cn/tfscom/i1/2259324182/TB2NAMmm00opuFjSZFxXXaDNVXa_!!2259324182.jpg"};
    private ShareManager shareManager;
    private List<String> stringList = new ArrayList<>();
    private ArrayList<String> fileList = new ArrayList<>();
    private static final int REQUEST_CAMERA_CODE = 0;
    private ImageCaptureManager captureManager; // 相机拍照处理类
    private boolean isShareImg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        btn_share_person = (Button) findViewById(R.id.btn_share_person);
        btn_share_circle = (Button) findViewById(R.id.btn_share_circle);
        btn_share_qq = (Button) findViewById(R.id.btn_share_qq);
        btn_share_qzone = (Button) findViewById(R.id.btn_share_qzone);
        btn_file = (Button) findViewById(R.id.btn_file);
//        btn_camera = (Button) findViewById(R.id.btn_camera);

        btn_share_person.setOnClickListener(this);
        btn_share_circle.setOnClickListener(this);
        btn_share_qq.setOnClickListener(this);
        btn_share_qzone.setOnClickListener(this);
        btn_file.setOnClickListener(this);
//        btn_camera.setOnClickListener(this);

        for (int i = 0; i < stringItem.length; i++) {
            stringList.add(stringItem[i]);
        }
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){

            case R.id.btn_share_person://微信好友
                Toast.makeText(MainActivity.this, "请稍后", Toast.LENGTH_SHORT).show();

                share(0,"","wchat");
                break;
            case R.id.btn_share_circle://微信朋友圈
                Toast.makeText(MainActivity.this, "请稍后", Toast.LENGTH_SHORT).show();

                share(1,"微信分享朋友圈的说明","wchat");
                break;

            case R.id.btn_share_qzone://QQ空间
                Toast.makeText(MainActivity.this, "请稍后", Toast.LENGTH_SHORT).show();
                share(1,"QQ空间分享的说明","qq_zone");
                break;

            case R.id.btn_share_qq://QQ好友
                Toast.makeText(MainActivity.this, "请稍后", Toast.LENGTH_SHORT).show();
                share(0,"QQ好友分享的说明","qq");
                break;
            case R.id.btn_file:
                PhotoPickerIntent intent1 = new PhotoPickerIntent(MainActivity.this);
                intent1.setSelectModel(SelectModel.MULTI);
                intent1.setShowCarema(true); // 是否显示拍照
                intent1.setMaxTotal(9); // 最多选择照片数量，默认为9
                intent1.setSelectedPaths(fileList); // 已选中的照片地址， 用于回显选中状态
                startActivityForResult(intent1, REQUEST_CAMERA_CODE);
                break;
//            case R.id.btn_camera:
//                try {
//                    if(captureManager == null){
//                        captureManager = new ImageCaptureManager(MainActivity.this);
//                    }
//                    Intent intent3 = captureManager.dispatchTakePictureIntent();
//                    startActivityForResult(intent3, ImageCaptureManager.REQUEST_TAKE_PHOTO);
//                } catch (IOException e) {
//                    Toast.makeText(MainActivity.this, com.foamtrace.photopicker.R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//                break;
        }

    }

    private void share(int i, String s,String mType) {
        isShareImg = true;
        shareManager = new ShareManager(MainActivity.this);
        shareManager.setShareImage(i,stringList,s,mType);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case REQUEST_CAMERA_CODE:
                fileList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                if (fileList.size() > 0){
                    shareManager = new ShareManager(MainActivity.this);
                    shareManager.setShareImage(0,fileList,"","qq");
                }
                break;

//            // 调用相机拍照
//            case ImageCaptureManager.REQUEST_TAKE_PHOTO:
//                if(captureManager.getCurrentPhotoPath() != null) {
//                    captureManager.galleryAddPic();
//
//                    ArrayList<String> paths = new ArrayList<>();
//                    paths.add(captureManager.getCurrentPhotoPath());
//                }
//                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isShareImg) {
            isShareImg = false;
            File file = new File(Environment.getExternalStorageDirectory() + "/shareImg/");
            Tools.deletePic(file);
        }
    }
}
