from cherrypy import wsgiserver
import sys
import os
import django.core.handlers.wsgi
 
if __name__ == "__main__":
    # Setup paths - a bit hackish, but works for me.
    # Assumes an absolute path is stored in <project>.conf.local.ROOT
    sys.path.append(os.path.realpath(os.path.dirname(__file__)))
    from conf.local import ROOT, PORT
    sys.path.append(ROOT)
 
    # Startup Django
    os.environ['DJANGO_SETTINGS_MODULE'] = 'efadmin.settings'
    server = wsgiserver.CherryPyWSGIServer(
        ('0.0.0.0', PORT),  # Use '127.0.0.1' to only bind to the localhost
        django.core.handlers.wsgi.WSGIHandler()
    )
    try:
        server.start()
    except KeyboardInterrupt:
        print 'Stopping'
        server.stop()
