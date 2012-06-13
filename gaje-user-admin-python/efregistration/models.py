# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#     * Rearrange models' order
#     * Make sure each model has one field with primary_key=True
# Feel free to rename the models, but don't rename db_table values or field names.
#
# Also note: You'll have to insert the output of 'django-admin.py sqlcustom [appname]'
# into your database.

from django.db import models

class Court(models.Model):
    uuid = models.CharField(max_length=255, primary_key=True)
    id = models.CharField(max_length=300, db_column='ID') # Field name made lowercase.
    name = models.CharField(max_length=150)
    cms_name = models.CharField(max_length=135, db_column='CMS_Name', blank=True) # Field name made lowercase.
    class Meta:
        db_table = u'court'

    def __unicode__(self):
        return self.name

class Organization(models.Model):
    uuid = models.CharField(max_length=255, primary_key=True)
    name = models.CharField(max_length=300)
    class Meta:
        db_table = u'organizations'

    def __unicode__(self):
	if self.uuid.startswith("initiatingPartyOrganizationUuid"):
#            return self.name + "(" + self.uuid[len("initiatingPartyOrganizationUuid"):] + ")"
	     return self.uuid[len("initiatingPartyOrganizationUuid"):] + " County" 

        return self.name

class Person(models.Model):
    uuid = models.CharField(max_length=255, primary_key=True)
    userid = models.CharField(max_length=90, db_column='userID', blank=True) # Field name made lowercase.
    password = models.CharField(max_length=90, blank=True)
    firstname = models.CharField(max_length=90, db_column='firstName') # Field name made lowercase.
    middlename = models.CharField(max_length=90, db_column='middleName', blank=True) # Field name made lowercase.
    lastname = models.CharField(max_length=90, db_column='lastName') # Field name made lowercase.
    suffixname = models.CharField(max_length=90, db_column='suffixName', blank=True) # Field name made lowercase.
    changepwdflag = models.IntegerField(db_column='changePwdFlag') # Field name made lowercase.
    deleteuserflag = models.IntegerField(db_column='deleteUserFlag') # Field name made lowercase.
    dob = models.DateTimeField(null=True, blank=True)
    courtscomment = models.CharField(max_length=1500, db_column='courtsComment') # Field name made lowercase.
    setcivilactionnumber = models.IntegerField(db_column='setCivilActionNumber') # Field name made lowercase.
    setcourtdate = models.IntegerField(db_column='setCourtDate') # Field name made lowercase.
    organizations = models.ManyToManyField(Organization, verbose_name="list of sites",through="Personorganizations")

    class Meta:
        db_table = u'person'

    def __unicode__(self):
        return self.firstname + " " + self.lastname

class Personorganizations(models.Model):
    uuid = models.CharField(max_length=255, primary_key=True)
    personuuid = models.ForeignKey(Person, db_column='personUuid') # Field name made lowercase.
    organizationuuid = models.ForeignKey(Organization, db_column='organizationUuid') # Field name made lowercase.
    courtuuid = models.CharField(max_length=300, db_column='courtUuid') # Field name made lowercase.
    class Meta:
        db_table = u'personOrganizations'


class Attorney(models.Model):
    uuid = models.CharField(max_length=255, primary_key=True)
    barid = models.CharField(max_length=90, db_column='barID') # Field name made lowercase.
    person = models.ForeignKey(Person, db_column='personUuid') # Field name made lowercase.
    organizationuuid = models.ForeignKey(Organization, null=True, db_column='organizationUuid', blank=True) # Field name made lowercase.
    class Meta:
        db_table = u'attorney'

    def __unicode__(self):
        return "Attorney: " + str(self.person) + " " + self.barid

class GajeWsAuthVendorCourtAccess(models.Model):
    id = models.IntegerField(primary_key=True)
    username = models.CharField(max_length=150)
    court_uuid = models.ForeignKey(Court, db_column='court_uuid')
    class Meta:
        db_table = u'gaje_ws_auth_vendor_court_access'

class Judge(models.Model):
    uuid = models.CharField(max_length=255, primary_key=True)
    judgeid = models.CharField(max_length=90, db_column='judgeID', blank=True) # Field name made lowercase.
    personuuid = models.CharField(max_length=300, db_column='personUuid') # Field name made lowercase.
    organizationuuid = models.CharField(max_length=300, db_column='organizationUuid', blank=True) # Field name made lowercase.
    class Meta:
        db_table = u'judge'

class Ldapdirectoryorganization(models.Model):
    uuid = models.CharField(max_length=255, primary_key=True)
    courtuuid = models.ForeignKey(Court, db_column='courtUuid') # Field name made lowercase.
    cname = models.CharField(max_length=300)
    uid = models.CharField(max_length=180, blank=True)
    certpassword = models.CharField(max_length=300, db_column='certPassword', blank=True) # Field name made lowercase.
    class Meta:
        db_table = u'ldapDirectoryOrganization'

class Ldapdirectoryperson(models.Model):
    uuid = models.CharField(max_length=255, primary_key=True)
    personuuid = models.ForeignKey(Person, db_column='personUuid') # Field name made lowercase.
    cname = models.CharField(max_length=300)
    uid = models.CharField(max_length=180, blank=True)
    certpassword = models.CharField(max_length=300, db_column='certPassword', blank=True) # Field name made lowercase.
    class Meta:
        db_table = u'ldapDirectoryPerson'

class Legalpreparer(models.Model):
   uuid = models.CharField(max_length=255,primary_key=True)
   personuuid = models.ForeignKey(Person, db_column='personUuid') # Field name made lowercase.
   orguuid = models.ForeignKey(Organization, db_column='orgUuid') # Field name made lowercase.
   attorneyuuid = models.ForeignKey(Attorney, db_column='attorneyUuid') # Field name made lowercase.
   class Meta:
       db_table = u'legalPreparer'

   def __unicode__(self):
       return str(self.personuuid) + "->" + str(self.attorneyuuid)


class Role(models.Model):
    uuid = models.CharField(max_length=255, primary_key=True)
    role = models.CharField(max_length=300)
    class Meta:
        db_table = u'roles'

    def __unicode__(self):
        return self.role

class Personroles(models.Model):
    roleuuid = models.ForeignKey(Role, db_column='roleUuid') # Field name made lowercase.
    personuuid = models.ForeignKey(Person, db_column='personUuid') # Field name made lowercase.
    class Meta:
        db_table = u'personRoles'


class Visualmarkcategory(models.Model):
    code = models.IntegerField(primary_key=True)
    visualcategory = models.CharField(max_length=300, db_column='visualCategory') # Field name made lowercase.
    class Meta:
        db_table = u'visualMarkCategory'

class Visualmark(models.Model):
    uuid = models.CharField(max_length=255, primary_key=True)
    visualtarget = models.CharField(max_length=300, db_column='visualTarget') # Field name made lowercase.
    defaultflag = models.IntegerField(db_column='defaultFlag') # Field name made lowercase.
    visualcategorycode = models.ForeignKey(Visualmarkcategory, db_column='visualCategoryCode') # Field name made lowercase.
    personuuid = models.ForeignKey(Person, db_column='personUuid') # Field name made lowercase.
    class Meta:
        db_table = u'visualMark'



