import sys
import os
import time
import random
import struct

import argparse



# Given a filepath, returns the binary content of the file
def get_binary_file_data(filepath):
    try:
        f = open(filepath, 'rb')
        bits = f.read()
        f.close()
    except IOError as e:
        print("Couldn't open or read file (%s)." % e)

    # convert bytes into bits
    bin_str = ''.join(format(ord(x), 'b').zfill(8) for x in bits)
    return bin_str

def bytes_to_hex(byte_str):
    hex_str = ""
    for c in byte_str:
        hex_str += hex(ord(c))[2:] + " "
    return hex_str

# Given a binary string, a start index, and number of bytes to read, returns a set of bytes/chars.
def get_bytes(bin_str, start, num_bytes):
    if not start+8*num_bytes < len(bin_str):
        print("Not enough bytes to consume.")
        exit(0)
    msg = ""
    for i in range(start, start+8*num_bytes, 8):
        msg += chr(int(bin_str[i:i+8], 2))
    return start+8*num_bytes, msg

# Searches a binary string for a preamble and returns the index where it is located
# Returns -1 if no preamble found
def search_for_preamble(bin_str, preamble, start):
    if not preamble or not isinstance(preamble, str):
        print("No valid preamble given. Enter the preamble as a string with the hex digits you want to search for.")
        exit(1)
    preamble = bin(int(preamble,16))[2:]
    idx = bin_str[start:].find(preamble)
    if idx == -1:
        return len(bin_str), False
    return idx+start, True


def main():
    parser = argparse.ArgumentParser(description='Generate the messages for EC415 Lab5.')
    parser.add_argument('filepath',
                        help='File path to decode.')
    # parser.add_argument('grc_file', help='GRC file path that the program will compile and run.')
    args = parser.parse_args()
    filepath = args.filepath

    print("File path = \"" + filepath + "\"")


    binary = get_binary_file_data(filepath)

    start=1;

    while True:

        [ind, found] = search_for_preamble(binary, 'AABBCCDD', start)

        if found:
            print('index: %d'%ind)
        else:
            print('EOF')
            break
        [nextInd, lengthChar] = get_bytes(binary, ind+32, 1)
        length=ord(lengthChar)
        print('mesg length: %d'%length)
        [nextInd, mesgChar] = get_bytes(binary, nextInd, length)
        print('mesg: %s'%(mesgChar.decode('hex')))

        start=nextInd
