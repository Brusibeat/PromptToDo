package org.academiadecodigo.hashtronauts.comms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Communication {


    /**
     * Builds a message to be sent by client/server based on supplied command & arguments
     *
     * @param command the command to be used
     * @param args    the command arguments
     * @return the message to be sent
     */
    public static String buildMessage(Command command, String[] args) {

        String method = command.method.desc;
        String cmd = command.message;
        String arguments = "";

        if (command.hasArgs) {
            for (String arg : args) {
                arguments += (arg + ",");
            }

            if (arguments.endsWith(",")) {
                arguments = arguments.substring(0, arguments.length() - 1);
            }
        }

        return method + " " + cmd + " " + arguments+"\n";
    }

    public static Method getMethodFromMessage(String message) {
        if (!isValid(message)) {
            return null;
        }

        return Method.getFromString(message.split(" ")[0]);
    }

    public static Command getCommandFromMessage(String message) {
        if (!isValid(message)) {
            return null;
        }

        return Command.getFromString(message.split(" ")[1]);
    }


    public static boolean isValid(String message) {
        Method method = Method.getFromString(message.split(" ")[0]);

        if (method == null) {
            System.err.println("Got malformed packet: " + message);
            return false;
        }

        Command command = Command.getFromString(message.split(" ")[1]);
        if (command == null) {
            System.err.println("Got malformed packet: " + message);
            return false;
        }

        return true;
    }
    public static String[] getArgsFromMessage(String message) {


        String[] sections = message.split(" ");

        String strArgs = "";

        for (int i = 2; i < sections.length; i++) {
            strArgs += sections[i].replaceAll("\n", "");
        }

        String[] args = new String[1024];

        String[] tmp = strArgs.substring(0, strArgs.indexOf("\"")).split(",");

        for (int i = 0; i < tmp.length; i++) {
            args[i] = tmp[i];
        }

        Pattern pattern = Pattern.compile("([\"'])(?:(?=(\\\\?))\\2.)*?\\1");

        Matcher matcher = pattern.matcher(strArgs);

        if (matcher.find()) {
            args[tmp.length] = matcher.group(0);
        }

        return args;
    }


    /**
     * Enum containing all commands
     */
    public enum Command {
        //Server
        SHUTDOWN(Method.POST, "shutdown", false),
        LOGIN(Method.POST, "login", true),
        RESPONSE(Method.ACK, "ack", true),
        REGISTER(Method.POST, "register", true),
        CREATE_LIST(Method.POST, "createList", true),
        GET_LIST(Method.GET, "getList", true),
        CREATE_ITEM(Method.POST, "createItem", true),
        EDIT_ITEM(Method.POST, "editItem", true),
        LIST_ITEMS(Method.GET, "listItems", true),
        MARK_DONE(Method.POST, "markDone", true);

        private final Method method;
        private final String message;
        private final boolean hasArgs;

        Command(Method method, String message, boolean hasArgs) {
            this.method = method;
            this.message = message;
            this.hasArgs = hasArgs;
        }

        public static Command getFromString(String string) {
            for (Command command : Command.values()) {
                if (string.equals(command.message)) {
                    return command;
                }
            }

            return null;
        }
    }

    /**
     * Enum containing the available Methods
     */
    public enum Method {
        GET("GET"),
        POST("POST"),
        ACK("ACK");

        private final String desc;

        Method(String desc) {
            this.desc = desc;
        }

        public static Method getFromString(String string) {

            switch (string) {
                case "ACK":
                    return Method.ACK;
                case "POST":
                    return Method.POST;
                case "GET":
                    return Method.GET;
                default:
                    return null;
            }
        }
    }
}
