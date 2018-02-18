/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.gson.GsonBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import pojo.GuardLogPojo;

/**
 * Created by Jayant Singh on 22/1/18.
 */

public class QrHelper {

    private Context context;

    public QrHelper(Context context) {
        this.context = context;
    }

    public void generateAndSetQr(ImageView qrCode, GuardLogPojo guardLogPojo) {
        try {
            BitMatrix bitMatrix;

            Point point = new Point();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getSize(point);
            int widthX = point.x;

            bitMatrix = new MultiFormatWriter().encode(
                    new GsonBuilder().create().toJson(guardLogPojo),
                    BarcodeFormat.QR_CODE,
                    widthX, widthX, null
            );
            int width = bitMatrix.getWidth(),
                    height = bitMatrix.getHeight();
            int[] pixels = new int[height * width];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            qrCode.setImageBitmap(bitmap);
            qrCode.setVisibility(View.VISIBLE);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
