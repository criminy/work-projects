from django.conf.urls.defaults import patterns, include, url

from django.contrib import admin
admin.autodiscover()

from efregistration.views.attorney import *
from efregistration.views.lp import *
from efregistration.views import *

from efregistration.forms import *

from django.conf import settings
from django.conf.urls.static import static

from django.contrib.auth.decorators import login_required

urlpatterns = patterns('',
    # Front page view
    url(r'^register$',login_required(BeginView.as_view()),name="index"),

    url(r'^register/success',login_required(SuccessView.as_view()),name="success"),

    # legal preparer registration
    url(r'^register/lp$',login_required(lp_index),name="lp"),
    url(r'^register/lp/(?P<key>\w+)/(?P<idx>\d+)$', login_required(LpWizard.as_view()),name="lpwizard"),

    # attorney registration
    url(r'^register/attorney$',login_required(att_index),name="attorney"),
    url(r'^register/attorney/(?P<key>\w+)/(?P<idx>\d+)$',login_required(AttWizard.as_view()),name="attwizard"),

    # Administration
    url(r'^admin/', include(admin.site.urls)),

    # Authentication handler
    url(r'^accounts/login/$', 'django.contrib.auth.views.login', {'template_name': 'gaje/login.html'},name="login"),
    url(r'^accounts/logout/$', 'django.contrib.auth.views.logout',{'template_name':'gaje/logged_out.html'},name="logout"),

) + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)

