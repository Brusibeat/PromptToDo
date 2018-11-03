package org.academiadecodigo.hashtronauts.comms;

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


    /**
     * Enum containing all commands
     */
    public enum Command {
        //Server
        SHUTDOWN(Method.POST, "shutdown", false),
        LOGIN(Method.POST, "login", true),
        RESPONSE(Method.ACK, "ack", true),
        REGISTER(Method.POST, "register", true);

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
               if (string.equals("GET")) {
                   return Method.GET;
               }

               if (string.equals("POST")) {
                   return Method.POST;
               }

               return null;
        }
    }
}
