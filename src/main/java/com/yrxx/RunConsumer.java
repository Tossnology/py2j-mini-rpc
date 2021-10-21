package com.yrxx;

public class RunConsumer {
    public static void main(String[] args) {
        String functionName = "hello";
        String[] params = new String[]{"12345"};
        String res = Consumer.call("localhost", 1236, functionName, params);
        System.out.println("调用" + functionName + "从Provider返回的结果：" + res);
    }
}
