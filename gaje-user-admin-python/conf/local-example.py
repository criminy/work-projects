# These are local settings that shouldn't be in SVN 

# The root of the classpath to add when running inside of CHERRYPY (only needs to be set when using run.py)
ROOT = 

# The port to listen on when starting the CherryPY server (only needs to be set when using run.py)
PORT = 8000

# The authentication backend for AD
# 	Comment this line out to disable ActiveDirectory authentication
# 	support. Also comment out the AD_ settings if 
#	you comment this one out.
#AUTHENTICATION_BACKENDS = ('efadmin.util.auth.ActiveDirectoryBackend',)

# The class for integrating our system with openLDAP
# 	Enable the 'dummy' version if you are testing
#	without an LDAP server to connect to. You can also
# 	comment out or delete the OPENLDAP_ settings below when
#	using DummyOpenLDAPIntegration.

OPENLDAP_SERVICE = 'efadmin.util.dummy.DummyOpenLDAPIntegration'
#OPENLDAP_SERVICE = 'efadmin.util.ldap.OpenLDAPIntegration'


# Hostname or IP of the active directory server
AD_DNS_NAME = ''

# port of the active directory server
AD_LDAP_PORT = 389

# DN in AD to search for users
AD_SEARCH_DN = 'OU=,DC=,dc=,dc='

# DN to authenticate with the server
AD_BIND_DN = "cn=,ou=,dc=,dc=,dc="

# The password of the authentication DN
AD_BIND_PASSWORD = ""

# Domain of the network
AD_NT4_DOMAIN = ''

# Fields to search for when searching for and grabbing users
AD_SEARCH_FIELDS = ['mail','givenName','sn','sAMAccountName']

# The final AD url
AD_LDAP_URL = 'ldap://%s:%s' % (AD_DNS_NAME,AD_LDAP_PORT)

OPENLDAP_HOSTNAME = ''
OPENLDAP_PORT = 389
OPENLDAP_USER = 'cn=,dc=,dc='
OPENLDAP_PASS = ''


SECRET_KEY=""


DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.mysql',
        'NAME': 'GAJE_USER_REGISTRATION',    
        'USER': '',                     
        'PASSWORD': '',               
        'HOST': '',  
        'PORT': '',     
    },
    'testing': {
	'ENGINE':'django.db.backends.mysql',
        'NAME':'GAJE_TEST',
        'USER':'',
        'PASSWORD':'',
        'HOST':'',
        'PORT':'',
    },  
    'production': {
	'ENGINE':'django.db.backends.mysql',
        'NAME':'GAJE_PROD',
        'USER':'',
        'PASSWORD':'',
        'HOST':'',
        'PORT':'',
    },  

}

