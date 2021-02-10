# Link-state routing simulation
This project was developed for the Computer Networks course at unibz.

The aim of this simulation is to show how routing tables are computed given
a topology file in the following form:
```
RouterA RouterB 3
RouterB RouterA 2
RouterC RouterB 1
RouterB RouterC 4
```

The topology file actually represents the link state database, which is typically
constructed by each router via the exchange of link state packets.

The algorithm used for computing the shortest path is the Dijkstra algorithm which
has been implemented with priority queues for better performance.

For more information about link-state routing you can look on [wikipedia](https://en.wikipedia.org/wiki/Link-state_routing_protocol).