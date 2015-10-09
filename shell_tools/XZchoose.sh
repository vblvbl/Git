#!/bin/bash
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
function resault(){
   echo -e "查询结果---您的星座是:"$2""$1"${RES}"
}
cat << END
---------------------------------------
*               星座查询              *
*     (分别输入月份和日期)  Enjoy!    *
---------------------------------------
END
while true
do
read -t 10 -p "请输入月份(整数):" Mon
expr $Mon + 0 >/dev/null 2>&1
[ $? -eq 0 ]&&
{
if [[ $Mon -gt 0 && $Mon -le 12 ]]
then
	read -t 10 -p "请输入日期(整数):" Day
	expr $Day + 0 >/dev/null 2>&1
	[ $? -eq 0 ]&&{
   case "$Mon" in
   2)
   if [[ $Day -gt 0 && $Day -le 29 ]]
      then
	break
      else
	echo "该月份没有这个日期"
        continue
   fi
   ;;
   4|6|9|11)
   if [[ $Day -gt 0 && $Day -le 30 ]]
   then
      break
   else
      echo "该月份没有这个日期"
      continue
   fi
   ;;
   *)
	if [[ $Day -gt 0 && $Day -le 31 ]]
      then
          break
   else
	     echo "该月份没有这个日期"
        continue
   fi
   ;;
esac
}||{
echo "错误,请输入整数日期!"
}
else
   echo "请输入有效的月份(1-12月)"
fi
}||
{
   echo "错误,请输入整数月份!"
   continue
}
done

if [ $Mon -eq 1 ];then
   if [[ $Day -gt 0 && $Day -le 19 ]]
      then
      resault "魔羯座" $COL1
   else
      resault "水瓶座" $COL2
   fi
elif [[ $Mon -eq 2 ]]; then
   if [[ $Day -gt 0 && $Day -le 18 ]]
      then
      resault "水瓶座" $COL2
   else
      resault "双鱼座" $COL3
   fi
elif [[ $Mon -eq 3 ]]; then
   if [[ $Day -gt 0 && $Day -le 20 ]]
      then
      resault "双鱼座" $COL3
   else
      resault "牡羊座" $COL4
   fi
elif [[ $Mon -eq 4 ]]; then
   if [[ $Day -gt 0 && $Day -le 20 ]]
      then
         resault "牡羊座" $COL4
   else
      resault "金牛座" $COL5
   fi
elif [[ $Mon -eq 5 ]]; then
   if [[ $Day -gt 0 && $Day -le 20 ]]
      then
      resault "金牛座" $COL5
   else
      resault "双子座" $COL6
   fi
elif [[ $Mon -eq 6 ]]; then
   if [[ $Day -gt 0 && $Day -le 21 ]]
      then
      resault "双子座" $COL6
   else
      resault "巨蟹座" $COL7
   fi
elif [[ $Mon -eq 7 ]]; then
   if [[ $Day -gt 0 && $Day -le 22 ]]
      then
         resault "巨蟹座" $COL7
   else
      resault "狮子座" $COL8
   fi
elif [[ $Mon -eq 8 ]]; then
   if [[ $Day -gt 0 && $Day -le 22 ]]
      then
         resault "狮子座" $COL8
   else
      resault "处女座" $COL9
   fi
elif [[ $Mon -eq 9 ]]; then
   if [[ $Day -gt 0 && $Day -le 22 ]]
      then
         resault "处女座" $COL9
   else
      resault "天秤座" $COL10
   fi
elif [[ $Mon -eq 10 ]]; then
   if [[ $Day -gt 0 && $Day -le 22 ]]
      then
         resault "天秤座" $COL10
   else
      resault "天蝎座" $COL11
   fi
elif [[ $Mon -eq 11 ]]; then
   if [[ $Day -gt 0 && $Day -le 21 ]]
      then
          resault "天蝎座" $COL11
   else
      resault "射手座" $COL12
   fi
else
   if [[ $Day -gt 0 && $Day -le 21 ]]
      then
         resault "射手座" $COL12
   else
      resault "魔羯座" $COL1
   fi
fi
