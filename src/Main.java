import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        //爬虫
        ArrayList<Book> allbooks = new ArrayList<>();
        OperationExcel operationExcel = new OperationExcel();

        operationExcel.createExcel("./result.xls");
        JsoupUtill ju = new JsoupUtill(Constants.InternetURL);
        allbooks=ju.getDoubanBook(allbooks);


         ju = new JsoupUtill(Constants.AlgorithmURL);
        allbooks=ju.getDoubanBook(allbooks);
         ju = new JsoupUtill(Constants.ProgramURL);
        allbooks=ju.getDoubanBook(allbooks);


//
        //写入excel

        InsertExcel insertExcel1 = new InsertExcel(allbooks);
        Thread thread1 = new Thread(insertExcel1);
        Thread thread2 = new Thread(insertExcel1);
        Thread thread3 = new Thread(insertExcel1);
        thread1.start();
        thread2.start();
        thread3.start();
      Thread.sleep(300);

        //排序生成结果
        allbooks=operationExcel.readExcel();
        Sort sort = new Sort();
        ArrayList<Book> resbooks = sort.sortbook(allbooks);
        operationExcel.createExcel("./sort.xls");
        for(Book book:resbooks){
            operationExcel.insertBook(book,"./sort.xls");
        }



    }

}
