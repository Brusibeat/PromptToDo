package org.academiadecodigo.hashtronauts.server.todolist;

import java.util.LinkedList;

public class TodoListStore {

    private LinkedList<TodoList> todoLists;

    /**
     * Fetches a TodoList by ID
     * @param id - the ID of the requested list
     * @return a {@code TodoList} if it exists, or null if it doesn't exist
     */
    public TodoList getTodo(int id){
        if( todoLists.size() < id ){
            return todoLists.get(id);
        }
        return null;
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
    public TodoList createTodo(){
        TodoList newList = new TodoList(todoLists.size() +1);

        todoLists.add( newList );

        return newList;
    }

}
