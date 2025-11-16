import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
public class DataStore {
    private Map<Integer,String> store= new HashMap<>();

    public void write(int index, String data){
        store.put(index,data);
    }
    public String read(int index) throws NoSuchElementException{
        if(store.containsKey(index)){
            return store.get(index);
        }
        throw new NoSuchElementException("No value at index "+index);
    }
}
