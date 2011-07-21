#!/usr/bin/python
# This script is run hourly to remove stale records from the audit table. 
# This script is run so that the audit table does not become too large.
# Note: We may have to increase the max_count size or 
#  determine another way to guard against this problem.
# Place in /srv/gaje/scripts/audit/ 
# and add it as an entry in the crontab.
import MySQLdb
conn = MySQLdb.connect (host = "db.gaje.us",
                           user = "audit_user",
                           passwd = "",
                           db = "gaje_audit_db")

cursor = conn.cursor()
cursor.execute("select count(*) from gaje_ws_audit_table");
row = cursor.fetchone()
count=int(row[0])

cursor.close()

max_count = 6000

if count > max_count:
        cursor = conn.cursor()
        cursor.execute("select id from gaje_ws_audit_table order by date asc limit " + str(count - int(max_count)))

        row = cursor.fetchone()
        while row != None:
                print row[0]
                c2 = conn.cursor()
                # delete the current
                c2.execute("delete from gaje_ws_audit_table where id = " + str(int(row[0])));
                c2.close()
                row = cursor.fetchone()
        cursor.close()

conn.commit()
conn.close()
