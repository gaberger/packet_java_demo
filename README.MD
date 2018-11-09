#### Demo the Packet Java API Using Clojure

## Prerequisites

Must have Java installed on the local machine

## Install Clojure

Follow instructions here https://clojure.org/guides/getting_started


## Dependencies

This demo is build leveraging the Packet JAVA library https://github.com/packethost/packet-java

## Environment setup

In the _resources_ directory you must have a file named *token* with a single string representing your Packet API token

Your directory will look like this...

~~~
.
├── LICENSE
├── README.MD
├── deps.edn
├── resources
│   ├── cloud-config
│   └── token
└── src
    └── packet_java_demo
        └── core.clj
~~~
        

## Running from the command line

~~~
clj -m packet-java-demo.core --help
  -h, --help                             Print this help
  -t, --host HOST          false         Specify hostname
  -o, --os OS              ubuntu_16_04  Specify valid OS
  -f, --facility FACILITY  ewr1          Specify facility
~~~  

Leaving the defaults just specify a _hostname_
  
`clj -m packet-java-demo.core --host <hostname>`



## Application Installation Demo

This repo leveragess Packet's *cloud-init* integration.

We update our device model with a cloud-config file 
        (see packet_java_demo/resources/cloud-config)
        
The example below installs nginx        
        
~~~
#cloud-config
package_upgrade: true
packages:
   -  nginx   
~~~      

## Screencast

https://youtu.be/hVPftYxIJ-0
