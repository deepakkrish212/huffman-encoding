#!/bin/bash

# ---------------------------------------------------------------------
# 2024-05-06 ADS (Alec, Deepak, and Soobin)
# Advanced Data Structures and Algorithms: Final HW
# Using Huffman Compression to create a compressed backup
# ---------------------------------------------------------------------
# How to run this program:
#   ./demo.sh

# Compile `Huffman.java` from textbook Chapter 5: Strings.
# Source:
#   https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Huffman.java.html
javac-algs4 Huffman.java

find . -type f -name "*.txt" | java-algs4 Huffman - | xxd
