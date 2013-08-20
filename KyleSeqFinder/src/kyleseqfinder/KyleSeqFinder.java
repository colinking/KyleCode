/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kyleseqfinder;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author colin
 */
public class KyleSeqFinder {

    /**
     * @param args the command line arguments
     */
    public static void writeData(File file, String name, String seq) {
    }

    public static File[] finder(String dirName) {
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".ace");
            }
        });

    }

    public static void main(String[] args) {
        /* Create new file for each file
         * Loop through each file
         * Search for "RD HWI-EAS"
         *  search each line, is it start with ^^?
         * Take the line with the name
         * Take the line with the sequence, write it
         * after finishing a folder, make new folder
         */
        for (File file: finder("")) {
            System.out.println(file.toString());
        }
    }
}
