#!/bin/bash
echo "输入剥离的文件夹根目录:"
read MYPATH
cd $MYPATH
for dir in `ls`
do
cd $dir/
for file in `ls`
do
[ -f $file ]&&{
mv $file /Users/Kang/R4Code/Python/video/demo/
}
done
cd ..
done
echo "sucessful"
