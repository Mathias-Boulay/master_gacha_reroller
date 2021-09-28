package fr.spse.master_gacha_reroller.utils;


import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for everything related to native unix commands
 * It mainly consists of wrappers to help with basic commands.
 * As of 27/09/2021, we assume commands need to be executed in SU.
 */
final public class Command {
    /** Remove the ability to be instantiated*/
    private Command(){}

    /** Convenience method to remove a file the UNIX way */
    public static boolean removeFile(String filePath){
        return execute("rm -f \"" + filePath + "\"");
    }

    /** Convenience method to remove a folder the UNIX way */
    public static boolean removeFolder(String folderPath){
        return execute("rm -Rf \"" + folderPath + "\"");
    }

    public static boolean killApp(String packageName){
        return execute("am force-stop " + packageName);
    }


    /** Convenience method for executing a single command */
    public static boolean execute(String command){
        return execute(new ArrayList<>(Collections.singletonList(command)), true);
    }

    /**
     * Execute UNIX commands
     * @param commands Commands to execute in order
     * @param isSuperUser are the commands using SU ?
     * @return whether or not it succeeded
     */
    private static boolean execute(ArrayList<String> commands, boolean isSuperUser) {
        boolean retval = false;

        try {
            if (null == commands || commands.size() == 0) {return retval;}

            Process process = Runtime.getRuntime().exec(isSuperUser ? "su" : "");

            DataOutputStream os = new DataOutputStream(process.getOutputStream());

            // Execute commands that may require root access
            for(String command : commands){
                os.writeBytes(command + "\n");
                os.flush();
            }

            os.writeBytes("exit\n");
            os.flush();

            try {
                int processRetval = process.waitFor();
                // Root access granted/denied
                retval = 255 != processRetval;
            }
            catch (Exception ex) {
                Log.e("ROOT", "Error executing root action", ex);
            }
        }
        catch (IOException | SecurityException ex) {
            Log.w("ROOT", "Can't get root access", ex);
        } catch (Exception ex) {
            Log.w("ROOT", "Error executing internal operation", ex);
        }

        return retval;
    }

}
