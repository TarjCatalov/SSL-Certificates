# Task 1: Securing a Communication Channel with SSL

For this task you will set up SSL certificates with either OpenSSL or XCA, and modify the Server.java and Client.java to use those certificates.


## Setup

To run the programs you will only need a standard installation of the Java-OpenJDK.

To see what an interceptor adversary could find from the programs, you can install
and use Wireshark (https://www.wireshark.org/).

Finally, you will want to have either the XCA (https://hohnstaedt.de/xca/) or
OpenSSL (https://www.openssl.org/) programs to create SSL certificates.


## Running the programs

With the setup done, you will first want to start Wireshark. Wireshark performs
packet sniffing to observe packets being sent and received by the network card of the computer it
is running on. This means the program requires administrator access, so you may find that on linux
and mac systems you will need to prepend sudo to the command used to run it.

When you start wireshark, it will give you a choice of network interfaces, select either loopback or
any. Enter 'tcp.port == 2250 && ip.addr == 127.0.0.1' without the quotations into the filter bar near the top of the program window to filter out to only the packets we are interested in. When running the other two programs, you should see packets appear in a list in the middle of the window,
right click one and select 'Follow' --> 'TCP Stream', another window will pop up and show the
messages being sent.


```sh
java Server.java
```

It will say that is listening on a port, which means you are ready to start the client with:

```sh
java Client.java
```

The server and client will exchange messages and output what they got from each other. You can now
check the interceptor again, it will have captured those messages and will also have said their
values. The captured messages are doubled since we are sending and receiving to the same computer,
so both sides of the communication are captured.

When you have completed the task successfully, Wireshark will give random byte output and nothing
meaningful that can be read.
