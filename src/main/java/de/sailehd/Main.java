package de.sailehd;

import de.sailehd.support.*;
import java.io.*;
import java.util.ArrayList;


public class Main {
    
    public static float deltaTime = 0.3f;

    public static synchronized void main(String[] args) throws Exception {
        Main main = new Main();
        main.Start();
    }
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private Thread loop;
    private String inputFolder;
    private String outputFile;
    private double fileSize;

    void Start() throws IOException{
        runLoop();
        String[] fileformats = new String[]{
                "mp3"
        };


        Debug.log(TextColor.RED + "FolderName" + TextColor.RESET);
        inputFolder = reader.readLine()+ "/";

        Debug.log(TextColor.RED + "Merged File Name" + TextColor.RESET);
        outputFile =  reader.readLine() + ".mp3";

        for (File file :FileManager.GetFiles(new File(inputFolder), fileformats)) {
            fileSize = fileSize + getFileSizeMegaBytesDouble(file);
        }

        FileManager.combineAudioFiles(inputFolder, outputFile, fileformats);

        loop.stop();
        System.exit(0);

    }

    public void runLoop(){
        this.loop = new Thread(() -> {
            long time = System.currentTimeMillis();
            while(true){
                if(System.currentTimeMillis() >= time + (1000 * 2)){
                    time = System.currentTimeMillis();
                    onSecond();
                }
            }


        });
        this.loop.setName("Loop");
        this.loop.start();
    }

    public void onSecond(){
        if(inputFolder != null && outputFile != null){
            Debug.clear();
            try {
                StringBuilder builder = new StringBuilder();
                double mbDone = getFileSizeMegaBytesDouble(new File(outputFile));
                builder.append(mbDone + "/" + fileSize + "mb | ");

                double onePercent = fileSize / 100;
                double percentDone = mbDone / onePercent;
                builder.append(percentDone + "%" +  "/100%");

                Debug.log(builder.toString());
            }
            catch (Exception e){

            }
        }
    }

    private static String getFileSizeMegaBytes(File file) {
        return (double) file.length() / (1024 * 1024) + " mb";
    }
    private static double getFileSizeMegaBytesDouble(File file) {
        return (double) file.length() / (1024 * 1024);
    }
}
