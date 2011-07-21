#!/bin/bash
case=$1
docket=AOC$(date +%Y%m%d%H%M)

echo "Docketing with $docket"

. ./gajews.settings.sh

url=$SERVICE_URL

alias pretty_print="python -c 'import sys; from lxml import etree; print etree.tostring(etree.fromstring(sys.stdin.read()),pretty_print=True); '"
alias send_soap_msg="curl -s --user $USERNAME:$PASSWORD -k"

echo "Calling $SERVICE_URL with acceptFiling"
echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \
	<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"http://GAJEWS.gaaoc.us/types/\"> \
   <soapenv:Header/> \
   <soapenv:Body> \
      <typ:acceptFiling> \
         <uuid>$case</uuid> \
	 <docket>$docket</docket> \         
         <documents> \
              $(cat $case.documents.xml) \
         </documents> \
      </typ:acceptFiling> \
   </soapenv:Body> \
</soapenv:Envelope>" | send_soap_msg -d @- $url | pretty_print


if [ -e $case.newdocuments.xml ]; then
	echo "Calling $SERVICE_URL with addDocument"
	echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"http://GAJEWS.gaaoc.us/types/\">
   <soapenv:Header/>
   <soapenv:Body>
      <typ:addDocument>
         <uuid>$case</uuid>
         <!--1 or more repetitions:-->
		$(cat $case.newdocuments.xml)
      </typ:addDocument>
   </soapenv:Body>
</soapenv:Envelope>" | send_soap_msg -d @- $url | pretty_print
fi




