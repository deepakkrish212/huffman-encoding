#!/bin/bash

# ---------------------------------------------------------------------
# 2024-05-06 ADS (Alec, Deepak, and Soobin)
# Advanced Data Structures and Algorithms
# ---------------------------------------------------------------------

# ---------------------------------------------------------------------
# Using Huffman Compression to create a compressed backup
# ---------------------------------------------------------------------
# How to run this program:
#   ./demo.sh

# Compile `Huffman.java` from textbook Chapter 5: Strings.
# Source:
#   https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Huffman.java.html
javac-algs4 Huffman.java

cat input.txt
echo ""

echo "xxd (hex)"
java-algs4 Huffman - < input.txt | xxd
echo ""

echo "hexdump -b (binday)"
java-algs4 Huffman - < input.txt | hexdump -b

# ---------------------------------------------------------------------
# Basics of Bash Scripting
# ---------------------------------------------------------------------
# I started using this script with:
#   https://linuxcommand.org/tlcl.php

# When using string expansion with `$`, the double quotes are
# not required, but are recommended to limit the expansion scope
# and therefore prevent accidental stuff from happening.
#
# Also, "using the quotes around the parameter ensures that
# the operator is always followed by a string [by correctly
# causing an error instead of silently ignoring it]" (pg. 27)

# "During expansion, variable names may be surrounded by
# optional curly braces, {}. This is useful in cases where
# a variable name becomes ambiguous because of its surrounding
# context."
# e.g.
#   mv "$filename" "${filename}1"

# How to print newlines with `\n`:
#   printf "Index: %s\nValue: %s\n\n" \
#          $string1 \
#          $string2

# For variables, we can assign results of a command.
# e.g.
#   LIST=`$(ls -a)`
#
# Also, we can assign arithmetic expansion.
# e.g.
#   SUM=$((25+75))

# How to get a filename based on the current time:
#   CURRENT_TIME="$(date +"%x %r %Z")"
#   FILENAME="${CURRENT_TIME}.txt"

# `echo $?` prints out the exit status of the last executed program
# Success = zero
# Error = non-zero

# How to if else:
#   x=5 
#   if [ "$x" -eq 5 ]; then 
#       echo "x equals 5." 
#   else 
#       echo "x does not equal 5." 
#   fi

# A short form of if statement using logical and. Remember that
# logical executes the latter half only if the former half is true.
# By the way, same stuff for logical or `||`.
#   [[ -z "$VAR" ]] && echo "Invalid string input."

# How to loop:
#   count=0
#   while [[ "$count" -lt 5 ]]; do 
#       echo "$count" 
#       count=$((count + 1)) 
#   done
#
# * Bash scripting has `break` and `continue` too.
#
# Loop from a pipeline:
#   sort distros.txt | while read distro version release; do
#       printf "Distro: %s\tVersion: %s\tReleased: %s\n" \ 
#              "$distro" \ 
#              "$version" \ 
#              "$release"
#   done
#
# * "Here we take the output of the sort command and display
#   the stream of text. However, it is important to remember
#   that since a pipe will execute the loop in a subshell,
#   any variables created or assigned within the loop will
#   be lost when the loop terminates." (pg. 429)
#   If the pipeline doesn't work, we can just use the fact
#   that loops accept standard input redirection through:
#     `done < ./distros.txt'
#
# How to make a numerical loop:
#   for i in {0..9}; do
#       echo "Index ${i}"
#   done

# How to use arrays:
#   arr=(0 1 2 3 4 5 6 7 8 9)
#   arr2=("one" 2 "three")
#   arr3=()
#   echo "${arr[0]}"
#
# Other syntax:
#   ${#arr[@]}: The size of the array
#   ${arr[@]:index_starting:index_ending}: Index slicing
#
# How to append to an array:
#   arr3+=("four" 5)
#
# How to print all elements:
#   echo "${arr2[@]}"
#
# How to loop through every element:
#   for number in ${arr[@]}; do
#       echo number
#   done
#
# How to loop through using an index:
#   for i in ${!arr[@]}; do
#       echo "Index ${i}: ${arr[i]}"
#   done

# `[ ... ]` is a built-in function for checking if something
# is true or not.
# e.g.
#   `[ -e file ]`: check if that file exists
#   `[ -d file ]`: check if that file exists and is a folder
#   `[ -w file ]`: check if that file exists and is writable
#   `[ -x file ]`: check if that file exists and is executable
#
#   `[ -z string ]` check if that string has the length of zero
#   `[ string ]`: check if that string is not null
#   `[ string1 == string2 ]`: check if two strings are equal
#   `[ string1 != string2 ]`: check if two strings are not equal
#
#   * Never use > or < for comparison as they are redirection
#     operators. This can potentially overwrite some files.
#
#   `[ integer1 -eq integer2 ]`: is equal to
#   `[ integer1 -ne integer2 ]`: is not equal to
#   `[ integer1 -le integer2 ]`: is less or equal to
#   `[ integer1 -lt integer2 ]`: is less
#   `[ integer1 -ge integer2 ]`: is greater or equal to
#   `[ integer1 -gt integer2 ]`: is greater
#
#   `&&`: logical and
#   `||`: logical or
#   `!`: not
#
#   `[[ string =~ ^-?[0-9]+[a-z]+$ ]]`: regex matches example
#   `[[ "$FILE"=="foo.*"]]`: comparison with pattern matching

# How to get standard input:
#   echo "Enter: "
#   read var1 var2 var3 var4 var5
#
# How to get standard input in the form of an array:
#   read -a arr
#
# How to read a secret with a timeout:
#   read -s -t secret
#
# How to use a custom separator instead of just a white space:
#   file_info="$(grep "^soobinrho:" /etc/passwd)"
#   IFS=":" read user pw uid gid name home shell <<< "$file_info"
#
# * "The shell allows one or more variable assignments to take
#   place immediately before a command. These assignments alter the
#   environment for the command that follows. The effect of
#   the assignment is temporary."

# How to store an output of a executed program. It's simple:
#   output=$(./run)

