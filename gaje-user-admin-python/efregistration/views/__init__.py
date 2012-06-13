from django.views.generic import TemplateView

class BeginView(TemplateView):
      template_name = "register/start.html"

class AttorneyView(TemplateView):
      template_name = "register/attorney.html"

class ClerkView(TemplateView):
      template_name = "register/clerk.html"



class SuccessView(TemplateView):
	template_name = "register/success.html"
