from fabric.api import *
import time
import os

def current_date():
	current_time = time.localtime()
	return "%s%s%s_%s%s" % (
		current_time.tm_year, 
		current_time.tm_mon,
		current_time.tm_mday,
		current_time.tm_hour,
		current_time.tm_min)

config_cvsroot=":extssh:cvs.gaje.us:/var/lib/cvsroot.gaje/"

gaje_ws_version="3.1.6"

def svn_get(src,dst):
    def _f():
	local("wget --no-check-certificate --output-document=%s %s" % (dst,src))
    return _f

def cvs_get(remote_file, to_location):
    def _f():
        local("cvs -d%s checkout -p %s > %s" % (config_cvsroot,remote_file,to_location))
    return _f

projects = {
	"gaje-ws": {
                "build":"mvn install", 
		"folder":".",
		"warname":"target/gaje-ws-%s.war" % (gaje_ws_version),
		"destination":"gaje-ws-%s-%s.war" % (gaje_ws_version,current_date()),
		"hosts":{
			"w2.gaje.us": {
				"url":"http://w2.gaje.us:62808/ws",
				"conf":"/srv/webapps/conf/gaje-ws.sh",
				"files":
				[
					{"command":cvs_get("server-configuration/gajetest/efiling/gaje-ws/testing/project.properties","src/main/resources/project.properties")},
					{"command":cvs_get("server-configuration/gajetest/efiling/gaje-ws/testing/log4j.properties","src/main/resources/log4j.properties")},					
					{"command":svn_get("https://cvs.gaaoc.us/svn/public/gaje-documents/src/main/java/CourtStampControllerImpl.groovy","src/main/java/CourtStampControllerImpl.groovy")}
				]
			},
			"testing.gaje.us": {
				"url":"https://testing.gaje.us/ws",
				"conf":"/srv/webapps/conf/testing/gaje-ws.sh",
				"files":
				[
					{"command":cvs_get("server-configuration/gajeappsrv02/gaje/gaje-ws/staging/log4j.properties","src/main/resources/log4j.properties")},
					{"command":cvs_get("server-configuration/gajeappsrv02/gaje/gaje-ws/staging/project.properties","src/main/resources/project.properties")},					
					{"command":svn_get("https://cvs.gaaoc.us/svn/public/gaje-documents/src/main/java/CourtStampControllerImpl.groovy","src/main/java/CourtStampControllerImpl.groovy")}
				]
			},
			"www.gaje.us": {
				"url":"https://www.gaje.us/ws",
				"conf":"/srv/webapps/conf/production/gaje-ws/gaje-ws.sh",
				"files":
				[
					{"command":cvs_get("server-configuration/gajeappsrv02/gaje/gaje-ws/production/log4j.properties","src/main/resources/log4j.properties")},
					{"command":cvs_get("server-configuration/gajeappsrv02/gaje/gaje-ws/production/project.properties","src/main/resources/project.properties")},					
					{"command":svn_get("https://cvs.gaaoc.us/svn/public/gaje-documents/src/main/java/CourtStampControllerImpl.groovy","src/main/java/CourtStampControllerImpl.groovy")}
				]
			}
		}
	}
}

def help():

    print "Projects:"
    for x in projects.keys(): 
	print "\t" + x
        print "\t\tAvailable hosts:"
	for y in projects[x]["hosts"].keys():
		print "\t\t\t" + y
	print "\t\tCommands:"
	print "\t\t\tdiff:" + x + "\t" + diff.__doc__
	print "\t\t\tbuild:" + x + "\t" + build.__doc__
        print "\t\t\tsetup:" + x + "\t" + setup.__doc__
        print "\t\t\tdeploy:" + x + "\t" + deploy.__doc__
	print "\t\t\tstatus:" + x + "\t" + status.__doc__
	print "\t\t\taccess_log:" + x + "\t" + access_log.__doc__



def build(project):
    """Builds the provided project using the build attribute of the project information"""
    project = projects[project]
    local("cd %s " % (project["folder"]) + " && " +  project["build"])

def setup(project):
    """ Prepares the project for the given hostname"""
    project = projects[project]
    for file_command in project["hosts"][env.host]["files"]:
 	file_command["command"]()

def deploy(project):
    """Deploys the server to the provided project and hostname"""
    setup(project)
    build(project)
    project = projects[project]
    folder = project["folder"]
    local_war = project["folder"] + "/" + project["warname"]
    dest_war = "/srv/webapps/incoming/" + project["destination"]
    conf = project["hosts"][env.host]["conf"]

    put(local_war, dest_war) # Transfer the file to the server using SCP
    run('sudo /srv/webapps/bin/deploy_tomcat_war %s %s' % (dest_war,conf)) # Run the script on the remote server

def diff(project):
    """Compares the code on the given server to the local code"""
    setup(project)
    build(project)
    name = project
    project = projects[project]
    folder = project["folder"]
    local_war = project["folder"] + "/" + project["warname"]
    dest_war = "/srv/webapps/incoming/" + project["destination"]
    conf = project["hosts"][env.host]["conf"]
    put(local_war, dest_war) # Transfer the file to the server using SCP
    output = run('sudo /srv/webapps/bin/diff_tomcat_war %s %s' % (dest_war,conf)) # Run the script on the remote server
    filename = "target/" + name + "_" + current_date() + ".diff"
    print "Writing diff to " + filename
    if not os.path.exists("target"):
        os.makedirs("target")
    f = open(filename,"w")
    f.write(output)
    f.close()            

def status(project):
    """Uses the URL line of the host to show the status of the host"""
    project_name = project
    project = projects[project]
    url = project["hosts"][env.host]["url"]
    import urllib
    print "checking url " + url + " for 200 OK"
    if urllib.urlopen(url).getcode() == 200:
        print "Project %s on Server %s Online" % (project_name,env.host)
    else:
        print "Project %s on Server %s Offline or In Error!" % (project_name,env.host)

def access_log(project):
    """Checks the access log of the tomcat instance specified by project"""
    project = projects[project]
    conf = project["hosts"][env.host]["conf"]
    run("(source %s && cd $DEPLOY_TOMCAT_ROOT && tail logs/localhost_access_log.`date +%%Y-%%m-%%d`.log )"  % (conf) );
    run("date")

