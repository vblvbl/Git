#!/bin/bashcd ~/Desktop/
cat china-province.txt |sed 's/，/\r\n/g' > tmp
cat tmp > china-province.txt
rm -rf tmp
echo "over"
