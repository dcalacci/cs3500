#!/bin/bash

for dir in *
do
  mv ./$dir/$dir ./$dir/FMap.java
  javac ./$dir/FMap.java
done

