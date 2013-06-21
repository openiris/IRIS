IRIS
====

This is an Openflow Controller software created by ETRI. 
It is using Beacon-like NIO-based event handling mechanism, 
and many core modules ported from Floodlight including:

1. Mac Learning
2. Link Discovery
3. Topology Management
4. Device Management
5. State Management

Though much of IRIS came from Floodlight and Beacon (especially core modules),
the architecure of IRIS is much simpler than that of Floodlight,
which enables easy development of modules and applications. 
Moreover, IRIS provides much better performance. 

IRIS is implemented by Java, and providing REST-based programming interface.
The REST Apis are compliant with that of Floodlight to guarantee the App programmers 
to easily port their Floodlight modules to IRIS.

The complete source code of this project will be released at the end of August. 

Byungjoon Lee


