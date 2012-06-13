# These are local settings that shouldn't be in SVN 


# This is a development version of the configuration, which
# allows us to run everything from within python and without
# external database dependencies.

ROOT=
PORT=8000

OPENLDAP_SERVICE = ('efadmin.util.dummy.DummyOpenLDAPIntegration',)
SECRET_KEY=""

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.sqlite3',
        'NAME': 'data/default_db',    
        'USER': '',                     
        'PASSWORD': '',               
        'HOST': '',  
        'PORT': '',     
    },
    'testing': {
	'ENGINE':'django.db.backends.sqlite3',
        'NAME':'data/testing_db',
        'USER':'',
        'PASSWORD':'',
        'HOST':'',
        'PORT':'',
    },  
    'production': {
	'ENGINE':'django.db.backends.sqlite3',
        'NAME':'data/prod_db',
        'USER':'',
        'PASSWORD':'',
        'HOST':'',
        'PORT':'',
    },  

}

