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

# Task 2: A basic password manager

For this task, you will develop a basic password manager for a single client. This password
manager will be secured with your own implementation of the RSA algorithm. You will have find
the correct way to use RSA, i.e. which parts of the messages to encrypt/decrypt, so that the
password management server and potential interceptors are never able to see the cleartext passwords.

The following sections provide further details for the expected pieces of the program.


## RSA

You will read p and q from the primes.txt file to compute n, phi(n), e, and d. To find d, you may
use the standard library modular inverse function, i.e. BigInteger.modInverse(). For all other places where
modular exponentiation is required, you will use your own implementation of the fast modular
exponentiation algorithm.

You will want to use the BigInteger (https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigInteger.html) class for this program.


## Password manager client

You will extend the Client.java file to have a user interface and to correctly handle passwords. The
interface should allow the user to store, retrieve, or end the program in any order at any point.
The interface should gracefully handle erroneous inputs and received messages.

The client has three possible network messages that it will send to the server:
- store website password
- get website
- end

Where store tells the server to store the password for the website, get tells the server to send
back the password for the specified website, and end tells the server to end the program.

The following an example output and input for the client:

```
Welcome to the SENG2250 password manager client, you have the following options:
- store <website> <password>
- get <website>
- end

>>> store example.com correct horse battery staple
Password successfully stored.

You have the following options:
- store <website> <password>
- get <website>
- end

>>> get example.com
correct horse battery staple

You have the following options:
- store <website> <password>
- get <website>
- end

>>> end
bye.
```


## Password manager server

You will extend the Server.java file to handle the store and get commands received from the client.

When the server receives a store command, it will store the specified password such that it is
mapped to the specified website. Afterwards it will send back the message
"Password successfully stored."

When the server receives a get commend, it will send back only the password that is mapped to the
specified website.

The end command is already handled in the code you are extending, but its specifications are that
on receiving an end command it will send back "end okay" and quit the program.


## Running the programs

The programs should be compiled only with javac, i.e. with the `javac *.java` command. The server
and client are started in separate command prompts/terminals. The server starts first and looks for
clients, the client, when started, will connect to the server if it is online.
