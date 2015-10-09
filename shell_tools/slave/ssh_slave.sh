#!/bin/bash
#Data 2015.8.20
#Author Likang
[ $UID -eq 0 ]&&
   {
      echo "+++Do not use root,Please try!+++"
      exit
   }
cat << END
---------------------------------------
*              SSH免密钥设置            *
*             Slave  Enjoy!           *
---------------------------------------
ps:此处用于主节点机器
END
cd
sleep 1
echo "密钥生成..."
ssh-keygen -t rsa
while true
do
[ -e authorized_keys ]&&break||{
   echo "+++++等待master发送authorized_keys文件..."
   sleep 1.5
}
done
echo "公钥移动中..."
mv authorized_keys ~/.ssh/
cd ~/.ssh
chmod 600 authorized_keys
echo "设置完成!可以测试ssh"
