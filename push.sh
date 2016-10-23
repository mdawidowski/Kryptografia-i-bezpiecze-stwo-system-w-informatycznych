#!/bin/bash

git status
git fetch
git add .
git config --global user.email "mdawidowski@sigma.uge.du.pl"
git commit -m "updated repository"
git status
git push
