#!/bin/bash

for dir in *
do
    cd dir && java ../TestFMap > Results.txt
done

