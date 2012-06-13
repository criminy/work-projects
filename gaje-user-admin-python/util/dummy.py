
class DummyOpenLDAPIntegration(object):
	"""OpenLDAP integration which silently does nothing
	   so testing can succeed
	"""

	def create_entity_from_forms(self,forms):
		pass

