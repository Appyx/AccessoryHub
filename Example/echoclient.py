#! /usr/bin/env python2
import sys
import socket

arguments=""
sys.argv[0]=""
for argument in sys.argv:
    arguments+=argument+" "
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect(("10.0.0.98", 7777))
sock.sendall(sys.argv[1]) 
sock.close()
