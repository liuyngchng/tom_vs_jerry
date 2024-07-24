# How to run
（1）安装JAVA 运行时, 
Windows 下载 https://www.oracle.com/cn/java/technologies/downloads/#jdk17-windows， 选择 “x64 Compressed Archive” ;  
解压后在在某个目录, 假定为 C:\123\456 , 则打开控制台(CMD)
在控制台中执行 
```shell script
c:\123\456\jdk17.0.12\bin\java -jar tom_vs_jerry-1.0-SNAPSHOT-jar-with-dependencies.jar
```
（2）安装 maven;
  
（3）编译打包;  
（4）运行;  
（5）点击 “开始运行” 按钮， 角色1 使用 ‘asdw’移动， 角色2使用 'jkli'移动（不区分大小写）;
（6）角色相撞后，点击“重新开始”，或者按下空格键，游戏重新运行
# script
```$sh
mvn clean package
java -jar target/tom_vs_jerry-1.0-SNAPSHOT-jar-with-dependencies.jar
```

# 游戏规则
（1）在规定的时间内（15秒，玩家可自定义）， Jerry 没有被抓到，则 Jerry 加分，游戏继续；  
（2）任一时刻，Tom 抓到 Jerry, 则 Tom 加分，游戏结束， 玩家按屏幕“重新开始”（或空格键） 按键开始游戏；  