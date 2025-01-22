#!/bin/bash
rm -rf deploy
mkdir deploy

datasets=()
forms=()
java=()
templates=()
widget=()
layout=()
workflow=()

prepareDeploy() {
  echo $FILE
  artifact=$(echo $FILE| cut -d'/' -f 2)
  nameIndex=3

  if test "$artifact" = "wcm"; then
    artifact=$(echo $FILE| cut -d'/' -f 3)
    nameIndex=4
  fi
  if test "$artifact" = "workflow"; then
    nameIndex=4
  fi

  # echo $artifact

  name=$(echo $FILE| cut -d'/' -f $nameIndex)
  if test "$artifact" = "workflow"; then
    name=$(echo $name| cut -d'.' -f 1)
  fi
  
  arr=${!artifact}

  # length=${#artifact[@]}
  eval 'length=${#'$artifact'[@]}'

  if [[ " ${arr[*]} " != " ${name} " ]]; 
  then
    # eval 'echo '$name' já existe'
  # else  
    # echo $FILE| cut -d'/' -f 3
    # echo $artifact $name incluido
    eval $artifact'[$length]="$name"'
  fi

  # exit 0
}

buildDeploy() {
  
  for i in "${datasets[@]}"; do
    gulp datasets --dataset=${i}
  done

  for i in "${forms[@]}"; do
    gulp forms --form=${i}
  done

  for i in "${widget[@]}"; do
    gulp widget --widget=${i}
  done

  for i in "${workflow[@]}"; do
    gulp workflow --workflow=${i}
  done
    
  javaLen=${#java[@]}

  # echo $javaLen

  if (($javaLen > 0));
  then
    mvn clean install -f src/java/pom.xml
    cp src/java/brother-component-pack/target/brother-component.ear deploy
  fi

  exit 0
}