#!/bin/bash

#### From startup.sh in apache-tomcat-6
# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=$(dirname $(readlink -f "$PRG"))
#TODO: make a if not efiling_root then set efiling_root clause
EFILING_ROOT=$(readlink -f "$PRGDIR/..")

#TODO: only load setenv.sh if setenv.sh exists.
. $EFILING_ROOT/bin/setenv.sh

echo "Running $EFILING_ROOT"

RUN_PARAMS="-Defiling.root=$EFILING_ROOT -Defiling.configfile=$EFILING_ROOT/conf/credentials.properties"

ACTIVEMQ_BASE=$EFILING_ROOT $ACTIVEMQ_HOME/bin/activemq start $RUN_PARAMS
