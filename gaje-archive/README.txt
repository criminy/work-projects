# gaje-archive

GAJE archive is a project to create a system daemon that archives
PDF documents to backup online and/or offline storage. It does
this by connecting to the GAJE database and document storage
folder, and analyzing metadata about the document. PDF Binaries
which meet a specific criteria are then backed up.

GAJE archive also analyzes data sets for any missing PDFs,
bad cases, etc.


## Using gaje-archive

The simplest way to use gaje-archive is to build the project inside of maven,
and run the created folder structure inside of <list folder>

For this, you need:

	Apache ActiveMQ: 	http://activemq.apache.org/download.html
		Tested with version 5.5.0
	Apache Derby:		http://db.apache.org/derby/derby_downloads.html
		Tested with 10.8.1.2
	Apache Maven 3: 	http://maven.apache.org/download.html
		Tested with 3
	Java JDK 1.6

	
## Building 


To build, use mvn -Pdevelopment clean package. This will build a development version of this project
with an embedded database.

### Build profiles

development - packages the application with an embedded database and 'profile-development' folder.
production - packages the application with the JDBC MySQL driver and 'profile-production' folder.
