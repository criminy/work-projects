"""
	Attorney registration page view handlers
"""


from django.http import HttpResponse,HttpResponseRedirect
from django.shortcuts import render_to_response,redirect
from django.contrib.auth.decorators import login_required
from django.utils.decorators import method_decorator
from django.views.generic.base import View
from django.views.generic import TemplateView
from django.contrib.formtools.wizard import FormWizard
from django.template.context import Context
from django.utils.html import escape

from efregistration.forms.attorney import *
from efregistration.views.util import *
from efregistration.models import *

import uuid



def att_index(request):
     return HttpResponseRedirect("attorney/" + str(uuid.uuid4()).replace('-','') + "/0")


class AttWizard(CacheFormWizard):
	"""
		Wizard for adding a new attorney in both testing and production systems.

		Navigation is:
			1. Initial form -> Finish
		
	"""

	template_name = "register/lp/wizard.html"
	name = "attwizard"

	def get_initial_forms(self):
		return [AttorneyForm,]

	def get_context_data(self,request,idx,key):
		return {
			"title": str(self.current_form.__title__()),
			"forms":self.forms
			}

	def done(self,request,forms):
		forms[0].persist(forms[1:],db='testing')
		forms[0].persist(forms[1:],db='production')
		forms[0].ldap_persist(forms[1:])

		return HttpResponseRedirect("/register/success")
