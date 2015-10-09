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
JAVA_HOME_SH=${JAVA_HOME}
cat << END
---------------------------------------
*            Hadoop安装部署            *
*                Enjoy!               *
---------------------------------------
END
sleep 1
while true
do
echo -e "请将相关文件放在当前用户的"${COL6}"桌面"${RES}""
echo "请输入hadoop相关gz包全名称:"
echo '例如:hadoop-2.5.1.tar.gz'
read HADOOP_PATH
HADOOP_ABS_PATH="/home/${USER}/Desktop/"${HADOOP_PATH}""
#tar -xzvf $HADOOP_PATH
[[ -f $HADOOP_ABS_PATH && -s $HADOOP_ABS_PATH ]]&&break||{
   echo "输入相关gz文件不正确，请重新输入!!"
   continue
}
done
cd
tar -xzvf $HADOOP_ABS_PATH
[ $? -ne 0 ]&&{
   echo "解压java的包时出现问题,程序结束!"
   exit
}
echo -e ""${COL1}"-------------------------------------------------------------------------"${RES}""
ls
echo -e ""${COL1}"-------------------------------------------------------------------------"${RES}""
echo "以上那个是您解压出来的hadoop，请输入文件夹名"
echo '例如:hadoop-2.5.1'
read HADOOP_DIR
echo "创建BinSoft文件夹"
mkdir BinSoft
echo "将hadoop文件夹移动到BinSoft..."
mv $HADOOP_DIR BinSoft/
echo "移动成功!"
cd BinSoft/$HADOOP_DIR
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
cd /home/"${USER}"/BinSoft/$HADOOP_DIR/etc/hadoop/
################################################################################
echo "配置hadoop-env.sh文件中..."
cat >>hadoop-env.sh <<- EOF
export JAVA_HOME=${JAVA_HOME_SH}/
EOF
echo -e ""${COL1}"hadoop-env.sh"${RES}"文件完成!"
sleep 1
################################################################################
echo "配置yarn-env.sh文件中..."
cat >>yarn-env.sh <<- EOF
export JAVA_HOME=${JAVA_HOME_SH}/
EOF
echo -e ""${COL1}"yarn-env.sh"${RES}"文件完成!"
sleep 1
################################################################################
echo "清空core-site.xml文件中..."
cat /dev/null >core-site.xml
echo "装入core-site.xml文件中..."
cat >>core-site.xml <<- EOF
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->

<!-- Put site-specific property overrides in this file. -->

<configuration>
	<property>
		<name>fs.defaultFS</name>
		<value>hdfs://master:9000</value>
	</property>
	<property>
		<name>hadoop.tmp.dir</name>
		<value>/home/${USER}/BinSoft/hadoopdata</value>
	</property>
</configuration>
EOF
echo -e ""${COL1}"core-site.xml"${RES}"文件完成!"
sleep 1
################################################################################
echo "清空hdfs-site.xml文件中..."
cat /dev/null >hdfs-site.xml
echo "装入hdfs-site.xml文件中..."
cat >>hdfs-site.xml <<- EOF
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->

<!-- Put site-specific property overrides in this file. -->

<configuration>
	<property>
		<name>dfs.replication</name>
		<value>1</value>
	</property>

</configuration>
EOF
echo -e ""${COL1}"hdfs-site.xml"${RES}"文件完成!"
sleep 1
################################################################################
echo "清空yarn-site.xml文件中..."
cat /dev/null >yarn-site.xml
echo "装入yarn-site.xml文件中..."
cat >>yarn-site.xml <<- EOF
<?xml version="1.0"?>
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->
<configuration>
	<property>
		<name>yarn.nodemanager.aux-services</name>
		<value>mapreduce_shuffle</value>
	</property>
	<property>
		<name>yarn.resourcemanager.address</name>
		<value>master:18040</value>
	</property>
	<property>
		<name>yarn.resourcemanager.scheduler.address</name>
		<value>master:18030</value>
	</property>
	<property>
		<name>yarn.resourcemanager.resource-tracker.address</name>
		<value>master:18025</value>
	</property>
	<property>
		<name>yarn.resourcemanager.admin.address</name>
		<value>master:18141</value>
	</property>
	<property>
		<name>yarn.resourcemanager.webapp.address</name>
		<value>master:18088</value>
	</property>
<!-- Site specific YARN configuration properties -->

</configuration>
EOF
echo -e ""${COL1}"yarn-site.xml"${RES}"文件完成!"
sleep 1
################################################################################
echo -e "转换"${COL1}"mapred-site.xml.template"${RES}"--->mapred-site.xml中..."
cp -p mapred-site.xml.template mapred-site.xml
echo "清空mapred-site.xml文件中..."
cat /dev/null >mapred-site.xml
echo "装入mapred-site.xml文件中..."
cat >>mapred-site.xml <<- EOF
<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->

<!-- Put site-specific property overrides in this file. -->

<configuration>
	<property>
		<name>mapreduce.framework.name</name>
		<value>yarn</value>
	</property>

</configuration>
EOF
echo -e ""${COL1}"mapred-site.xml"${RES}"文件完成!"
################################################################################
echo -e ""${COL4}"六大基本配置文件搞定啦-.-!!!"${RES}""
sleep 1
################################################################################
echo "清空slaves文件中..."
cat /dev/null >slaves
echo "输入您已知的节点ip地址,输入错误会影响集群的搭建!!!(最多三个)"
echo "例如:192.168.2.217 192.168.2.154 192.168.2.222"
read slave1 slave2 slave3
for slave in $slave1 $slave2 $slave3
do
[ -n $slave ]&&echo -e ""${slave}"\r" >>slaves
done
echo -e ""${COL1}"slaves"${RES}"文件完成!"
sleep 1
################################################################################
#/home/Kang/BinSoft/hadoop-2.5.1
cd /home/${USER}/BinSoft/
mkdir hadoopdata
echo "配置完成..."