package com.xu.sharemoreimage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xu.my_library.ShareManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_share_person;
    private Button btn_share_circle;
    private Context mContext;
    private List<File> files = new ArrayList<>();
    private String[] stringItem = {"https://gd2.alicdn.com/imgextra/i1/2259324182/TB2sdjGm0BopuFjSZPcXXc9EpXa_!!2259324182.jpg",
    "http://img4.tbcdn.cn/tfscom/i1/2259324182/TB2ISF_hKtTMeFjSZFOXXaTiVXa_!!2259324182.jpg",
    "http://img2.tbcdn.cn/tfscom/i1/2259324182/TB2NAMmm00opuFjSZFxXXaDNVXa_!!2259324182.jpg"};
    private ShareManager shareManager;
    private List<String> stringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        btn_share_person = (Button) findViewById(R.id.btn_share_person);
        btn_share_circle = (Button) findViewById(R.id.btn_share_circle);

        btn_share_person.setOnClickListener(this);
        btn_share_circle.setOnClickListener(this);

        for (int i = 0; i < stringItem.length; i++) {
            stringList.add(stringItem[i]);
        }
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){

            case R.id.btn_share_person:
                Toast.makeText(MainActivity.this, "请稍后", Toast.LENGTH_SHORT).show();

                share(0,"");
                break;

            case R.id.btn_share_circle:
                Toast.makeText(MainActivity.this, "请稍后", Toast.LENGTH_SHORT).show();

                share(1,"微信分享朋友圈的说明");
                break;
        }

    }

    private void share(int i, String s) {

        shareManager = new ShareManager(MainActivity.this);
        shareManager.setShareImage(i,stringList,s);

    }

    /**
     *
     * @param flag  0分享给微信好友  1分享到朋友圈
     */
    private void shareImage(final int flag) {

        //判断是否安装微信，如果没有安装微信 又没有判断就直达微信分享是会挂掉的
       if (!Tools.isWeixinAvilible(mContext)){
           Toast.makeText(MainActivity.this, "您还没有安装微信", Toast.LENGTH_SHORT).show();

       }else{

           new Thread(new Runnable() {
               @Override
               public void run() {
                   //这一步一定要clear,不然分享了朋友圈马上分享好友图片就会翻倍
                   files.clear();

                   try {

                       for (int i = 0; i < stringItem.length; i++) {
                           File file = Tools.saveImageToSdCard(mContext, stringItem[i]);
                           files.add(file);
                       }

                       Intent intent = new Intent();
                       ComponentName comp;

                       if (flag == 0) {
                           comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                       } else {
                           comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                           intent.putExtra("Kdescription", "分享朋友圈的图片说明");
                       }
                       intent.setComponent(comp);
                       intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                       intent.setType("image/*");

                       ArrayList<Uri> imageUris = new ArrayList<Uri>();
                       for (File f : files) {
                           imageUris.add(Uri.fromFile(f));
                       }

                       intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                       startActivity(intent);
                   }catch (Exception e){
                       e.printStackTrace();
                   }
               }
           }).start();

       }


    }
}
