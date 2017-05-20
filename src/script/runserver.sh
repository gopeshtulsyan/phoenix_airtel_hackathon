	#!/bin/sh

HOSTIP=`hostname -f`

JAVA_OPTS="-Xmx12g -Duser.timezone=Asia/Calcutta -Dfile.encoding=UTF-8 -Danalytic.properties=server.properties -XX:+HeapDumpOnOutOfMemoryError -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:MaxTenuringThreshold=1 -XX:+AggressiveOpts -XX:+UseFastAccessorMethods -Dio.netty.leakDetectionLevel=SIMPLE";

JMX_OPTS="-Dcom.sun.management.jmxremote.port=8585 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.local.only=false -Djava.rmi.server.hostname=$HOSTIP "

JAVA_DEBUG_OPTS="-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5432 "

CLASSPATH="../classes:../lib/*"

CLASS_NAME="in.wynk.netty.server.NettyHttpServer"

echo "Starting Server: $CLASS_NAME"

java -cp $CLASSPATH $JAVA_OPTS $JMX_OPTS $JAVA_DEBUG_OPTS $JAVA_ENV_OPTS $CLASS_NAME 
