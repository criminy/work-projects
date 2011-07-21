#!/bin/bash
case=$1

. ./gajews.settings.sh

url=$SERVICE_URL

alias pretty_print="python -c 'import sys; from lxml import etree; print etree.tostring(etree.fromstring(sys.stdin.read()),pretty_print=True); '"
alias send_soap_msg="curl -s --user $USERNAME:$PASSWORD -k"

echo "Calling $SERVICE_URL"
echo "Removing PDF contents from output"
echo "Saving $case.xml"
echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \
	<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"http://GAJEWS.gaaoc.us/types/\"> \
   <soapenv:Header/> \
   <soapenv:Body> \
      <typ:getCaseFiling> \
         <uuid>$case</uuid> \
      </typ:getCaseFiling> \
   </soapenv:Body> \
</soapenv:Envelope>" | send_soap_msg -d @- $url | pretty_print | tee $case.xml | grep -v "<content>" 

python extract_documents.py documents/$case < $case.xml


