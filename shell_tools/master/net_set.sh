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
*              网络环境设置             *
*                Enjoy!               *
---------------------------------------
END
sleep 1
########################设置主机名###############################################
echo "请输入您要设置的主机名"
read MYHOSTNAME
echo "清空/etc/sysconfig/network文件"
cat /dev/null >/etc/sysconfig/network
echo "设置network文件中..."
cat >/etc/sysconfig/network <<- EOF
#启动网络
NETWORKING=yes
#主机名
HOSTNAME=$MYHOSTNAME
EOF
hostname $MYHOSTNAME
echo "您的主机名为:"
hostname
sleep 1
########################设置ip地址###############################################
function staticIp(){
   MAC=`head -2 /etc/sysconfig/network-scripts/ifcfg-eth0|tail -1`
   sleep 1
   echo "输入您想设置的静态ip地址"
   read MYSTATICIP
   cat >/etc/sysconfig/network-scripts/ifcfg-eth0 <<- EOF
   DEVICE="eth0"
   $MAC
   NM_CONTROLLED="yes"
   ONBOOT="yes"
   BOOTPROTO=static
   IPADDR=$MYSTATICIP
   #BROADCAST=""
   GATEWAY=192.168.2.1
   NETMASK=255.255.255.0
   DNS1=192.168.2.225
EOF
   echo "静态ip设置完成"
   MYIP=`ifconfig|head -2|cut -d : -f 2|tail -1|cut -d " " -f 1`
}

echo "检测您的ip情况..."
MYIP=`ifconfig|head -2|cut -d : -f 2|tail -1|cut -d " " -f 1`
echo "---------------------------------------------------------------------"
echo -e ""${COL6}""${MYIP}""${RES}""
echo "---------------------------------------------------------------------"
[ ${#MYIP} -gt 10 ]&&{
echo '需要更换静态ip地址?[yes/no]'
read Choose
if [ "${Choose}" == "yes" ]
   then
   staticIp
fi
}||{
echo "您的ip有问题,请静态设置..."
staticIp
}
sleep 1
#########################设置hosts文件###########################################
count=0
echo '++++设置hosts文件!!!++++'
echo '分别输入其他节点[ip地址]和[主机名]'
echo "格式为:$MYIP"
echo "格式为:$MYHOSTNAME"
while true
do
echo '[ip地址]:'
read WORD 
echo '[主机名]:'
read WNAME
TWORD=`echo -e "${WORD}"`
WORDS[$count]=""$TWORD" "$WNAME""
((count=count+1))
echo '是否继续录入[y/n]'
read cc
[ cc == "y" ]&&continue||{
echo "成功录入$WNAME信息,total:$count+1"
break
}
done
cat >/etc/hosts <<- EOF
$MYIP $MYHOSTNAME
${WORDS[*]}
EOF
echo "所有网络相关配置完成!!!"
i=3
while [ $i -gt 0 ]
do
echo "$((i*2))秒后即将重启..."
((i=i-1))
sleep 2
done
reboot
