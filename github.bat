@echo off
git init
git add .
git config --global user.email "garfild63@list.ru"
git commit -m "Beta-version"
git remote add origin https://github.com/Garfild63/parser.git
git pull origin master
git push origin master
pause
