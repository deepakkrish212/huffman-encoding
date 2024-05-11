import edu.princeton.cs.algs4.MinPQ;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Huffman {

    private static final int R = 256;  // Extended ASCII alphabet size

    private Huffman() {}  // Prevent instantiation

    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        private boolean isLeaf() {
            return (left == null) && (right == null);
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public static void compress(InputStream inputStream, OutputStream outputStream) throws IOException {
        BufferedInputStream in = new BufferedInputStream(inputStream);
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(outputStream));

        // Read the input using a buffer since readAllBytes is not available
        List<Byte> byteList = new ArrayList<>();
        int data;
        while ((data = in.read()) != -1) {
            byteList.add((byte) data);
        }
        char[] input = new char[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            input[i] = (char) (byteList.get(i) & 0xff);  // Convert byte to char, treating as unsigned
        }

        // Frequency table
        int[] freq = new int[R];
        for (char c : input) {
            freq[c]++;
        }

        // Huffman trie
        Node root = buildTrie(freq);

        // Code table
        String[] st = new String[R];
        buildCode(st, root, "");

        // Print trie for decoder
        writeTrie(root, out);

        // Number of bytes in original message
        out.writeInt(input.length);

        // Encode input
        for (char c : input) {
            String code = st[c];
            for (int j = 0; j < code.length(); j++) {
                out.writeBoolean(code.charAt(j) == '1');
            }
        }

        // Close and flush the output stream
        out.flush();
        out.close();
    }

    // Modified function to take in inputStream and outputStream
    public static void expand(InputStream inputStream, OutputStream outputStream) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(inputStream));
        BufferedOutputStream out = new BufferedOutputStream(outputStream);

        // Read in Huffman trie from input stream
        Node root = readTrie(in);

        // Number of bytes to write
        int length = in.readInt();

        // Decode using the Huffman trie
        for (int i = 0; i < length; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                boolean bit = in.readBoolean();
                if (bit) x = x.right;
                else x = x.left;
            }
            out.write(x.ch);
        }

        // Flush output stream
        out.flush();
        out.close();
    }

    private static Node buildTrie(int[] freq) {
        MinPQ<Node> pq = new MinPQ<>();
        for (char i = 0; i < R; i++) {
            if (freq[i] > 0) pq.insert(new Node(i, freq[i], null, null));
        }

        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }

    private static void writeTrie(Node x, DataOutputStream out) throws IOException {
        if (x.isLeaf()) {
            out.writeBoolean(true);
            out.writeChar(x.ch);
            return;
        }
        out.writeBoolean(false);
        writeTrie(x.left, out);
        writeTrie(x.right, out);
    }

    private static Node readTrie(DataInputStream in) throws IOException {
        boolean isLeaf = in.readBoolean();
        if (isLeaf) {
            return new Node(in.readChar(), -1, null, null);
        } else {
            return new Node('\0', -1, readTrie(in), readTrie(in));
        }
    }

    private static void buildCode(String[] st, Node x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left, s + '0');
            buildCode(st, x.right, s + '1');
        } else {
            st[x.ch] = s;
        }
    }
}

