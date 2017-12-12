#! /bin/bash
echo "Now starting to generate dependencies for spark project..."
BUILD_HOME=$(cd "$(dirname "$0")"; pwd)
mavenDIr=$BUILD_HOME/maven
cd $BUILD_HOME
for i in $(ls *.jar);do
  pomName=$(echo $i | sed 's/.jar/.pom/g')
  pomPath=`find ${mavenDIr} -name $pomName`
  if [ "x$pomPath"  != "x" ];then
    pomDir=`dirname $pomPath`
    cp $i $pomDir
  fi
done
echo "The dependencies can be get in $BUILD_HOME/maven."

