import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/*
 *excel操作类
 */
public class OperationExcel {

    /*
     *新建一个excel
     */
    public void createExcel(String filname){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("用户表一");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("序号");
        cell = row.createCell(1);
        cell.setCellValue("书名");
        cell = row.createCell(2);
        cell.setCellValue("评分");
        cell = row.createCell(3);
        cell.setCellValue("评价人数");
        cell = row.createCell(4);
        cell.setCellValue("作者");
        cell = row.createCell(5);
        cell.setCellValue("出版社");
        cell = row.createCell(6);
        cell.setCellValue("出版日期");
        cell = row.createCell(7);
        cell.setCellValue("价格");

        try {
            FileOutputStream fos = new FileOutputStream(filname);
            workbook.write(fos);
            System.out.println("创建成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     *插入数据
     */
    public void insertBook(Book book,String filename) throws IOException {
        POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(filename));
//        POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream("./sort.xls"));
        HSSFWorkbook workbook = new HSSFWorkbook(fs);
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row1=sheet.getRow(0);
        int line = readline(filename);
        FileOutputStream out=new FileOutputStream(filename);
//        FileOutputStream out=new FileOutputStream("./sort.xls");
            row1 = sheet.createRow(line + 1);
            //创建单元格设值
            row1.createCell(0).setCellValue(line + 1);
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


    }
    /*
     *读取已存在的excel内容
     */
    public ArrayList<Book> readExcel() throws IOException {
        POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream("./result.xls"));
        HSSFWorkbook workbook = new HSSFWorkbook(fs);
        HSSFSheet sheet = workbook.getSheetAt(0);
        ArrayList<Book> books = new ArrayList<>();
        for(int i=1;i<=sheet.getLastRowNum();i++){
            HSSFRow row = sheet.getRow(i);
            HSSFCell cell = null;
            Book book = new Book();
            String id = row.getCell(0).toString();
            book.setId(id.substring(0,id.indexOf(".")));
            book.setName(row.getCell(1).toString());
            double score = Double.parseDouble(row.getCell(2).toString());
            book.setScore((double)Math.round(score*100)/100);
            book.setCommetNum(row.getCell(3).toString());
            book.setAuthor(row.getCell(4).toString());
            book.setPress(row.getCell(5).toString());
            book.setPublicationData(row.getCell(6).toString());
            book.setPrice(row.getCell(7).toString());
            books.add(book);

        }

        return books;
    }
    /*
     * 读取已存在书本数量
     */
    public int readline(String filename) throws IOException {
        POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(filename));
        HSSFWorkbook workbook = new HSSFWorkbook(fs);
        HSSFSheet sheet = workbook.getSheetAt(0);
        int line = sheet.getLastRowNum();
        return line;
    }
}

