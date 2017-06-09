package com.illusion.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rammstein on 24.06.16.
 * Class that are outputs Log messages with custom format:
 * <p>
 * Type:       [  TYPE_OF_MESSAGE  ]
 * Time:       TIME_WHEN_MESSAGE_WAS_INCOMING
 * Class:      CLASS_WHERE_WAS_LOG_CALL
 * Message:    MESSAGE_FOR_LOG
 */
public class    Logger {

    private static String TAG = "Logger";
    /**
     * Date format for log output
     */
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat(
            "MM-dd HH:mm:ss.ssss", Locale.getDefault()
    );

    public static void setTag(String tag){
        TAG = tag;
    }

    /**
     * Method that are managing of Log Type
     * <p>
     * Default Log - DEBUG
     *
     * @param requestedBy - class where was method call
     * @param message     - message for Log
     * @param type        - type of message
     */
    public static void setLog(Class requestedBy, String message, Type type) {
        switch (type) {
            case T_DEBUG:
                setDebugLog(requestedBy, message);
                break;
            case T_WARNING:
                setWarningLog(requestedBy, message);
                break;
            case T_ERROR:
                setErrorLog(requestedBy, message);
                break;
            default:
                setDefaultLog(requestedBy, message);
                break;
        }
    }

    private static void setDefaultLog(Class requestedBy, String message) {
        Log.i(TAG + requestedBy.getSimpleName(),
                "\n" +
                        "Type: [  DEFAULT  ]" + "\n" +
                        "Time: " + sDateFormat.format(new Date(System.currentTimeMillis())) + "\n" +
                        "Class: " + requestedBy.getSimpleName() + "\n" +
                        "Message: " + message
        );
    }

    private static void setDebugLog(Class requestedBy, String message) {
        Log.d(TAG + requestedBy.getSimpleName(),
                "\n" +
                        "Type: [  DEBUG  ]" + "\n" +
                        "Time: " + sDateFormat.format(new Date(System.currentTimeMillis())) + "\n" +
                        "Class: " + requestedBy.getSimpleName() + "\n" +
                        "Message: " + message
        );
    }

    private static void setWarningLog(Class requestedBy, String message) {
        Log.w(TAG + requestedBy.getSimpleName(),
                "\n" +
                        "Type: [  WARNING  ]" + "\n" +
                        "Time: " + sDateFormat.format(new Date(System.currentTimeMillis())) + "\n" +
                        "Class: " + requestedBy.getSimpleName() + "\n" +
                        "Message: " + message
        );
    }

    private static void setErrorLog(Class requestedBy, String message) {
        Log.e(TAG + requestedBy.getSimpleName(),
                "\n" +
                        "Type: [  ERROR  ]" + "\n" +
                        "Time: " + sDateFormat.format(new Date(System.currentTimeMillis())) + "\n" +
                        "Class: " + requestedBy.getSimpleName() + "\n" +
                        "Message: " + message
        );
    }

    public enum Type {
        T_DEFAULT, T_DEBUG, T_WARNING, T_ERROR
    }
}
