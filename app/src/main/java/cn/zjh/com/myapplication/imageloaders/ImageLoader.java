package cn.zjh.com.myapplication.imageloaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import java.io.File;

//import okhttp3.internal.cache.DiskLruCache;

/**
 * Created by zhuojh on 2019/2/20.
 *
 * 1>图片同步加载
 * 2》图片异步加载
 * 3》图片压缩
 * 4》内存缓存
 * 5》磁盘缓存
 * 6》网络加载图片
 */

public class ImageLoader {

    private LruCache<String,Bitmap> mMemoryCache;
//    private DiskLruCache mDiskLruCache;

    private Context mContext;
    public ImageLoader(Context context){
        mContext = context.getApplicationContext();
        int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);//kb
        int cacheSize = maxMemory /8;//内存缓存为 maxMemory的1/8;
        mMemoryCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
//                return super.sizeOf(key, value);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    return value.getAllocationByteCount()*value.getHeight() /1024;
                }else{
                    return value.getRowBytes()*value.getHeight() /1024;
                }
            }
        };

        File cacheFiles = getDiskCacheDir(mContext,"bitmap");
    }

    /**
     *
     * 如果有外置卡，将缓存设置在外置卡
     * 否则设置在 手机缓存路径中
     * 前者获取到的就是 /sdcard/Android/data/<application package>/cache 这个SD卡缓存路径，
     * 而后者获取到的是 /data/data/<application package>/cache 这个手机缓存路径。
     * @param mContext
     * @param uniqueName
     * @return
     */
    private File getDiskCacheDir(Context mContext, String uniqueName) {
      boolean externalAvaliable =   Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
      final String cachePath;
      if(externalAvaliable){
          cachePath = mContext.getExternalCacheDir().getPath();
      }else{
          cachePath = mContext.getCacheDir().getPath();
      }
        File file = new File(cachePath+File.separator+uniqueName);
        return file;
    }
}
