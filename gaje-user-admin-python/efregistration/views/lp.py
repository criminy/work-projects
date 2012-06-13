"""
Legal preperation page registration view handlers
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

from efregistration.forms import *
from efregistration.forms.legalpreparer import *
from efregistration.views.util import *
from efregistration.models import *

import uuid

def lp_index(request):
     return HttpResponseRedirect("lp/" + str(uuid.uuid4()).replace('-','') + "/0")

class LpWizard(CacheFormWizard):
      """ 
	Wizard for adding a new legal preparer in both the testing and the production systems.

	Navigation is:
		1 InitialDataForm -> 1..N AssociateAttorneyFormsForTesting -> 1..N AssociateAttorneyFormsForProduction -> Finish

	This wizard class starts with the Initial form (function get_initial_forms below). 
	After the initial form is submitted, the list of forms are expanded 
		based on the first forms input data (function step below).
        The input data from the first form is given to the next forms (function init_form).
	After the wizard is completed, the form is converted into database objects (function done)
      """
      template_name = "register/lp/wizard.html"
      name = "lpwizard"


      def get_initial_forms(self):
	""" Gives the initial forms for the wizard """
        return [LegalPreparerForm,]

      def get_context_data(self,request,idx,key):
        """ Populate the context data """
	return {
		"title":
			str(self.current_form.__title__()),
		"forms":self.forms
		}

      def init_form(self,request,form,step):
        """ Adds additional data to required forms """
        if isinstance(form,LegalPreparerForm2):
          form.__setup__(self.data[0].cleaned_data["testing_organizations"][step-1])


	# We have to offset here because 'step' may equal 3 but that is wanting the
        # first or second item in the production_organizations list.
	if isinstance(form,LegalPreparerForm3):
          offset = len(self.data[0].cleaned_data["testing_organizations"])
          form.__setup__(self.data[0].cleaned_data["production_organizations"][step - offset - 1])

      def step(self,request,form,step):
        """ Called after every form submit """
        if isinstance(form,LegalPreparerForm):
	  self.forms = self.get_initial_forms()
          for x in form.cleaned_data["testing_organizations"]:
            self.forms.append(LegalPreparerForm2)

          for x in form.cleaned_data["production_organizations"]:
            self.forms.append(LegalPreparerForm3)

      def done(self,request,forms):
        """ Persist the form data, after the wizard is complete, and returns an http response """

        forms[0].persist(forms[1:],db='testing')
        forms[0].persist(forms[1:],db='production')
	return HttpResponseRedirect("/register/success")

