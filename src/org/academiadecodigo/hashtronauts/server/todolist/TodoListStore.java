package org.academiadecodigo.hashtronauts.server.todolist;

import org.academiadecodigo.hashtronauts.server.users.User;
import org.academiadecodigo.hashtronauts.server.users.UserStore;
import org.academiadecodigo.hashtronauts.server.utils.FileSystem;
import org.academiadecodigo.hashtronauts.server.utils.Utils;

import java.util.HashMap;

public class TodoListStore {

    private HashMap<String, TodoList> todoLists;
    private final String PATH = "resources/lists/";
    private final String FILE_FORMAT = ".txt";
    private final UserStore userStore = new UserStore();

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
        String codedName = Utils.getCRC32(name);

        if (!todoLists.containsKey(codedName)) {
            String filePath = PATH + codedName + FILE_FORMAT;

            if(!FileSystem.fileExists(filePath)) {
                return null;
            }

            loadTodos(name);
        }

        return todoLists.get(Utils.getCRC32(name));
    }

    /**
     * Loads {@code TodoList} from the file to the HashMap property
     * @param fileName name of the file to load
     */
    public void loadTodos(String fileName){
        String codedName = Utils.getCRC32(fileName);
        TodoList todoList = todoLists.containsKey(codedName) ? todoLists.get(codedName) : createTodo(fileName);

        synchronized (todoList) {

            String filePath = PATH + codedName + FILE_FORMAT;
            byte[] data = new byte[0];
            data = FileSystem.loadFile(filePath);

            if (data.length <= 0 ) {
                return;
            }

            String[] items = new String(data).split("\n");
            String[] itemData;

            for (int i = 0; i < items.length; i++) {
                itemData = items[i].split(":");
                User user = userStore.getUser(itemData[1]);
                if (user == null) {
                    user = new User(0,"unknown",0);
                }

                todoList.createItem(Integer.parseInt(itemData[0]), itemData[3], user, Utils.parseFormatteDate(itemData[2]), Boolean.valueOf(itemData[4]));
            }

        }
    }

    /**
     * Saves list and its items into a file
     * @param fileName name of the list to save as a file
     */
    public void saveTodos(String fileName){

        String codedName = Utils.getCRC32( fileName );
        TodoList todoList = todoLists.get(codedName);

        synchronized (todoList) {
            String filePath = PATH + codedName + FILE_FORMAT;

            StringBuilder data = new StringBuilder();

            for (TodoItem item : todoList.getItems().values()) {
                data.append(item.toString());
                data.append('\n');
            }

            FileSystem.saveFile(filePath, data.toString().getBytes());
        }
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
