package cn.zjh.com.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhuojh on 2019/2/18.
 */

public class Book implements Parcelable {
    public int bookId;
    public String bookName;

    public Book(String bookName, int bookId){
        this.bookId = bookId;
        this.bookName = bookName;
    }

    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);

    }
}
