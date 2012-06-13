#!/usr/bin/python

import fnmatch
import os

import glob
import lxml.etree as etree
import sys

namespaces = {"pom":"http://maven.apache.org/POM/4.0.0"}

def verify_parent_connection(parent,this_parent):
	if this_parent != None and (parent["groupId"] != this_parent["groupId"] or parent["version"] != this_parent["version"] or parent["artifactId"] != this_parent["artifactId"]):
	     return ("Invalid Parent",
"""\tdirectory parent %s  (this_parent)
\tXML parent %s (parent)""" % (str(this_parent), str(parent) ))
        else:
	     return ("OK",None)


def verify_custom_naming(parent,self):
	"""group of the child should be the group of the parent + the artifact of the parent"""
        if parent["groupId"] == "": 
            return "OK",None
        if self["groupId"] == parent["groupId"] + "." +  parent["artifactId"]: 
            return "OK",None
        else:
            return ("Invalid child name",
"""\tparent: %s
\tchild: %s""" % (parent["groupId"] + "." +  parent["artifactId"],self["groupId"]))

auto_correct = True

fields = ["groupId","artifactId","version"]

def get_maven_object(pom_xml,root_xpath,version = ""):
          self = {}
          for x in fields:
              nname = x + ".node"
              self[nname] = pom_xml.xpath( root_xpath + "/pom:%s" % (x) ,namespaces = namespaces)
              self[nname] = self[nname][0] if len(self[nname]) != 0 else None
              self[x] = self[nname].text if self[nname] != None else ( version if x == "version" else "" )
          return self


def call_check(func,message,on_fail,self):
          status,extra = func()
	  message = "%30s %10s %30s %10s" % (message,self["artifactId"][0:9],self["directory"][0:29],status)
          print message
	  if extra:
              print extra

          if status != "OK":
             on_fail()


def find_and_verify_pom(directory,this_parent=None):
	pom_xml = etree.parse(directory + "/pom.xml")
       
	self = get_maven_object(pom_xml,"/pom:project",this_parent["version"] if this_parent != None else "")
	self["directory"] = directory
	parent = get_maven_object(pom_xml,"/pom:project/pom:parent","")

	def correct_parent_connection():
		  if auto_correct == True:
		     nparent = {}
		     nparent = get_maven_object(pom_xml,"/pom:project/pom:parent","")         
		     for x in fields:
			 nparent[x + ".node"].text = this_parent[x]
		      
		     contents = etree.tostring(pom_xml,pretty_print = True)
		     f = open(directory + "/pom.xml","w")
		     f.write(contents)
		     f.close()

        def v():
             return verify_parent_connection(parent,this_parent)
 
	call_check(v,"Checking Parent Connections",correct_parent_connection,self)

        def correct_groupId_naming():      
            if auto_correct == True:
                self["groupId.node"].text = parent["groupId"] + "." + parent["artifactId"]
 
                contents = etree.tostring(pom_xml,pretty_print = True)
                f = open(directory + "/pom.xml","w")
                f.write(contents)
                f.close()

        def y():
	     return verify_custom_naming(parent,self)

        call_check(y,"Verifying groupId naming",correct_groupId_naming,self)

#        def z():
#            return update_description(parent,self)

#        call_check(y,"Updating description","",self)

	# find children poms
	children = [ x[:-len("/pom.xml")] for x in glob.glob(directory + "/*/pom.xml") ]
	for x in children:
		find_and_verify_pom(x,self)


find_and_verify_pom(".")
