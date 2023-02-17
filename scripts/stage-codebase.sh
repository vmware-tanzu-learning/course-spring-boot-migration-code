#!/bin/bash

# This script stages the codebase for deployment.
# It takes a single argument, the name of the directory to stage the
# codebase into.
#
# NOTE: the target directory will be deleted if it already exists!
TARGET_DIR=$1

pushd $(pwd) > /dev/null

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$SCRIPT_DIR"/..

if [ -z "$TARGET_DIR" ]; then
    echo "Usage: $0 <target_dir>"
    exit 1
fi

if [ -d "$TARGET_DIR" ]; then
    rm -rf "$TARGET_DIR"
fi

cd ./"$PROJECT_DIR"/..
cp -r $(basename $PROJECT_DIR) $TARGET_DIR

popd > /dev/null