#!/bin/bash
echo "输入从master移动过来的hadoop文件夹名字"
read HADOOP_DIR
cd ~/BinSoft/$HADOOP_DIR
HADOOP_HOME=`pwd`
sleep 1
echo "修改hadoop环境变量..."
cat >>~/.bash_profile <<- EOF
#Hadoop
export HADOOP_HOME="${HADOOP_HOME}"/
export PATH=\$HADOOP_HOME/bin:\$HADOOP_HOME/sbin:\$PATH
EOF
echo "环境变量配置成功!"
source ~/.bash_profile