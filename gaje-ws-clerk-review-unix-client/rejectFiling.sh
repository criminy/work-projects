#!/bin/bash
case=$1

. ./gajews.settings.sh

url=$SERVICE_URL

alias pretty_print="python -c 'import sys; from lxml import etree; print etree.tostring(etree.fromstring(sys.stdin.read()),pretty_print=True); '"
alias send_soap_msg="curl -s --user $USERNAME:$PASSWORD -k"

echo "Calling $SERVICE_URL with rejectFiling"
echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \
	<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"http://GAJEWS.gaaoc.us/types/\"> \
   <soapenv:Header/> \
   <soapenv:Body> \
      <typ:rejectFiling> \
         <uuid>$case</uuid> \
	 <rejectionReason>Rejected on $(date +%Y-%m-%d-%H-%M)</rejectionReason> \         
      </typ:rejectFiling> \
   </soapenv:Body> \
</soapenv:Envelope>" | send_soap_msg -d @- $url | pretty_print


