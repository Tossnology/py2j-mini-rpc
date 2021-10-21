package com.yrxx;

import cn.hutool.json.JSONUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Consumer {

    public static String call(String hostname, int port, String functionName, String[] args) {
        //构造一个Msg，并将其序列化发送给provider
        Msg msg = new Msg();
        msg.setFunctionName(functionName);
        msg.setArgs(args);
        String json = JSONUtil.toJsonStr(msg);
        int length = json.length();
        Socket socket = new Socket();
        byte[] in = new byte[1024];
        try {
            socket.connect(new InetSocketAddress(hostname, port));

            ByteBuffer out = ByteBuffer.allocate(4 + length);
            out.putInt(length);
            out.put(json.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = out.array();
            reverse0123(bytes);//python那头接收到的是反过来的
            socket.getOutputStream().write(bytes);
            socket.getInputStream().read(in);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(in);
    }

    private static void reverse0123(byte[] bytes) {
        int mid = 2;
        for(int i = 0; i < mid; i++) {
            swap(bytes, i, 3 - i);
        }
    }

    private static void swap(byte[] bytes, int index1, int index2) {
        byte tmp = bytes[index1];
        bytes[index1] = bytes[index2];
        bytes[index2] = tmp;
    }
}

