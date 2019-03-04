package cn.zjh.com.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.zjh.com.myapplication.BookController;
import cn.zjh.com.myapplication.bean.Book;

/**
 * Created by zhuojh on 2019/2/18.
 */

public class AIDLService extends Service {
    private String TAG = getClass().getSimpleName();
    private List<Book> bookList;

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {
        bookList = new ArrayList<>();

        bookList.add(new Book("活着",1));
        bookList.add(new Book("童年",2));
        bookList.add(new Book("在人间",3));
        bookList.add(new Book("我的大学",4));
        bookList.add(new Book("朝花夕拾",5));
        bookList.add(new Book("阿q正传",6));
    }



    private final BookController.Stub stub = new BookController.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if(book!=null){
                bookList.add(book);
            }else{
                Log.e(TAG,"接收一个空的对象");
            }

        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
