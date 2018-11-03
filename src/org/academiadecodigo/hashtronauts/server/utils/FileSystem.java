package org.academiadecodigo.hashtronauts.server.utils;

import java.io.*;

public class FileSystem {

    /**
     * Load all data from a file located in {@param filePath}
     * @param filePath - Path to the requested file
     * @return A byte array containing all the data in the file.
     */
    public static byte[] loadFile(String filePath) {

        String result = "";
        BufferedReader reader = null;

        try {
            String line;

            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);

            reader = new BufferedReader(fileReader);

            while ((line = reader.readLine()) != null) {
                result += line + "\n";

            }

            reader.close();

        } catch (FileNotFoundException ex) {
            return null;

        } catch (IOException ex) {
            System.err.println("Something went wrong in loadFile() method.");
            ex.printStackTrace();

            return null;
        }

        return result.getBytes();
    }

    /**
     * Save all data in {@param data} to a file located in {@param filePath}. Method first calls the cleanFile()
     * method to clean the file before writing updated data on it.
     * @param filePath - Path to the file to save data in
     * @param data - Data to write in the file
     */
    public static void saveFile(String filePath, byte[] data){
        cleanFile(filePath);

        BufferedWriter writer;

        try {

            FileOutputStream outputStream = new FileOutputStream(filePath);

            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write( new String(data) );
            writer.close();

        } catch (IOException ex) {
            System.err.println("Something went wrong in saveFile() method");
            ex.printStackTrace();
        }

    }

    /**
     * Clean up a file of data.
     * @param filePath
     */
    private static void cleanFile(String filePath){
        BufferedWriter writer;

        try {

            File file = new File(filePath);

            file.getParentFile().mkdirs();
            file.createNewFile();

            FileOutputStream outputStream = new FileOutputStream(file);

            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write( "" );
            writer.close();

        } catch (IOException ex) {
            System.err.println("Something went wrong in cleanFile() method");
            ex.printStackTrace();
        }

    }

}
