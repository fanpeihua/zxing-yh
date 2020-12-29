package com.yonghui.zxing;

import android.graphics.Bitmap;

import com.google.zxing.Result;

/**
 * @author fanpeihua
 */
public interface OnCaptureListener {


    /**
     * 接收解码后的扫码结果
     * @param result
     * @param barcode
     * @param scaleFactor
     */
    void onHandleDecode(Result result, Bitmap barcode, float scaleFactor);


}
