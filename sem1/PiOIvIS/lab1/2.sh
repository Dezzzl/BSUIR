#!/bin/bash
dir=$1
if ! [ -d "$dir" ]; 
then
mkdir $1
cd $1
else
cd $1
fi
if ! [ -d tetr ]; 
then
mkdir tetr
else
rm -rf tetr 
mkdir tetr
fi
echo "Состояние сети $(netstat)">dev.txt
cp dev.txt tetr/dev1.txt
cd tetr
echo "Count of lines=$(wc -l dev1.txt)"


