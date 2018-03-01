/*
 *排序类
 */

import java.util.ArrayList;

public class Sort {
    public ArrayList<Book> sortbook(ArrayList<Book> books){
       for(int i=0;i<books.size();i++){
           for(int j=0;j<books.size();j++){
               Book temp = new Book();
               if(books.get(i).getScore()>books.get(j).getScore()){
                   temp.setId(books.get(j).getId());
                   temp.setName(books.get(j).getName());
                   temp.setScore(books.get(j).getScore());
                   temp.setCommetNum(books.get(j).getCommetNum());
                   temp.setAuthor(books.get(j).getAuthor());
                   temp.setPrice(books.get(j).getPrice());
                   temp.setPublicationData(books.get(j).getPublicationData());
                   temp.setPress(books.get(j).getPress());
                   books.get(j).setId(books.get(i).getId());
                   books.get(j).setName(books.get(i).getName());
                   books.get(j).setScore(books.get(i).getScore());
                   books.get(j).setCommetNum(books.get(i).getCommetNum());
                   books.get(j).setAuthor(books.get(i).getAuthor());
                   books.get(j).setPress(books.get(i).getPress());
                   books.get(j).setPublicationData(books.get(i).getPublicationData());
                   books.get(j).setPrice(books.get(i).getPrice());
                   books.get(i).setId(temp.getId());
                   books.get(i).setName(temp.getName());
                   books.get(i).setScore(temp.getScore());
                   books.get(i).setCommetNum(temp.getCommetNum());
                   books.get(i).setAuthor(temp.getAuthor());
                   books.get(i).setPress(temp.getPress());
                   books.get(i).setPublicationData(temp.getPublicationData());
                   books.get(i).setPrice(temp.getPrice());
               }
           }
       }
       ArrayList<Book> res = new ArrayList<>();
        for(int i=0;i<100;i++){
            res.add(books.get(i));
        }
        return res;
    }

}
