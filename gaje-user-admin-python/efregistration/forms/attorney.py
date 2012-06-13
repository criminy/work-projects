from django import forms
from efregistration.models import *

from django.conf import settings

from efadmin.util import import_fqdn

import ldap

LDAP_SERVICE_CLASS = settings.OPENLDAP_SERVICE.split(".")[-1]
LDAP_SERVICE_PKG = ".".join(settings.OPENLDAP_SERVICE.split(".")[:-1])
ldap_integration_mod = __import__(LDAP_SERVICE_PKG, globals(), locals(), [LDAP_SERVICE_CLASS])
ldap_integration_class = getattr(ldap_integration_mod,LDAP_SERVICE_CLASS)
ldap_integration = ldap_integration_class()

class AttorneyForm(forms.Form):
	""" Initial form for the attorney data """
	username = forms.CharField(max_length=40)
	password = forms.CharField(max_length=40)

	barId = forms.CharField(max_length=40)

	first_name = forms.CharField(max_length=40)
	middle_name = forms.CharField(max_length=40,required=False)
	last_name = forms.CharField(max_length=40)
        signature_visual_marks = forms.CharField(max_length=100)

	testing_organizations = forms.ModelMultipleChoiceField(queryset=Organization.objects.using('testing').filter(uuid__startswith='initiatingPartyOrganizationUuid'))

        production_organizations = forms.ModelMultipleChoiceField(queryset=Organization.objects.using('production').filter(uuid__startswith='initiatingPartyOrganizationUuid'))
	        

        mapping = {"username":"userid",
                   "first_name":"firstname",
                   "middle_name":"middlename",
                   "last_name":"lastname",
                   "password":"password"}

        def clean_username(self):
	     """method for cleaning the username field and making sure it is unique"""
	     userid = self.cleaned_data["username"]
	     if len(Person.objects.using('testing').filter(userid=userid)) != 0 and len(Person.objects.using('production').filter(userid=userid)) != 0:
                    raise forms.ValidationError("The username is already taken!")

             return userid

        def to_personorgs(self,person,db='testing'):
		for x in self.cleaned_data[db + '_organizations']:
		    po = Personorganizations.objects.using(db).create(
			    personuuid = person,
			    organizationuuid = x,
			    courtuuid = 'courtUuid' + x.uuid[len('initiatingPartyOrganizationUuid'):],
			    uuid = 'po_' + x.uuid[len('initiatingPartyOrganizationUuid'):] + "_" + person.uuid
		    )


        def persist(self,second_forms,db='testing'):

	     # Populate person object
             kwargs = {'changepwdflag':0,'deleteuserflag':0,
		 'courtscomment':'',
                 'setcivilactionnumber':0,
                 'setcourtdate':0,
                 'uuid':"user_" + self.cleaned_data['username'] + self.cleaned_data['last_name']
	     }

             for x in self.mapping:
                kwargs[self.mapping[x]] = self.cleaned_data[x]
                                   
             person = Person.objects.using(db).create(**kwargs)
             ##################

             # create personROle object
             role = Role.objects.using(db).filter(role = 'attorney')[0]
             Personroles.objects.using(db).create(personuuid = person,roleuuid = role)

             # create person organizations objects
	     self.to_personorgs(person,db)

             # create visual mark objects
             marks = self.cleaned_data["signature_visual_marks"].split(",")
             i = 0
             cattcode = Visualmarkcategory.objects.using(db).get(pk=1)
             for x in marks:
                     vm = Visualmark.objects.using(db).create(
                              uuid = 'att_' + person.uuid + '_' + str(i),
                              defaultflag = 1 if i == 0 else 0,
                              visualcategorycode = cattcode,
                              personuuid = person,
                              visualtarget=x)
                     i = i + 1
             
	     # create default 'This the ' target
             datecattcode = Visualmarkcategory.objects.using(db).get(pk=4)
             Visualmark.objects.using(db).create(
                    uuid = 'att_' + person.uuid + "_date1", 
                    defaultflag = 1,
                    visualcategorycode = datecattcode,
                    personuuid = person,
                    visualtarget = "This the")

	     # create attorney objects
             for x in self.cleaned_data[db + "_organizations"]:
 		     att = Attorney.objects.using(db).create(
                	      uuid = 'att_' + x.uuid + '_' + person.uuid,
   			      person = person,
	                      organizationuuid = x,
			      barid = self.cleaned_data["barId"])


        def ldap_persist(self,forms):
             ldap_integration.create_entity_from_forms(forms)

        def __title__(self): 
            return "Create a new Attorney"

