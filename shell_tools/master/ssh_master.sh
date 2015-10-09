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
[ $UID -eq 0 ]&&
   {
      echo "+++Do not use root,Please try!+++"
      exit
   }
cat << END
---------------------------------------
*              SSH免密钥设置            *
*             Master  Enjoy!           *
---------------------------------------
ps:此处用于主节点机器
END
cd
sleep 1
echo "密钥生成..."
ssh-keygen -t rsa
[ -e ~/.ssh/id_rsa.pub ]||{
   echo "请手动清空.ssh文件夹,程序结束"
   exit
}
echo "公钥复制中..."
cat ~/.ssh/id_rsa.pub >>~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys
echo "公钥完成!!!"
echo -e "输入连接的"${COL1}"主机名:"${RES}"[可以一次输入多个]"
echo "格式:www baike slave1 slave2"
read MYHOST1 MYHOST2 MYHOST3 MYHOST4
echo -e "输入连接的主机名对应的"${COL1}"用户:"${RES}""
echo "例如:Kang"
read MYUSER
echo "远程发送公钥中..."
for MYHOST in $MYHOST1 $MYHOST2 $MYHOST3 $MYHOST4
do
[ -n $MYHOST ]&&{
   echo "正在连接主机${MYHOST}...."
   scp ~/.ssh/authorized_keys ${MYUSER}@${MYHOST}:~/
}
[ $? -eq 0 ]&&{
	echo "制作完成！"
}||{
   echo "主机${MYHOST}的ssh链接失败，请尝试手动执行"
}
done
sleep 1
