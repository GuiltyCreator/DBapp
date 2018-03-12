# DBapp
豆逼app


dbapp使用的依赖库：

```
    compile 'com.squareup.okhttp3:okhttp:3.4.1'
    compile 'com.squareup.okio:okio:1.7.0'
    
    compile 'com.google.code.gson:gson:2.8.0'
    compile 'com.android.support:design:26.1.0'
    compile 'de.hdodenhof:circleimageview:2.1.0'
    compile 'com.android.support:recyclerview-v7:26.1.0'
    compile 'com.github.bumptech.glide:glide:3.7.0'
    compile 'com.yalantis:ucrop:1.5.0'
    
    compile group: 'com.qiniu', name: 'happy-dns', version: '0.2.3'
    compile 'com.qiniu:qiniu-android-sdk:7.3.+'
    compile 'com.qiniu:qiniu-java-sdk:7.1.+'
    compile 'com.loopj.android:android-async-http:1.4.9'
```
okhttp3,七牛云与其附属依赖库，，圆形图像，uCrop图片裁剪，gson，Design Support库,Glide

[circleimageview的参考文档](https://github.com/hdodenhof/CircleImageView)

[七牛云的参考文档](https://developer.qiniu.com/kodo/sdk/1236/android)

[uCrop图片裁剪](https://github.com/Yalantis/uCrop)

[Glide](https://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en)


[数据接口文档](https://github.com/jay68/bihu_web/wiki/%E9%80%BC%E4%B9%8EAPI%E6%96%87%E6%A1%A3)

项目用MVP开发模式，将view层与model层分开，控件和数据分开处理，中间用Presenter层进行M层与V层的交互。

关于控件：My_EdiText:

自定义控件，其布局在**R.layout.input_box_layout**，写了一个工具类，给控件增加监听器，给EditText增加了一个**TextWatcher**的匿名类，重写其方法**afterTextChanged(Editable s)**,在内容发生改变的时候，显示clear按钮。并在设置clear按钮的点击事件，清空EditText的内容。

疑问：当调用**adapter.notifyDataSetChanged**()时，图片会重新加载，图片大小从0变到原有的大小，变化时使布局改变。怎么能做到调用**adapter.notifyDataSetChanged**()时，布局不改变？

思路：定死图片的大小。虽然会使图片变得不容易看清楚，但是可以添加点击图片放大查看的功能。（又是新的坑！）