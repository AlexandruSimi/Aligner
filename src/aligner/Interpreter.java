/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aligner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class Interpreter {

    private SoundReader soundReader;
    private TextReader textReader;

    public void createReaders(String soundPathString, String textReaderString) {
        soundReader = new SoundReader(soundPathString);
        textReader = new TextReader(textReaderString);
    }

    public void exportXML() {
        String xmlPathString = "temp.xml";//o aliniere pe rand!
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File(xmlPathString));
            writer.write("<? xml version=\"1.0\" encoding=\"UTF-8\">\n");
            writer.write("<valori>\n");

            int startIndexI;
            int stopIndexI;
            startIndexI = 0;
            stopIndexI = 0;
            float media = soundReader.getByIndex(startIndexI);
            int valI = 0;
            stopIndexI = parse(startIndexI, media);
            writer.write("<group start=\"" + (startIndexI * 1.0) / 100 + "\" stop=\"" + (stopIndexI * 1.0) / 100 + "\">" + valI + "</group>\n");

            while (stopIndexI < soundReader.count() - 1) {
                startIndexI = stopIndexI + 1;
                media = soundReader.getByIndex(startIndexI);
                stopIndexI = parse(startIndexI, media);
                if (soundReader.getByIndex(startIndexI - 2) <= soundReader.getByIndex(stopIndexI - 1)) {
                    valI = increment(valI);
                } else {
                    valI = decrement(valI);
                }
                writer.write("<group start=\"" + (startIndexI * 1.0) / 100 + "\" stop=\"" + (stopIndexI * 1.0) / 100 + "\">" + valI + "</group>\n");
            }
            writer.write("</valori>");
        } catch (IOException ex) {
            Logger.getLogger(Interpreter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Interpreter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int parse(int indexStartI, float mediaF) {
        boolean conditionB = true;
        int indexIntermediareI = indexStartI;
        int indexStopI = indexStartI;
        while (conditionB == true) {
            indexStopI++;
            if (mediaF * 8.0 / 3 < soundReader.getByIndex(indexStopI) || mediaF > 8.0 / 3 * soundReader.getByIndex(indexStopI) || indexStopI >= soundReader.count() - 1) {
                conditionB = false;
            } else {
                mediaF = media(indexStartI, indexStopI);
            }
        }
        return indexStopI;
    }

    public void analyse() {
        //citim din temp
        //vedem siruri de 0, 1, 2, 3, 4.. care vor fi grupate in 
    }

    public SoundReader getSoundReader() {
        return soundReader;
    }

    public void setSoundReader(SoundReader soundReader) {
        this.soundReader = soundReader;
    }

    public TextReader getTextReader() {
        return textReader;
    }

    public void setTextReader(TextReader textReader) {
        this.textReader = textReader;
    }

    public int increment(int value) {
        return value + 1;
    }

    public int decrement(int value) {
        if (value == 0) {
            return 0;
        } else {
            return value - 1;
        }
    }

    public float media(int startIndex, int stopIndex) {
        float media = 0;
        for (int i = startIndex; i <= stopIndex; i++) {
            media += soundReader.getByIndex(i);
        }
        media /= (stopIndex - startIndex + 1);
        return media;
    }
}
