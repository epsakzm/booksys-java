package booksys.storage;

import booksys.presentation.LogFormatter;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Recorder {
    private final static Logger LOG = Logger.getGlobal();
    private static Recorder logInstance;
    private String logTitle;

    public static Recorder record() {
        if (logInstance == null) {
            logInstance = new Recorder();
        }
        return logInstance;
    }

    private void initErrorLog(StringBuffer buf) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            File file = new File("./logs/" + logTitle + "DB.log");
            fw = new FileWriter(file);
            fw.write(buf.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDBErrorLog() {

        File file = null;
        FileReader fr = null;
        BufferedReader br = null;
        StringBuffer buf = new StringBuffer();
        String line = "";
        String templogTitle;
        try {
            file = new File("C:\\Program Files\\MariaDB 10.2\\data\\PARK.err");
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                String[] dateToken = tokens[0].split("-");
                templogTitle = "";
                for (String token : dateToken) {
                    templogTitle += token;
                }
                if (templogTitle.equals(logTitle)) {
                    buf.append(line + "\n");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fr.close();
                br.close();
                initErrorLog(buf);
            } catch (IOException e) {
            }
        }
    }

    private Recorder() {
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        for (Handler h : handlers) {
            if (h instanceof ConsoleHandler) {
                rootLogger.removeHandler(h);
            }
        }
        LOG.setLevel(Level.INFO);

        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        logTitle = df.format(now);
        Handler handler = null;
        try {
            handler = new FileHandler("./logs/" + logTitle + ".log", true);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        LogFormatter formatter = new LogFormatter();
        handler.setFormatter(formatter);
        LOG.addHandler(handler);

        readDBErrorLog();
    }

    public void log(String msg) {
        LOG.info(msg);
    }
}