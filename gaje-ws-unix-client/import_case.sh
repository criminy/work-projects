#!/bin/bash

. ./gajews.settings.sh

url=$SERVICE_URL

alias pretty_print="python -c 'import sys; from lxml import etree; print etree.tostring(etree.fromstring(sys.stdin.read()),pretty_print=True); '"
alias send_soap_msg="curl -s --user $USERNAME:$PASSWORD -k"

case_filing=$1
cv_number=$2

echo "Calling $SERVICE_URL"

# import the case 
echo " \
<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"http://GAJEWS.gaaoc.us/types/\"> \
   <soapenv:Header/> \
   <soapenv:Body> \
      <typ:importAckRequest> \
         <ack> \
            <success>1</success> \
            <uuid>$case_filing</uuid> \
            <caseNum>$cv_number</caseNum> \
         </ack> \
      </typ:importAckRequest> \
   </soapenv:Body> \
</soapenv:Envelope> " | send_soap_msg -d @- $url | pretty_print

# download the documents
echo " \
<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"http://GAJEWS.gaaoc.us/types/\"> \
   <soapenv:Header/> \
   <soapenv:Body> \
      <typ:getDocumentsRequest> \
         <uuid>$case_filing</uuid> \
      </typ:getDocumentsRequest> \
   </soapenv:Body> \
</soapenv:Envelope>" | send_soap_msg -d @- $url | python extract_documents.py documents/$case_filing

# acknowledge the documents
echo " \
<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"http://GAJEWS.gaaoc.us/types/\"> \
   <soapenv:Header/> \
   <soapenv:Body> \
      <typ:docAckRequest> \
         <caseUuid>$case_filing</caseUuid> \
         <docAck>1</docAck> \
      </typ:docAckRequest> \
   </soapenv:Body> \
</soapenv:Envelope> " | send_soap_msg -d @- $url



