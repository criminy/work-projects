
import sys
import os
from lxml import etree
from base64 import b64decode

folder = sys.argv[1]

env = etree.fromstring(sys.stdin.read())
for doc in env.xpath("//documents/*"):
	uuid = doc.xpath("uuid/text()")[0]
	title = doc.xpath("title/text()")[0]
	court = doc.xpath("courtID/text()")[0]
	content = b64decode(doc.xpath("content/text()")[0])
	if not os.path.exists(folder):
		os.makedirs(folder)
	f = open(folder + "/" + title + "_" + uuid + ".pdf","w")
	f.write(content)
	f.close()
	print "Saved " + folder + "/" + title + "_" + uuid + ".pdf"
