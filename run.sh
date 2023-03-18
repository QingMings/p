#!/bin/bash
java -Dvertx.hazelcast.config=./config/cluster.xml \
 -Dhazelcast.local.publicAddress=10.211.55.2  -jar ./target/p-1.0.0-all.jar   -cluster
