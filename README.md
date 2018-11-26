# ShareMoreImage
微信 QQ 多图片分享


微信sdk本身是没有提供多图片分享的，所以如果想要实现微信的多图片分享就只能我们自己调用系统原始的分享功能去实现。
 
 
实现起来呢其实也很简单，就是调用系统分享，把分享的图片存到Uri的arrayList数组当中，然后 调用一下代码就可以实现了。


intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
startActivity(intent);

 
在这儿注意调用系统本身的分享功能首先要判断手机里是否有安装微信，


其次就是要把图片存在本地才能实现分享。再有就是android N系统以后分享路径为file：//  的异常。


 在application中添加
 
 
   //解决android N（>=24）系统以上分享 路径为file://时的 android.os.FileUriExposedException异常
   
   
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        
        
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            
            
            StrictMode.setVmPolicy(builder.build());
            
            
        }

 
 使用方法：
 
 
 复制my_library中的ShareManager 和Tools 工具类。
 
 
 在需要分享的地方初始化ShareManager ，再调用setShareImage()方法，传入参数即可，有在ShareManager中做程序的安装判断，微信，QQ，QQ空间没有安装会给出提示。Tools工具类是保存图片到本地。这儿有对图片做命名的限制，不会无限制的存入图片。
 

区分微信和QQ


在传入的参数中新增了mType标志，QQ分享传入 qq,QQ空间传入qq_zone ,微信统一传入 wchat。


因为最近有朋友问到了有关QQ的分享，所以新加了QQ和QQ空间的分享，不过分享QQ空间不会调用QQ中的空间，会直接去调用QQ空间App。


因为都是做本地调用分享，所以还是把图片保存到本地 再分享，这样唤起分享的时候是有一点延迟的。
      

由于新版微信 和 QQ有做限制，没有再带入文本数据，所以文本传空即可。

新增了本地分享，传入图片路径的list即可。

修改图片缓存地址，每次分享成功返回页面，在onResume中调用deletePic() 方法删除图片缓存

![微信6.7.3版本以后微信屏蔽了微信朋友圈的多图分享，好友多图分享还是能用的](https://github.com/XW837156540/ShareMoreImage/blob/master/app/src/main/res/mipmap-hdpi/iv_test.jpg)
