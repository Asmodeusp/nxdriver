package com.saimawzc.freight.weight.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    private Paint p = new Paint();
    private Path path = new Path();
    private Bitmap bi  = Bitmap.createBitmap(400, 800, Bitmap.Config.ARGB_8888);
    private SurfaceHolder holder = null;
    private Canvas canvas = null;
    private Canvas canvasTemp = null;
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
// TODO Auto-generated constructor stub
        holder = getHolder();
        holder.addCallback(this);
        p.setColor(Color.RED);
        p.setTextSize(40);
        p.setStrokeWidth(5);
        p.setAntiAlias(true);
        p.setFlags(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
        setOnTouchListener(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
// TODO Auto-generated method stub

    }
    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
// TODO Auto-generated method stub
        bi = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        canvasTemp = new Canvas(bi);
        draw();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
// TODO Auto-generated method stub

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
// TODO Auto-generated method stub
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:

                path.moveTo(event.getX(), event.getY());
                draw();
                break;
            //不能将ACTION_DOWN里的写在ACTION_MOVE里，不然会看不到画出的轨迹
            case MotionEvent.ACTION_MOVE: // 画出每次移动的轨迹
            case MotionEvent.ACTION_UP:
                path.lineTo(event.getX(), event.getY());
                draw();
                break;
        }
        return true;
    }


    private void draw() {
        try {
            canvas = holder.lockCanvas();
            if (holder != null) {
                canvasTemp.drawColor(Color.WHITE);
                canvasTemp.drawPath(path, p);
                canvas.drawBitmap(bi, 0, 0, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (holder != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }


    private String getTime(){
        return new SimpleDateFormat("HHmmssSSS").format(new Date(System.currentTimeMillis()));
    }
    //SurfaceView是不能截图的，不过看了你代码发现不是这个原因造成的保存不了图片
//是canvas.setBitmap()的位置不对，画完才设置Bitmap上去，肯定画不出来
    @SuppressLint("WrongThread")
    public void saveCanvas(View v){
//这样虽然能够保存画出的图片，但是看不到SurfaceView了
// Canvas c = new Canvas(bi);
// c.drawColor(Color.WHITE);
// this.draw(c);
        FileOutputStream fos = null;
        String fileName = getTime();
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+fileName+".png";
        try {
            fos = new FileOutputStream(new File(filePath));
            bi.compress(Bitmap.CompressFormat.PNG, 100, fos);
            MediaScannerConnection.scanFile(getContext(), new String[]{filePath}, null, new MediaScannerConnection.OnScanCompletedListener() {

                @Override
                public void onScanCompleted(String arg0, Uri arg1) {
// TODO Auto-generated method stub
                    Log.v("MediaScanWork", "file " + arg0
                            + " was scanned seccessfully: " + arg1);
                }
            });
            Toast.makeText(getContext(), "保存成功,文件名:" + filePath + ".png", Toast.LENGTH_LONG).show();
            clear();
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void clear() {
// TODO Auto-generated method stub
        path.reset();
        draw();
    }


}
