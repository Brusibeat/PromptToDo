package org.academiadecodigo.hashtronauts.server.todolist;

import org.academiadecodigo.hashtronauts.server.users.User;
import org.academiadecodigo.hashtronauts.server.utils.FileSystem;
import org.academiadecodigo.hashtronauts.server.utils.Utils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

public class TodoListStore {

    private HashMap<String, TodoList> todoLists;
    private final String PATH = "resources/lists/";
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
     * @param name - the name of the requested list
     * @return a {@code TodoList} if it exists, or null if it doesn't exist
     */
    public TodoList getTodo(String name){
        return todoLists.get(Utils.getCRC32(name));
    }

    /**
     * Loads {@code TodoList} from the file to the HashMap property
     * @param fileName name of the file to load
     */
    public void loadTodos(String fileName){
        String codedName = Utils.getCRC32(fileName);
        String filePath = PATH + codedName + FILE_FORMAT;
        byte[] data = FileSystem.loadFile( filePath );

        if (data == null ) {
            return;
        }

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

    }

    /**
     * Saves list and its items into a file
     * @param fileName name of the list to save as a file
     */
    public void saveTodos(String fileName){
        String codedName = Utils.getCRC32( fileName );
        String filePath = PATH + codedName + FILE_FORMAT;

        StringBuilder data = new StringBuilder();

        for (TodoItem item : todoLists.get(codedName).getItems().values()) {
            data.append(item.toString());
        }

        FileSystem.saveFile( filePath, data.toString().getBytes());
    }

    /**
     * Creates a new instance of {@code TodoList} and adds it to the HashMap property
     * @return a new instance of {@code TodoList}
     */
    public TodoList createTodo(String id){
        String newId = Utils.getCRC32(id);
        TodoList newList = new TodoList( newId );

        todoLists.put( newId, newList );

        return newList;
    }



}
