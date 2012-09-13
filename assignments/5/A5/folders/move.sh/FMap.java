#!/usr/bin/bash

for file in *
do
  mkdir folders/$file
  cp $file folders/$file
done
