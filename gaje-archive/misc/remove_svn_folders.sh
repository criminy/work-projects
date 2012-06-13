#!/bin/bash

if [ ! $1 ]; then	
	echo "usage: remove_svn <folder-name>"
fi


find $1 -iname \*\.svn | xargs rm -rfv
