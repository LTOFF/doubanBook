import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 *爬虫工具类
 *
 */
public class JsoupUtill {

    private String Url;
    public JsoupUtill(String url) {
        this.Url = url;
    }






    public ArrayList<Book> getDoubanBook(ArrayList<Book> allbooks) {
        try {

            //得到1-20页数据
            for (int i = 0; i < 25; i++) {

                String url = Url + "?start="+String.valueOf(i * Constants.NUM)+ Constants.START ;

                Connection connection = Jsoup.connect(url);
                Document document = connection.get();


                Elements books = document.select("li.subject-item");//获得所有书的信息


                Iterator<Element> bookIter = books.iterator();

                while (bookIter.hasNext()) {
                    Element item = bookIter.next();//获得单本书的信息
                    Book book = new Book();
                    book.setName(String.valueOf(item.select("a").attr("title")));


                    String[] result = String.valueOf(item.select("div.pub").text()).split("/");
                    if(result.length==4){
                        book.setAuthor(result[0]);
                        book.setPress(result[1]);
                        book.setPublicationData(result[2]);
                        book.setPrice(result[3]);
                    }else if(result.length<4){
                        book.setAuthor("书本信息不足");
                    }
                    else {
                        book.setAuthor(result[0]+result[1]);
                        book.setPress(result[2]);
                        book.setPublicationData(result[3]);
                        book.setPrice(result[4]);
                    }
                    if (item.select("span.rating_nums").isEmpty()) {
                        book.setScore(0);
                    }else {
                        book.setScore(Double.parseDouble(String.valueOf(item.select("span.rating_nums").text())));
                    }
                    book.setCommetNum(String.valueOf(item.select("span.pl").text()).replaceAll("\\(|\\)", ""));

                    int flag = 0;
                    for(int count=0;count<allbooks.size();count++){
                        if(book.getName().equals(allbooks.get(count).getName())){
                            flag = 1;
                            break;
                        }
                    }
                    if(flag == 0) {
                        allbooks.add(book);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return allbooks;
    }

}  