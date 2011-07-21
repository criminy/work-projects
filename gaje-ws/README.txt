
This is an implementation of the GAJE Import webservice using 
Spring,  
Hibernate (as the JPA implementation), 
Apache CXF (as the JAXWS implementation),
Groovy (for dynamic court stamping logic),
and JAXB (for pojo->xml schema binding).

The webservice can be run in any standard servlet container.

To auto-build the dependencies, run:

    sh svn-builddeps.sh

To build this project as a web project, use:

    mvn compile package

The war will be placed in target/

To run unit tests, use:

    mvn test

Test errors are placed in target/surefire-reports/

To test the web service, you can deploy the war into tomcat or use:

    mvn jetty:run

or

    mvn tomcat:run

to test from within maven.
Install scripts from src/main/groovy/ into /srv/gaje/scripts/ws/.
This is dynamic logic that can be changed at runtime.


To add users/vendors, put them into the LDAP server and give them court credentials in the SQL table gaje_ws_court_auth_vendor_access

