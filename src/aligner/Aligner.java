/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aligner;

/**
 *
 * @author Claudiu
 */
public class Aligner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SoundReader soundReader = new SoundReader("audio.xml");
        Interpreter interpreter = new Interpreter();
        interpreter.setSoundReader(soundReader);
        interpreter.exportXML();
    }
}
