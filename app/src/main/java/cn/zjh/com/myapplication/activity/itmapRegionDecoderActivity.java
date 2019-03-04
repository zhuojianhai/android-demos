package cn.zjh.com.myapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import cn.zjh.com.myapplication.R;

/****
 * 一般为了尽可能避免OOM都会按照如下做法：
 *  1>对于图片显示：根据需要显示图片控件的大小对图片进行压缩显示。
 *  2>如果图片数量非常多：则会使用LruCache等缓存机制，将所有图片占据的内容维持在一个范围内。
 *  其实对于图片加载还有种情况，就是单个图片非常巨大，并且还不允许压缩。比如显示：世界地图、清明上河图、微博长图等。
 *  那么对于这种需求，该如何做呢？
 *  首先不压缩，按照原图尺寸加载，那么屏幕肯定是不够大的，并且考虑到内存的情况，不可能一次性整图加载到内存中，所以肯定是局部加载，那么就需要用到一个类：
 *  BitmapRegionDecoder
 *
 *
 *
 *
 *
 */
public class itmapRegionDecoderActivity extends DemoBaseActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itmap_region_decoder);
        imageView = (ImageView)findViewById(R.id.id_imageview);
        try {
            //获得图片的宽、高
            InputStream inputStream = getAssets().open("changtu.jpg");
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream,null,tmpOptions);
            int width = tmpOptions.outWidth;
            int height = tmpOptions.outHeight;

            //设置图片的显示中心区域
            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream,false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            Rect rect = new Rect(width / 2 - 100, height / 2 - 100,width / 2 + 100, height / 2 + 100);

            Bitmap bitmap = bitmapRegionDecoder.decodeRegion(rect,options);

            imageView.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }

//        改变view的位置
//      ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)imageView.getLayoutParams();
//        marginLayoutParams.width+=100;
//        marginLayoutParams.leftMargin+=100;
//        imageView.requestLayout();
//        imageView.setLayoutParams(marginLayoutParams);
    }
}
