package com.saimawzc.freight.weight.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.provider.MediaStore;
import android.service.wallpaper.WallpaperService;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;


import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

public class GLView extends GLSurfaceView {
    boolean shouldTakePic = false;
    private int surfaceWidth;
    private int surfaceHeight;
    private Activity activity;
    public void setShouldTakePic(boolean shouldTakePic) {
        this.shouldTakePic = shouldTakePic;
    }


    public GLView(Context context, Activity mActivity) {
        super(context);
        activity=mActivity;
        setEGLContextFactory(new ContextFactory());
        setEGLConfigChooser(new ConfigChooser());

        this.setRenderer(new Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {

            }

            @Override
            public void onSurfaceChanged(GL10 gl, int w, int h) {

            }

            @Override
            public void onDrawFrame(GL10 gl) {

                    try {
                        if (shouldTakePic) {
                            shouldTakePic = false;
                            int w = surfaceWidth;
                            int h = surfaceHeight;
                            int b[] = new int[(int) (w * h)];
                            int bt[] = new int[(int) (w * h)];
                            IntBuffer buffer = IntBuffer.wrap(b);
                            buffer.position(0);
                            GLES20.glReadPixels(0, 0, w, h, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buffer);
                            for (int i = 0; i < h; i++) {
                                for (int j = 0; j < w; j++) {
                                    int pix = b[i * w + j];
                                    int pb = (pix >> 16) & 0xff;
                                    int pr = (pix << 16) & 0x00ff0000;
                                    int pix1 = (pix & 0xff00ff00) | pr | pb;
                                    bt[(h - i - 1) * w + j] = pix1;
                                }
                            }
                            Bitmap inBitmap = null;
                            if (inBitmap == null || !inBitmap.isMutable() || inBitmap.getWidth() != w || inBitmap.getHeight() != h) {
                                inBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                            }
                            inBitmap.copyPixelsFromBuffer(buffer);
                            inBitmap = Bitmap.createBitmap(bt, w, h, Bitmap.Config.ARGB_8888);
                            saveBitmap(inBitmap,activity);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

        });
        this.setZOrderMediaOverlay(true);
    }

    @SuppressLint("WrongThread")
    public void saveBitmap(Bitmap bmp, Activity context) {
        //首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "VisualSonic");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "保存照片成功", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "保存失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {

        super.onDetachedFromWindow();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {

        super.onPause();
    }
    private static class ContextFactory implements EGLContextFactory
    {
        private static int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

        @Override
        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig)
        {
            EGLContext context;
            int[] attrib = { EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE };
            context = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib );
            return context;
        }

        @Override
        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context)
        {
            egl.eglDestroyContext(display, context);
        }
    }

    private static class ConfigChooser implements EGLConfigChooser {
        @Override
        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            final int EGL_OPENGL_ES2_BIT = 0x0004;
            final int[] attrib = {EGL10.EGL_RED_SIZE, 4, EGL10.EGL_GREEN_SIZE, 4, EGL10.EGL_BLUE_SIZE, 4,
                    EGL10.EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT, EGL10.EGL_NONE};

            int[] num_config = new int[1];
            egl.eglChooseConfig(display, attrib, null, 0, num_config);

            int numConfigs = num_config[0];
            if (numConfigs <= 0)
                throw new IllegalArgumentException("fail to choose EGL configs");

            EGLConfig[] configs = new EGLConfig[numConfigs];
            egl.eglChooseConfig(display, attrib, configs, numConfigs,
                    num_config);

            for (EGLConfig config : configs) {
                int[] val = new int[1];
                int r = 0, g = 0, b = 0, a = 0, d = 0;
                if (egl.eglGetConfigAttrib(display, config, EGL10.EGL_DEPTH_SIZE, val))
                    d = val[0];
                if (d < 16)
                    continue;

                if (egl.eglGetConfigAttrib(display, config, EGL10.EGL_RED_SIZE, val))
                    r = val[0];
                if (egl.eglGetConfigAttrib(display, config, EGL10.EGL_GREEN_SIZE, val))
                    g = val[0];
                if (egl.eglGetConfigAttrib(display, config, EGL10.EGL_BLUE_SIZE, val))
                    b = val[0];
                if (egl.eglGetConfigAttrib(display, config, EGL10.EGL_ALPHA_SIZE, val))
                    a = val[0];
                if (r == 8 && g == 8 && b == 8 && a == 0)
                    return config;
            }

            return configs[0];
        }
    }
}
