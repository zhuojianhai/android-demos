// IMyAidlInterface.aidl
package cn.zjh.com.myapplication;

// Declare any non-default types here with import statements
//即使是在同一包，也需要导入
import cn.zjh.com.myapplication.bean.Book;
interface BookController {

     List<Book> getBookList();
     void addBook(in Book book);//只允许客户端输入

}
