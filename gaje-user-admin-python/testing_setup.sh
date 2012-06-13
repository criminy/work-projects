
rm data/*db*


./manage.py syncdb
./manage.py syncdb --database=testing
./manage.py syncdb --database=production
./manage.py loaddata --database=testing data/fixtures/initial_data.json
./manage.py loaddata --database=production data/fixtures/initial_data.json
