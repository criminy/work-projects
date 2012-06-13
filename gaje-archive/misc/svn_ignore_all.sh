mvn eclipse:clean clean

LINE="$(cat svnignores.txt)"

for x in `find -name pom.xml`; do
	svn propset svn:ignore "$LINE" $(dirname $x)
done
