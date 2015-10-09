#!/bin/bash
#Data 2015.8.20
#Author Likang
COL1='\E[1;31m'
COL2='\E[1;32m'
COL3='\E[1;33m'
COL4='\E[1;34m'
COL5='\E[1;35m'
COL6='\E[1;36m'
COL7='\E[1;37m'
COL8='\E[1;38m'
COL9='\E[1;39m'
COL10='\E[1;40m'
COL11='\E[1;41m'
COL12='\E[1;42m'
RES='\E[0m'
[ $UID -eq 0 ]||
   {
      echo "+++Use root,Please try!+++"
      exit
   }
cat << END
---------------------------------------
*              JAVA环境部署             *
*                Enjoy!               *
---------------------------------------
END
sleep 1
cd
while true
do
echo -e "请输入java相关gz包"${COL5}"全路径"${RES}":"
echo '例如:/home/Kang/Desktop/jdk-7u71-linux-x64.gz'
read JAVA_PATH
[[ -f $JAVA_PATH && -s $JAVA_PATH ]]&&break||{
   echo "输入相关gz文件不正确，请重新输入!!"
   continue
}
done
cd
tar -xzvf $JAVA_PATH
[ $? -ne 0 ]&&{
   echo "解压java的包时出现问题,程序结束!"
   exit
}
echo -e ""${COL1}"-------------------------------------------------------------------------"${RES}""
ls
echo -e ""${COL1}"-------------------------------------------------------------------------"${RES}""
echo "以上那个是您解压出来的jdk，请输入文件夹名"
echo '例如:jdk1.7.0_71'
read JDK_DIR
echo "移动安装文件路径..."
mkdir /usr/java
[ $? -eq 0 ]||{
   echo "java文件夹已经存在!!!"
   rm -rf /usr/java/
   echo "文件夹已经删除－。－"
   mkdir /usr/java
   echo "创建文件夹成功"
}
mv $JDK_DIR /usr/java
cd /usr/java/$JDK_DIR
JAVA_HOME=`pwd`
sleep 1
echo "修改java环境变量..."
cat >>/etc/profile <<- EOF
#Java
export JAVA_HOME=$JAVA_HOME
export PATH=\$JAVA_HOME/bin:\$PATH
EOF
echo "环境变量配置成功!"
source /etc/profile
i=3
while [ $i -gt 0 ]
do
echo "$i秒后即将重启..."
((i=i-1))
sleep 1
done
reboot