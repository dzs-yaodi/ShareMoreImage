package com.xu.my_library;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public class Tools {

    public static String IMAGE_NAME = "iv_share_";
    public static int  i =0;

    //根据网络图片url路径保存到本地
    public static final File saveImageToSdCard(Context context, String image) {
        boolean success = false;
        File file = null;
        try {
            file = createStableImageFile(context);

            Bitmap bitmap = null;
            URL url = new URL(image);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            InputStream is = null;
            is = conn.getInputStream();
            bitmap =  BitmapFactory.decodeStream(is);

            FileOutputStream outStream;

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (success) {
            return file;
        } else {
            return null;
        }
    }

    //创建本地保存路径
    public static File createStableImageFile(Context context) throws IOException {
        i++;
        String imageFileName =IMAGE_NAME + i+ ".jpg";
//        File storageDir = new File(context.getExternalCacheDir() + "shareImg");
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/shareImg/");
        Log.i("info","=======保存路径====" + storageDir.getAbsolutePath());
        if (!storageDir.exists()){
            storageDir.mkdirs();
        }
        File image = new File(storageDir, imageFileName);
        return image;
    }

    //判断是否安装了微信,QQ,QQ空间
    public static boolean isAppAvilible(Context context,String mType) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(mType)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void deletePic(File file){

        if (file.isDirectory()){

            File[] files = file.listFiles();

            for (int j = 0; j < files.length; j++) {
                File f = files[j];
                deletePic(f);
            }

//            file.delete();//如要保留文件夹，只删除文件，请注释这行
        }else{
            file.delete();
        }

    }
}
