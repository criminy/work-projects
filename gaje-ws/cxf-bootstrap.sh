# This script uses apache CXF to generate the server interfaces and the
# jaxb bindings from the WSDL. The implementation class is not generated,
# however, as we don't want our code overwritten with a generated one.
CXF_HOME=/home/$USER/Build/apache-cxf/
GAJE_HOME=/srv/gaje/
CLASSPATH=$CXF_HOME/lib/* $CXF_HOME/bin/wsdl2java -d src/main/java/ -wsdlLocation classpath:GAJE_ImportWS.wsdl src/main/resources/GAJE_ImportWS.wsdl
