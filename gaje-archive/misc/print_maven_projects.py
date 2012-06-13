#!/usr/bin/python

import fnmatch
import os

import glob
import lxml.etree as etree
import sys

namespaces = {"pom":"http://maven.apache.org/POM/4.0.0"}

print_groupid = False if "--ignore-groupid" in sys.argv else True
print_version = False if "--ignore-version" in sys.argv else True
print_artifactid = False if "--ignore-artifactid" in sys.argv else True
print_parent = True if "--show-parents" in sys.argv else False

# Find all pom.xml files

def print_pom_by_structure(directory,nesting="",parent_version=""):
	  pom_xml = etree.parse(directory + "/pom.xml")
	  group_id = pom_xml.xpath("/pom:project/pom:groupId/text()",namespaces=namespaces)
	  artifact_id = pom_xml.xpath("/pom:project/pom:artifactId/text()",namespaces=namespaces)
	  version = pom_xml.xpath("/pom:project/pom:version/text()",namespaces=namespaces)

	  version = version[0] if len(version) != 0 else parent_version
	  group_id = group_id[0] if len(group_id) != 0 else ""
	  artifact_id = artifact_id[0] if len(artifact_id) != 0 else ""

          parent_out = ""
	  if print_parent:
               parent_group_id = pom_xml.xpath("/pom:project/pom:parent/pom:groupId/text()",namespaces=namespaces)
               parent_artifact_id = pom_xml.xpath("/pom:project/pom:parent/pom:artifactId/text()",namespaces=namespaces)
               parent_version = pom_xml.xpath("/pom:project/pom:parent/pom:version/text()",namespaces=namespaces)

               parent_group_id = parent_group_id[0] if len(parent_group_id) != 0 else ""
               parent_artifact_id = parent_artifact_id[0] if len(parent_artifact_id) != 0 else ""
               parent_version = parent_version[0] if len(parent_version) != 0 else ""
 
               parent_out = parent_group_id + " " + parent_artifact_id + " " + parent_version
              
	  out = nesting
          out = out + " " + (group_id if print_groupid else "")
          out = out + " " + (artifact_id if print_artifactid else "")
          out = out + " " + (version if print_version else "")

	  print out, "[parent=" + parent_out + "]"

	  children = [ x[:-len("/pom.xml")] for x in glob.glob(directory + "/*/pom.xml") ]
	  for x in children:
		print_pom_by_structure(x,nesting + " ",version)

def print_pom_by_modules(directory,nesting="",parent_version=""):
	  pom_xml = etree.parse(directory + "/pom.xml")
	  group_id = pom_xml.xpath("/pom:project/pom:groupId/text()",namespaces=namespaces)
	  artifact_id = pom_xml.xpath("/pom:project/pom:artifactId/text()",namespaces=namespaces)
	  version = pom_xml.xpath("/pom:project/pom:version/text()",namespaces=namespaces)

	  version = version[0] if len(version) != 0 else parent_version
	  group_id = group_id[0] if len(group_id) != 0 else ""
	  artifact_id = artifact_id[0] if len(artifact_id) != 0 else ""

          parent_out = ""
	  if print_parent:
               parent_group_id = pom_xml.xpath("/pom:project/pom:parent/pom:groupId/text()",namespaces=namespaces)
               parent_artifact_id = pom_xml.xpath("/pom:project/pom:parent/pom:artifactId/text()",namespaces=namespaces)
               parent_version = pom_xml.xpath("/pom:project/pom:parent/pom:version/text()",namespaces=namespaces)

               parent_group_id = parent_group_id[0] if len(parent_group_id) != 0 else ""
               parent_artifact_id = parent_artifact_id[0] if len(parent_artifact_id) != 0 else ""
               parent_version = parent_version[0] if len(parent_version) != 0 else ""
 
               parent_out = parent_group_id + " " + parent_artifact_id + " " + parent_version
              
	  out = nesting
          out = out + " " + (group_id if print_groupid else "")
          out = out + " " + (artifact_id if print_artifactid else "")
          out = out + " " + (version if print_version else "")

	  print out, "[parent=" + parent_out + "]"

	  children = [ x.text for x in pom_xml.xpath("/pom:project/pom:modules/pom:module",namespaces=namespaces) ]
	  for x in children:
		print_pom_by_modules(directory + "/" + x,nesting + " ",version)




print_pom_by_modules(".")
print ""
print_pom_by_structure(".")
