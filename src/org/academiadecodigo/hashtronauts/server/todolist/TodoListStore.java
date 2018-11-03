package org.academiadecodigo.hashtronauts.server.todolist;

import org.academiadecodigo.hashtronauts.server.utils.FileSystem;

import java.util.HashMap;
import java.util.zip.CRC32;

public class TodoListStore {

    private HashMap<String, TodoList> todoLists;
    private final String PATH = "resources/";
    private final String FILE_FORMAT = ".txt";

    /**
     * Constructs a new instance of {@code TodoListStore}. Initializes a new HashMap that will contain all references
     * of {@code TodoList}.
     */
    public TodoListStore(){
        todoLists = new HashMap<>();
    }

    /**
     * Fetches a TodoList by ID
     * @param id - the ID of the requested list
     * @return a {@code TodoList} if it exists, or null if it doesn't exist
     */
    public TodoList getTodo(int id){
        return todoLists.get(id);
    }

    /**
     * Loads {@code TodoList} from the file to the HashMap property
     */
    public void loadTodos(String fileName){
        String codedName = Utils.getCRC32(fileName);
        String filePath = PATH + codedName + FILE_FORMAT;
        byte[] data = FileSystem.loadFile( filePath );

        if( !todoLists.containsKey(fileName)){
            createTodo( fileName );
        }

        TodoList loadedList = todoLists.get( fileName );
        String[] items = data.toString().split("\n");
        String[] itemData;

        for(int i = 0; i < items.length; i++){
            itemData = items[i].split(":");

            loadedList.createItem(Integer.parseInt(itemData[0]), itemData[3]);
        }

        loadedList.createItem();
    }

    /**
     * Saves {@code TodoList} from the HashMap property to a file
     */
    public void saveTodos(String fileName){
        String codedName = Utils.getCRC32( fileName );
        String filePath = PATH + codedName + FILE_FORMAT;

        String data = "";
        TodoItem item;
        for( int i = 0; i < todoLists.get( fileName ).getItems().size(); i++){
            item = todoLists.get( fileName ).getItem(i);
            data = item.getItemID() + ":";
            data += item.getEditedBy() + ":";
            data += item.getEditedDate() + ":";
            data += item.getItemValue() + "\n";
        }

        FileSystem.saveFile( filePath, data.getBytes());

    }

    /**
     * Creates a new instance of {@code TodoList} and adds it to the HashMap property
     * @return a new instance of {@code TodoList}
     */
    public TodoList createTodo(String id){
        String newId = createTodoListID(id);
        TodoList newList = new TodoList( newId );

        todoLists.put( newId, newList );

        return newList;
    }

    public String createTodoListID(String title){
        CRC32 crc32 = new CRC32();
        crc32.update(title.getBytes());

        return Long.toHexString( crc32.getValue() );
    }

}
