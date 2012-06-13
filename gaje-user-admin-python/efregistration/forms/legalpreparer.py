from django import forms
from efregistration.models import *

from django.db import transaction

class LegalPreparerForm(forms.Form):
	""" Initial form for the legal preparer data """
	username = forms.CharField(max_length=40)
	password = forms.CharField(max_length=40)

	first_name = forms.CharField(max_length=40)
	middle_name = forms.CharField(max_length=40,required=False)
	last_name = forms.CharField(max_length=40)
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


        def to_personorgs(self,person,db):
             for x in self.cleaned_data[db + '_organizations']:
                 po = Personorganizations.objects.using(db).create(
	                 personuuid = person,
        	         organizationuuid = x,
                	 courtuuid = 'courtUuid' + x.uuid[len('initiatingPartyOrganizationUuid'):],
                         uuid = 'po_' + x.uuid[len('initiatingPartyOrganizationUuid'):] + "_" + person.uuid
		 )

        def persist(self,second_forms,db):

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
             role = Role.objects.using(db).filter(role = 'agent')[0]
             Personroles.objects.using(db).create(personuuid = person,roleuuid = role)

             # create person organizations objects
	     self.to_personorgs(person,db)
	
	     # create legal preparer objects
             for form2 in second_forms:
                if  db == 'testing' and isinstance(form2,LegalPreparerForm2)  or \
		    db == 'production' and isinstance(form2,LegalPreparerForm3): 
	                for x in form2.cleaned_data[db + '_receiving_attornies']:
        	           lp = Legalpreparer.objects.using(db).create(
                	      uuid = 'lp_' + x.uuid + '_' + person.uuid,
   			      personuuid = person,
	                      orguuid = form2.org,
        	              attorneyuuid = x)

        def __title__(self): 
            return "Create a new Legal Preparer"

class LegalPreparerForm2(forms.Form):
	""" Form for associating attornies with the currently editing legal preparer """

        testing_receiving_attornies = \
			forms.ModelMultipleChoiceField(queryset=Attorney.objects.none())

        def __setup__(self,org=None):
                self.org = org
		self.fields["testing_receiving_attornies"].queryset = Attorney.objects.using('testing') \
			.filter(organizationuuid = org)

	def __title__(self):
                return "Attornies in " + str(self.org) + " for testing.gaje.us"


class LegalPreparerForm3(forms.Form):
	""" Form for associating attornies with the currently editing legal preparer """

        production_receiving_attornies = \
			forms.ModelMultipleChoiceField(queryset=Attorney.objects.none())

	def __setup__(self,org=None):
		self.org = org
		self.fields['production_receiving_attornies'].queryset = Attorney.objects.using('production') \
			.filter(organizationuuid = org)

	def __title__(self):
		return "Attornies in " + str(self.org) + " for www.gaje.us"
