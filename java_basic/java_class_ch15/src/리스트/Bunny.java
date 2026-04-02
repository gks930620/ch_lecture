package 리스트;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bunny {

    public  static  void makeBunnies(ArrayList<String> list ){
        for(int i=0 ; i<list.size()  ; i++){
            list.set(i, "버니즈가 된 " + list.get(i));
        }
    }

    public  static  void makeBunnies2(List<String> list ){
        for(int i=0 ; i<list.size()  ; i++){
            list.set(i, "버니즈가 된 " + list.get(i));
        }
    }

}
