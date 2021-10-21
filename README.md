# Java调用Python服务的简单RPC
通过 **Consumer.call()** 方法传入IP、端口、函数名和参数列表进行调用<br>
基于BIO实现<br>
协议：前4个字节为消息长度，后面为消息内容的Json格式<br>
先启动run_provider.py，再启动RunConsumer.java