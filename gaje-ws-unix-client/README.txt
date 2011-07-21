
# gaje-ws-unix-client

This is a command line unix client to our GAJE_ImportWS webservice.

## Setup

You need the debian packages:
 
    python-lxml
    curl

Copy the *gajews.example.settings.sh* file to *gajews.settings.sh*

## Get Queued Filings

To setup, update the username and password fields in the .sh files
and the url field in the .sh files to the proper values. 

To run, use the command:

   sh get_queued_filings.sh courtId

where courtId is the ID of the court given to the vendor, typically
houstoncounty,bibbcounty,clarkecounty.

You will get an XML dump of the data to standard output.

## Import Case

To setup, update the username and password fields in the .sh files
and the url field in the .sh files to the proper values. 

This command imports the case and downloads the documents into
the folder: documents/

To run, use the command:

    sh import_case.sh caseRecordUuid cvNumber

where caseRecordUuid is the uuid of the case and cvNumber is the 
new civil action number of the case.




