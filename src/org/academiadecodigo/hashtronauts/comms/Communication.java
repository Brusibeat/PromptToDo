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


        return method + " " + cmd + " " + arguments;
    }


    /**
     * Enum containing all commands
     */
    public enum Command {
        //Server
        SHUTDOWN(Method.POST, "shutdown", false);

        private final Method method;
        private final String message;
        private final boolean hasArgs;

        Command(Method method, String message, boolean hasArgs) {
            this.method = method;
            this.message = message;
            this.hasArgs = hasArgs;
        }
    }

    /**
     * Enum containing the available Methods
     */
    private enum Method {
        GET("GET"),
        POST("POST");

        private final String desc;

        Method(String desc) {
            this.desc = desc;
        }
    }
}
