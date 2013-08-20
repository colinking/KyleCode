/*
 * @author Colin King
 * @date August 19, 2013
 */
package kyleseqfinder;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KyleSeqFinder {

    //writes the name and sequnece to a specified file
    public static void writeData(File file, String name, String seq) {
        try {
            //"true" tells the writer to not overwrite the data in the file each time
            FileWriter writeFile = new FileWriter(file, true);
            writeFile.write(">" + name + "\n");
            writeFile.write(seq + "\n");
            writeFile.close();
        } catch (IOException ex) {
            Logger.getLogger(KyleSeqFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //returns a list of all the files ending in ".ace" within a specified folder
    public static File[] finder(String dirName) {
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".ace");
            }
        });
    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { //some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
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
        String searchFor = "RD HWI-EAS";
//        String fileExt = ".ace";
        //String folderRoot = "/Users/KyleKing/Desktop/Sequence Files/";
        String folderRoot = "/home/colin/SequenceFiles/";
        /*String[] folderNames = new String[]{
            "1000009.2.fasta.assemblies", 
            "1000009.3.fasta.assemblies", 
            "1000009.4.fasta.assemblies", 
            "1000010.4.fasta.assemblies", 
            "1000010.6.fasta.assemblies", 
            "1000010.7.fasta.assemblies", 
            "1000010.8.fasta.assemblies"};*/
        String[] folderNames = new String[]{"ABC123", "ABC456", "ABC789"};
        //for each folder in the above list
        for (String folder : folderNames) {
            folder = folderRoot + folder;
            //create the new folder, won't create it if it already exists
            deleteFolder(new File(folder + ".txt"));
            new File(folder + ".txt").mkdir();
            for (File file : finder(folder)) {
                try {
                    int count = 0;
                    //search, and write
                    Scanner scan = new Scanner(file);
                    while (scan.hasNext()) {
                        String returnedText = scan.findInLine(searchFor);
                        String text = scan.nextLine();
                        if (returnedText != null) { //it found the sequence it was looking for
                            writeData(new File(folder + ".txt/" + file.getName() + count + ".fasta"), returnedText + text, scan.nextLine());
                            count++;
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(KyleSeqFinder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        System.out.println("Finished Folder");
        }
    }
}
