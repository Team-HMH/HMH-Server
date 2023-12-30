#!/bin/bash
# shellcheck disable=SC2046
# shellcheck disable=SC2009
kill $(ps aux | grep java | grep -v grep | awk '{print $2}')
echo "java stopped"