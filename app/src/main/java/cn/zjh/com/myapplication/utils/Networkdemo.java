package cn.zjh.com.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/***
 * 在使用OKHttp之前，首先要先了解如下几个比较核心的类：
 * OkHttpClient：客户端对象
 * Request：访问请求，Post请求中需要包含RequestBody
 * RequestBody：请求数据，在Post请求中用到
 * Response：即网络请求的响应结果
 * MediaType：数据类型，用来表明数据是json，image，pdf等一系列格式
 * client.newCall(request).execute()：同步的请求方法
 * client.newCall(request).enqueue(Callback callBack)：异步的请求方法，但Callback是执行在子线程中的，因此不能在此进行UI更新操作
 */
public class Networkdemo {
    private String Tag = this.getClass().getSimpleName();
    //将数据传递到ui主线程
    private Handler  mHander = new Handler(Looper.getMainLooper());

    OkHttpClient client = new OkHttpClient();


    public void showGet(){
        //采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
        Request request = new Request.Builder().get().url("https://www.baidu.com/").build();

        Call call =  client.newCall(request);

        //异步调用并设置回调函数
       call.enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {

           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               int responseCode =  response.code();
               final String responseStr = response.body().string();

               /***
                * 需要注意的是，异步调用的回调函数是在子线程当中的，
                * 因为需要用Handler或者runOnUiThread来更新UI
                */
//               runOnUiThread(new Runnable() {
//                   @Override
//                   public void run() {
//                       tv_result.setText(responseStr);
//                   }
//               });

           }
       });
    }


    /**
     * 在OkHttp中用Post方法向服务器发送一个请求体时，请求体需要是一个RequestBody。这个请求体可以是：
     * key-value：键值对类型
     * String：字符串类型
     * Form：类似于Html的表单数据提交
     * Stream：流类型
     * File：文件类型
     *
     * Post 键值对方式
     */
    public void showPostKeyValue(){
        FormBody formBody = new FormBody.
                Builder()
                .add("userName","initUserName")
                .add("password","initPassword")
                .build();

        Request request = new Request.Builder()
                .url("http://www.jianshu.com/")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                mHander.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(Tag,"相应数据为："+responseStr);
                    }
                });

            }
        });

    }

    /**
     * Post 字符串
     * 在上面的例子中Post传递的是参数对，
     * 有时候我们会有要传送字符串的需要，
     * 比如向服务器发送一个JSON字符串。那么就可以用如下方法：
     */
    public void showpostString(){
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("text/plain;charset=utf-8"),
                "{username:admin;password:admin}"
        );
        Request request = new Request.Builder()
                .url("http://www.jianshu.com/")
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
               Log.i(Tag, "Code：" + String.valueOf(response.code()));
                mHander.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(Tag,"请求结果为："+responseStr);
                    }
                });
            }
        });
    }

    /**
     * <form id="fm1" action="" method="post">
     *     <input id="username" name="username" type="text"/>
     *     <input id="password" name="password" type="password"/>
     *</form>
     *
     * 这里使用到 MuiltipartBody 来构建一个RequestBody，
     * 这是 RequestBody 的一个子类，提交表单数据就是利用这个类来实现的
     */
    public void showPostForm(){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userName","userName")
                .addFormDataPart("password","password")
                .build();

        Request request = new Request.Builder()
                .url("http://www.jianshu.com/")
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    /**
     *
     */
    public void showPostStream(){
        MediaType mediaTypeMarkdown = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return mediaTypeMarkdown;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("Numbers\n");
                sink.writeUtf8("-------\n");
                for (int i = 2; i <= 997; i++) {
                    sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
                }
            }

            private String factor(int n) {
                for (int i = 2; i < n; i++) {
                    int x = n / i;
                    if (x * i == n) return factor(x) + " × " + i;
                }
                return Integer.toString(n);
            }
        };

        Request request = new Request.Builder()
                .url("http://www.jianshu.com/")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /***
     * Post 文件
     *
     */
    public void showPostFile(){
        MediaType mediaType = MediaType.parse("text/x-markdown;charset=utf-8");
        File file = new File("Readme.md");

        Request request = new Request.Builder()
                .url("http://www.jianshu.com/")
                .post(RequestBody.create(mediaType,file))
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    public void showDownloadImage(ImageView imageView){
        Request request = new Request.Builder()
                .get()
                .url("http://avatar.csdn.net/B/0/1/1_new_one_object.jpg")
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              InputStream inputStream =  response.body().byteStream();
              final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
              mHander.post(new Runnable() {
                  @Override
                  public void run() {
                      imageView.setImageBitmap(bitmap);
                  }
              });

                //将图片保存到本地存储卡中
                File file = new File(Environment.getExternalStorageDirectory(), "image.png");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] temp = new byte[128];
                int length;
                while ((length = inputStream.read(temp)) != -1) {
                    fileOutputStream.write(temp, 0, length);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();

            }
        });
    }


    /****
     * Okhttp内置的拦截器如下所示： interceptor
     *  RetryAndFollowUpInterceptor:负责失败重试以及重定向。
     *  BridgeInterceptor:负责把用户构造的请求转换为发送给服务器的请求，把服务器返回的响应转换为对用户友好的响应。
     *  CacheInterceptor:负责读取缓存以及更新缓存。
     *  ConnectionInterceptor:负责与服务器建立连接。
     *  CallServerInterceptor:负责从服务器读取响应的数据。
     */


}
