## 引入

  **Gradle：**

> implementation	'com.yonghui.zxing:lib:1.0.0'

如果Gradle有下载依赖失败的情况，可以在Project的build.gradle里面添加如下：

```
allProjects{
	repositories {
		// ...
		maven {url 'https://dl.bintray.com/yonghui/zxing-yh'}
	}
}
```

## 布局引入

```
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">


    <SurfaceView
        android:id="@+id/surfaceView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

    <com.yonghui.zxing.ViewfinderView
        android:id="@+id/viewfinderView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:labelText="扫描货架二维码，可识别货架进入编辑"
        app:labelTextSize="14sp"
        app:frameWidth="300dp"
        app:frameHeight="300dp"
        app:laserColor="@color/colorAccent"
        app:frameColor="@color/colorPrimary"
        app:cornerColor="#F77B22"
        app:resultPointColor="@color/colorAccent"
        app:labelTextLocation="bottom"
        app:laserStyle="line"/>

    <ImageView
        android:id="@+id/ivTorch"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="160dp"
        android:src="@drawable/zxl_torch_selector"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        android:visibility="gone"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</FrameLayout>
```

## 代码示例

```
    //跳转的默认扫码界面
    startActivityForResult(new Intent(context,CaptureActivity.class),requestCode);
    
    //生成二维码
    CodeUtils.createQRCode(content,600,logo);
    //生成条形码
    CodeUtils.createBarCode(content, BarcodeFormat.CODE_128,800,200);
    //解析条形码/二维码
    CodeUtils.parseCode(bitmapPath);
    //解析二维码
    CodeUtils.parseQRCode(bitmapPath);
```

```
    // 1. 使用的Activity页面继承CaptureActivity
   
    // 2.获取CaptureHelper，里面有扫码相关的配置设置
    getCaptureHelper().playBeep(false)//播放音效
        .vibrate(true)//震动
        .supportVerticalCode(true)//支持扫垂直条码，建议有此需求时才使用。
        .decodeFormats(DecodeFormatManager.QR_CODE_FORMATS)//设置只识别二维码会提升速度
        .framingRectRatio(0.9f)//设置识别区域比例，范围建议在0.625 ~ 1.0之间。非全屏识别时才有效
        .framingRectVerticalOffset(0)//设置识别区域垂直方向偏移量，非全屏识别时才有效
        .framingRectHorizontalOffset(0)//设置识别区域水平方向偏移量，非全屏识别时才有效
        .frontLightMode(FrontLightMode.AUTO)//设置闪光灯模式
        .tooDarkLux(45f)//设置光线太暗时，自动触发开启闪光灯的照度值
        .brightEnoughLux(100f)//设置光线足够明亮时，自动触发关闭闪光灯的照度值
        .continuousScan(isContinuousScan)//是否连扫
        .supportLuminanceInvert(true);//是否支持识别反色码（黑白反色的码），增加识别率
        
        
        
    /**
     * 3.扫码结果回调
     *
     * @param result 扫码结果
     * @return
     */
    @Override
    public boolean onResultCallback(String result) {
        Log.d("scanResult:"+result);
        return true;
    }
```

ViewfinderView属性说明

| 属性                    | 值类型    | 默认值                               | 说明                                    |
| :---------------------- | :-------- | :----------------------------------- | :-------------------------------------- |
| maskColor               | color     | <font color=#000000>#60000000</font> | 扫描区外遮罩的颜色                      |
| frameColor              | color     | <font color=#1FB3E2>#7F1FB3E2</font> | 扫描区边框的颜色                        |
| cornerColor             | color     | <font color=#1FB3E2>#FF1FB3E2</font> | 扫描区边角的颜色                        |
| laserColor              | color     | <font color=#1FB3E2>#FF1FB3E2</font> | 扫描区激光线的颜色                      |
| resultPointColor        | color     | <font color=#EFBD21>#C0EFBD21</font> | 扫描区结果点的颜色                      |
| labelText               | string    |                                      | 扫描提示文本信息                        |
| labelTextColor          | color     | <font color=#C0C0C0>#FFC0C0C0</font> | 提示文本字体颜色                        |
| labelTextSize           | dimension | 14sp                                 | 提示文本字体大小                        |
| labelTextPadding        | dimension | 24dp                                 | 提示文本距离扫描区的间距                |
| labelTextLocation       | enum      | bottom                               | 提示文本显示位置                        |
| showResultPoint         | boolean   | false                                | 是否显示合适的扫码结果点                |
| frameWidth              | dimension |                                      | 扫码框宽度                              |
| frameHeight             | dimension |                                      | 扫码框高度                              |
| laserStyle              | enum      | line                                 | 扫描激光的样式                          |
| gridColumn              | integer   | 20                                   | 网格扫描激光列数                        |
| gridHeight              | integer   | 40dp                                 | 网格扫描激光高度，为0dp时，表示动态铺满 |
| cornerRectWidth         | dimension | 4dp                                  | 扫描区边角的宽                          |
| cornerRectHeight        | dimension | 16dp                                 | 扫描区边角的高                          |
| scannerLineMoveDistance | dimension | 2dp                                  | 扫描线每次移动距离                      |
| scannerLineHeight       | dimension | 5dp                                  | 扫描线高度                              |
| frameLineWidth          | dimension | 1dp                                  | 边框线宽度                              |
| scannerAnimationDelay   | integer   | 15                                   | 扫描动画延迟间隔时间，单位：毫秒        |
| frameRatio              | float     | 0.625f                               | 扫码框与屏幕占比                        |

## 引入的库：

```gradle
//Android
api 'com.android.support:appcompat-v7:28.0.0'
api 'com.google.zxing:core:3.3.3'
```




