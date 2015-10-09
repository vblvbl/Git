#!/bin/bash
cd /Users/Kang/Temp/
for x in `ls /Users/Kang/Temp/|grep -v Icon`
do
rm -rf ${x}
done
