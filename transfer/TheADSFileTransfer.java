import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import edu.princeton.cs.algs4.StdOut;


public class TheADSFileTransfer {

    private static File encode(File sourceFile) throws IOException{
        return Huffman.compress(sourceFile);
    }

    private static File decode(File encodedFile, File outFile)  throws IOException {
        return Huffman.expand(encodedFile, outFile);
    }

    private static void transferFile(File source, File dest) throws IOException {
        File encoded = encode(source);

        File destEncodeFile = new File(dest.getParent(), encoded.getName());

        Files.move(encoded.toPath(), destEncodeFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        decode(destEncodeFile, dest);
        
        StdOut.println("Original file size: " + source.length() + " bytes");
        StdOut.println("Encoded file size: " + destEncodeFile.length() + " bytes");
        StdOut.println("Saved Size: " + (((float) source.length() - (float) destEncodeFile.length())/((float) source.length()) * 100) + "%");
        
        Files.deleteIfExists(destEncodeFile.toPath());

    }

    public static void main(String[] args) {
        String source = args[0];
        String dest = args[1];

        File sourceFile = new File(source);
        File destFile = new File(dest);

        try {
            transferFile(sourceFile, destFile);
            System.out.println("File successfully transferred from " + sourceFile.getName() + " to " + destFile.getName());
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
