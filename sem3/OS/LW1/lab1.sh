#!/bin/bash
find "$4" -depth -type f -user $3 -size +$1c -size -$2c -printf "%h, %f, %s\n" > $5
echo "Total number of scanned files: "
find "$4" -depth -type f | wc -l
