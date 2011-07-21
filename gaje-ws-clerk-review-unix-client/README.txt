
# gaje-ws-unix-client

This is a command line unix client to our gaje-ws-clerk-review webservice.

## Setup

You need the debian packages:
 
    python-lxml
    curl

Copy the *gajews.example.settings.sh* file to *gajews.settings.sh*

To setup authentication, update the username and password fields in the .sh files
and the url field in the .sh files to the proper values. 

## Get Queued Filings

To run, use the command:

   sh get_queued_filings.sh courtId

where courtId is the ID of the court given to the vendor, typically
houstoncounty,bibbcounty,clarkecounty,...

You will get an XML dump of the data to standard output.

## Get a case filing

To run, use the command:

   sh get_case_filing UUID

where UUID is the ID of the case. You will get an XML dump of the data
to standard output and UUID.xml, and a dump of the documents into 
the folder documens/UUID/ .

## Accept a case

This command generates a docket number, prints it to standard out, and
then reads the UUID.documents.xml file for document binaries to update
and UUID.newdocuments.xml for additional court provided PDFs to transmit.

To run, use the command:

    sh acceptFiling.sh UUID 

where UUID is the uuid of the case.

## Reject a case

This command rejects a case.

To run, use the command:

    sh rejectFiling.sh UUID

where UUID is the uuid of the case.


