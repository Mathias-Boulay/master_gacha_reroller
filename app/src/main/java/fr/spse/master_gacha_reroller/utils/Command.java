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
public class Command {


    /** Convenience method to remove a file the UNIX way */
    public static void removeFile(String filePath){
        execute("rm -f \"" + filePath + "\"", true);
    }

    /** Convenience method to remove a folder the UNIX way */
    public static void removeFolder(String folderPath){
        execute("rm -Rf \"" + folderPath + "\"", true);
    }


    /** Convenience method for executing a list of commands */
    public static void execute(String command, boolean isSuperUser){
        execute(new ArrayList<>(Collections.singletonList(command)), isSuperUser);
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
