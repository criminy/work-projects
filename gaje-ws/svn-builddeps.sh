#!/bin/bash
## Script which checks out the dependencies of this project, builds, and installs them
mkdir -p target/svn
svn co https://cvs.gaaoc.us/svn/public/gaje-model/ target/svn/gaje-model
svn co https://cvs.gaaoc.us/svn/public/gaje-documents/ target/svn/gaje-documents
(cd target/svn/gaje-model/ && mvn install)
(cd target/svn/gaje-documents/ && mvn install)
