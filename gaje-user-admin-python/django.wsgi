import os
import sys

os.environ['DJANGO_SETTINGS_MODULE'] = 'efadmin.settings'

import django.core.handlers.wsgi
application = django.core.handlers.wsgi.WSGIHandler()

path = '/home/webuser/lib/efadmin/'
if path not in sys.path:
    sys.path.append(path)

