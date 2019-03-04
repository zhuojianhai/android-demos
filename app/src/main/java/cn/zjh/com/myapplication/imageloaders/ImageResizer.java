package cn.zjh.com.myapplication.imageloaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;


/**
 * Created by zhuojh on 2019/2/20.
 *单独完成图片的压缩功能
 *
 *
 */

public class ImageResizer {
    private static final String TAG = "ImageResize";

    public ImageResizer(){

    }


    /**
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap  decodeSampleBitmapFromReasoure(Resources res,int resId,int reqWidth,int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(res,resId,options);

        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res,resId,options);
    }


    /**
     *
     * @param fd         // 打开文件，获取文件描述符
     *                   String fileName = "out.txt";
     *                   FileOutputStream os = new FileOutputStream(fileName);
     *                   FileDescriptor  fd = os.getFD();

     * @param reqWidth
     * @param reqHeiht
     * @return
     */
    public Bitmap decodeSampleBitmapFromFileDesciptor(FileDescriptor fd,int reqWidth,int reqHeiht){
        BitmapFactory.Options  options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd,null,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeiht);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFileDescriptor(fd,null,options);
    }

    /**
     * 根据具体规则计算出inSampleSize值
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        if(reqWidth ==0 || reqHeight== 0){
         return inSampleSize;
        }

        final int height = options.outHeight;
        final int width = options.outWidth;

        /**
         * 如果图片的宽或者高大于期待的宽高
         *  1》先把图片的宽和高都缩小到原来的一半
         *
         */
        if(height>reqHeight || width>reqWidth){
            final int halfHeight = height/2;
            final int halfWidth = width/2;

            while((halfHeight/inSampleSize)>=reqHeight && (halfWidth/inSampleSize)>reqWidth){
                    inSampleSize*=2;
            }
        }
        return inSampleSize;
    }


}
