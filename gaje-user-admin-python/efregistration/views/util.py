from django.http import HttpResponse,HttpResponseRedirect
from django.shortcuts import render_to_response,redirect
from django.contrib.auth.decorators import login_required
from django.utils.decorators import method_decorator
from django.views.generic.base import View
from django.core.cache import cache

class CacheFormWizard(View):
        """
		Form wizard which uses django cache as the backend form storage.

		Override 'done' for running code when the form completes.
                 
                Override 'step' to update steps.

                Required: Implement 'get_initial_forms' to return a list of classes for the initial form values.

		Path or Request Parameters:
			key
			idx
	"""

	def cache_get(self,k,default=None):
		return cache.get(self.key + "-" + k,default)

        def cache_set(self,k,value):
                cache.set(self.key + "-" + k,value)
      
        def cache_delete(self,k):
                cache.delete(self.key + "-" + k)

        def dispatch(self, *args, **kwargs):
                if "key" in kwargs :
                    self.key = kwargs["key"]
                else:
		    self.key = str(uuid.uuid4()).replace("-","")

                self.forms = cache.get(self.key + "-forms",[]) 
		self.data = cache.get(self.key,[])
                
                if len(self.forms) == 0:
                    self.forms = self.get_initial_forms()

        	return super(CacheFormWizard, self).dispatch(*args, **kwargs)

        name = ""
	timeout = 5 * 60

	def post(self,request,*args,**kwargs):

	    
            idx = int(kwargs["idx"])
	    self.current_form = self.forms[idx](request.POST,request.FILES)
            self.init_form(request,self.current_form,idx)

	    if not self.current_form.is_valid():
		return self.get(request,self.current_form,*args,**kwargs)

	    self.step(request,self.current_form,idx)
            cache.set(self.key + "-forms",self.forms,self.timeout)
  
	    if len(self.data) > idx:
            	self.data[idx] = self.current_form
            else:
	        self.data.append(self.current_form)
	    cache.set(self.key,self.data,self.timeout)

	    if idx+1 >= len(self.forms):
                ret = self.done(request,self.data)
                cache.delete(self.key)
                cache.delete(self.key + "-forms")
                return ret

            return redirect(self.name,self.key,str(idx+1))

        def init_form(self,request,form,step):
            pass

        def done(self,request,forms):
            return HttpResponse("Wizard " + self.name + " has completed")

        def get(self,request,form=None,*args,**kwargs):
                idx = int(kwargs["idx"])

                form = form if form is not None else self.forms[idx]()
                self.current_form = form 
                self.init_form(request,form,idx)
                ctx = self.get_context_data(request,*args,**kwargs)
                ctx["form"] = form
		return render_to_response(self.template_name,ctx)

        def step(self,request,form,step):
            pass

        def get_context_data(self,request,*args,**kwargs):
            return {}


class ProtectedView(View):
	""" 
	    Mixin class for applying login-required to a 
	    view-class.
	"""
	@method_decorator(login_required)
	def dispatch(self, *args, **kwargs):
        	return super(ProtectedView, self).dispatch(*args, **kwargs)

class TemplateFormView(View):
        """
            Abstract class that supports standard Form 
            processing AND standard template rendering.

	"""

        form = None

	def post(self,request,*args,**kwargs):
                assert self.form != None
		form = self.build_bound_form(request)
		if not form.is_valid():
			return self.get(request,form)

		return self.valid(request,form,*args,**kwargs)

        def get(self,request=None,form=None,*args,**kwargs):
                assert self.form != None
                form = form if form is not None else self.build_simple_form(request)
                if "setup" in form.fields:
                      form.fields.setup(*args,**kwargs)
                ctx = self.get_context_data(request,*args,**kwargs)
                ctx["form"] = form
		return render_to_response(self.template_name,ctx)

        def build_bound_form(self,request):
            return self.form(request.POST,request.FILES)

        def get_context_data(self,request):
            return {}

        def build_simple_form(self,request):
            """
            Constructs a simple,unbound form
            """
            return self.form()

        def valid(self,request,form):
            """
            Callback used when a form post is successful. Does nothing by default
            """
            pass


