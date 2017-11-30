package com.xu.my_library;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

    public void setShareImage(final int flag, final List<String> stringList,final String Kdescription){

        if (!Tools.isWeixinAvilible(mContext)){

            Toast.makeText(mContext, "您还没有安装微信", Toast.LENGTH_SHORT).show();

        }else{

            new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int i = 0; i < stringList.size(); i++) {
                        File file = Tools.saveImageToSdCard(mContext, stringList.get(i));
                        files.add(file);
                    }


                    Intent intent = new Intent();
                    ComponentName comp;

                    if (flag == 0) {
                        comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                    } else {
                        comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                        intent.putExtra("Kdescription", Kdescription);
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

}
