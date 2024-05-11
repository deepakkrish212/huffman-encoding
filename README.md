# Huffman Encoding
> :warning: THE PROGRAM ONLY WORKS FOR TEXT FILES OF LESS THAN 4096 bytes

This is the official repository of the final project for Algorithms and Data Structures (COCS 327) at Augie.
- <b>Team Members</b>
    - Alec
    - Deepak
    - Soobin

<h1> Project </h1>
Everday, million terabytes of data is being transffered over the internet. However, if lossless compression didn't exist, this number would be WAYYYYY higher. Lossless compression is a data compression technique that is able to compress data without losing any data. 
<br> <br>
There are various number of algorithms that are being used everyday to compress different types of data. For this project, our team will be utilizing the <b> Huffman Encoding </b> algorithm to compress text files.

<h2> Run Instruction </h2>

> Make sure to have `algs4` library installed

Compilation <br>
`javac-algs4 FileServer.java`
`javac-algs4 FileClient.java`

Execution <br> (In different termial instace)
`java-algs4 FileServer`
`java-algs4 FileClient`

Instructions

- `send fileName.txt` - Send the file name from client to all of the clients connected to the server
- `decode filename.huf` - Decode the huffman encoded file
