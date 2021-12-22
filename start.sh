cd `dirname $0`                 #进入根目录

proj_home=$PWD                  #工程根目录
img_output=${image}          #生成镜像标签
appname=${name}                   #容器名称
port=80                         #docker暴露端口

#  mvn打包镜像
docker run  --rm doc--name my-maven-project -v /root/.m2:/root/.m2 -v "$PWD":/usr/local/maven -w /usr/local/maven maven:3.1.1-jdk-8-alpine mvn clean install
# 获得docker容器 id和镜像 id
r_c=`docker ps -a | grep "$appname" | awk '{print $1 }'`
c=`docker ps -a | grep "$appname" | awk '{print $1 }'`
r_img=`docker images | grep "$appname" | awk '{print $3 }'`
# 如果容器正在运行，停止它
if [ "$r_c"x != ""x ]; then
    docker stop "$r_c"
fi
# 删除容器
if [ "$c"x != ""x ]; then
    docker rm "$c"
fi
# 删除镜像
if [ "$r_img"x != ""x ]; then
    docker rmi "$r_img"
fi

# 生成镜像
docker build -t $img_output .
# 日志目录
mkdir -p $PWD/logs
chmod 777 $PWD/logs

# 启动镜像  8086为工程的端口
docker run -d --name $appname -p $port:8086 $img_output