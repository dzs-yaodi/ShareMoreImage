# ShareMoreImage
微信多图片分享
 
微信sdk本身是没有提供多图片分享的，所以如果想要实现微信的多图片分享就只能我们自己调用系统原始的分享功能去实现。
 
实现起来呢其实也很简单，就是调用系统分享，把分享的图片存到Uri的arrayList数组当中，
然后 调用 
intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
startActivity(intent);
就可以实现了。
 
在这儿注意调用系统本身的分享功能首先要判断手机里是否有安装微信，
其次就是要把图片存在本地才能实现分享。再有就是android N系统以后分享路径为file：//  的异常。
 在application中添加
   //解决android N（>=24）系统以上分享 路径为file://时的 android.os.FileUriExposedException异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

 
使用的时候可以直接复制MainActivity中的shareImage方法，然后导入Tools工具类，保存图片到本地。这儿有对图片做命名的限制，
不会无限制的存入图片。
 
也可以直接复制my_libraay或导入lib中的jar，初始化ShareManager，传入分享参数
      
