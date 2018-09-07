package com.xu.my_library;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */
public class ShareManager {

    private Context mContext;
    private List<File> files = new ArrayList<>();

    public ShareManager(Context mContext) {
        this.mContext = mContext;
    }

    public void setShareImage(final int flag, final List<String> stringList,final String Kdescription,final String mType){

        if (mType.equals("qq") && !Tools.isAppAvilible(mContext,"com.tencent.mobileqq")){

            Toast.makeText(mContext, "您还没有安装QQ", Toast.LENGTH_SHORT).show();
            return;
        }else if (mType.equals("wchat") && !Tools.isAppAvilible(mContext,"com.tencent.mm")){

            Toast.makeText(mContext, "您还没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }else if (mType.equals("qq_zone") && !Tools.isAppAvilible(mContext,"com.qzone")){
            Toast.makeText(mContext, "您还没有安装QQ空间", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < stringList.size(); i++) {
                    File file = null;
                    if (stringList.get(i).contains("http")) {
                        file  = Tools.saveImageToSdCard(mContext, stringList.get(i));
                    }else{
                        file = new File(stringList.get(i));
                    }
                    files.add(file);
                }
                Intent intent = new Intent();
                ComponentName comp;
                if (mType.contains("qq")){
                    if (flag == 0) {
                        comp = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
                    }else{
                        comp = new ComponentName("com.qzone", "com.qzonex.module.operation.ui.QZonePublishMoodActivity");
                    }
                }else{
                    if (flag == 0) {
                        comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                    } else {
                        comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
//                           intent.putExtra("Kdescription", Kdescription);
                    }
                }
                intent.setComponent(comp);
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("image/*");
                ArrayList<Uri> imageUris = new ArrayList<Uri>();
                for (File f : files) {
                    imageUris.add(Uri.fromFile(f));
                }
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                mContext.startActivity(intent);

            }
        }).start();
    }
}
