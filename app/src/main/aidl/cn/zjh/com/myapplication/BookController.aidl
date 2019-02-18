// IMyAidlInterface.aidl
package cn.zjh.com.myapplication;

// Declare any non-default types here with import statements
import cn.zjh.com.myapplication.bean.Book;
interface BookController {

     List<Book> getBookList();
     void addBook(in Book book);

}
