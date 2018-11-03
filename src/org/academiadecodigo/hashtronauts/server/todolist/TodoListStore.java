package org.academiadecodigo.hashtronauts.server.todolist;

import java.util.HashMap;
import java.util.zip.CRC32;

public class TodoListStore {

    private HashMap<String, TodoList> todoLists;

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
     * Loads all {@code TodoList} from the file to the HashMap property
     */
    public void loadTodos(){

    }

    /**
     * Saves all {@code TodoList} from the HashMap property to a file
     */
    public void saveTodos(){

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
