import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class InsertExcel implements Runnable {
    private int count = 0;
    private ArrayList<Book> books ;



    public InsertExcel(ArrayList<Book> books){
        this.books = books;
    }

    /*
     *插入excel
     */
    public void insertExcel() throws IOException {
        synchronized (books){
            if(count>0){
//                System.out.println(Thread.currentThread().getName()+"@@"+count);
                Book book = books.get(count-1);
                POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream("./result.xls"));
                HSSFWorkbook workbook = new HSSFWorkbook(fs);
                HSSFSheet sheet = workbook.getSheetAt(0);
                HSSFRow row1=sheet.getRow(0);
                OperationExcel operationExcel = new OperationExcel();
                int line = operationExcel.readline("./result.xls");
                FileOutputStream out=new FileOutputStream("./result.xls");
                row1 = sheet.createRow(line + 1);
                //创建单元格设值
                row1.createCell(0).setCellValue(line+1);
                row1.createCell(1).setCellValue(book.getName());
                row1.createCell(2).setCellValue(String.valueOf(book.getScore()));
                row1.createCell(3).setCellValue(book.getCommetNum());
                row1.createCell(4).setCellValue(book.getAuthor());
                row1.createCell(5).setCellValue(book.getPress());
                row1.createCell(6).setCellValue(book.getPublicationData());
                row1.createCell(7).setCellValue(book.getPrice());
                try {
                    out.flush();
                    workbook.write(out);
                    out.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                return;
            }
        }
    }
    public void run(){
        while(count<=books.size()){
            try {
                insertExcel();
                count++;
            } catch (IOException e) {
                e.printStackTrace();
            }
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
