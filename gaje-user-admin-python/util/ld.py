import ldap

from django.conf import settings

class OpenLDAPIntegration(object):
	"""Standard OpenLDAP integration component"""

	def create_entity_from_forms(self,forms):
	     # create the person ldap object
	     host = settings.OPENLDAP_HOSTNAME
	     port = settings.OPENLDAP_PORT
	     user = settings.OPENLDAP_USER
	     passw = settings.OPENLDAP_PASS

	     l = ldap.open(host,port)
	     l.simple_bind_s(who=user,cred=passw)
	     modlist = list()
             cn = str(self.cleaned_data["first_name"] + " " + self.cleaned_data["last_name"])
	     modlist.append( ("cn",cn) )
	     modlist.append( ("mail",str(self.cleaned_data["first_name"] + "." + self.cleaned_data["last_name"] + "@gaaoc.us") ))
	     modlist.append( ("objectClass", ("inetOrgPerson","pkiUser") ) )
	     modlist.append( ("ou","people") )
	     modlist.append( ("sn", str(self.cleaned_data['username'])   ) )
	     modlist.append( ("uid", str(self.cleaned_data['username'])  ) )
	     modlist.append( ("userpassword","dummypass") )

             l.add_s("cn=" + cn + ",ou=people,dc=gaje,dc=us",modlist)

	     l.unbind_s()

