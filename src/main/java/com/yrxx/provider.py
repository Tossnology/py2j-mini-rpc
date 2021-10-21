import socket
import struct
import json
import services
import threading

class handleThread(threading.Thread):
    def __init__(self, channel, addr):
        threading.Thread.__init__(self)
        self.channel = channel
        self.addr = addr
    def run(self):
        print('连接地址：', self.addr)
        length_bytes = self.channel.recv(4)
        length = struct.unpack('i', length_bytes)[0]

        msg = self.channel.recv(length)
        d = json.loads(msg)
        functionName = d['functionName']
        args = d['args']
        res = eval('services.' + functionName)(args)
        self.channel.send(bytes(res, 'utf-8'))
        self.channel.close()
        

def start(port):
    s = socket.socket()
    host = socket.gethostname()
    s.bind((host, port))
    s.listen(5)

    while True:
        c, addr = s.accept()
        handleThread(c, addr).start()
        
