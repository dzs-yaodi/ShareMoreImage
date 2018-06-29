package com.xu.sharemoreimage;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xu.my_library.ShareManager;
import com.xu.my_library.Tools;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_share_person;
    private Button btn_share_circle;
    private Button btn_share_qq;
    private Button btn_share_qzone;
    private Context mContext;
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
        btn_share_qq = (Button) findViewById(R.id.btn_share_qq);
        btn_share_qzone = (Button) findViewById(R.id.btn_share_qzone);

        btn_share_person.setOnClickListener(this);
        btn_share_circle.setOnClickListener(this);
        btn_share_qq.setOnClickListener(this);
        btn_share_qzone.setOnClickListener(this);

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
        }

    }

    private void share(int i, String s,String mType) {

        shareManager = new ShareManager(MainActivity.this);
        shareManager.setShareImage(i,stringList,s,mType);

    }
}
