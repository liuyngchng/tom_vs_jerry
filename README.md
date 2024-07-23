# How to run
（1）安装JAVA 运行时;  
（2）安装 maven;  
（3）编译打包;  
（4）运行;  
（5）点击 “开始运行” 按钮， 角色1 使用 ‘asdw’移动， 角色2使用 'jkli'移动;
（6）角色相撞后，点击“重新开始”，游戏重新运行
# script
```$sh
mvn clean package
java -jar target/tom_vs_jerry-1.0-SNAPSHOT-jar-with-dependencies.jar
```