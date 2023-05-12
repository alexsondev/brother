#!/usr/bin/env bash

CURRENT_BRANCH=$(git symbolic-ref HEAD)

if [ "$CURRENT_BRANCH" == "refs/heads/master" ]; then
    __print_fail "Commits na master não são permitidos. Abra uma hotfix nesse caso."
    return 1
fi
if [ "$CURRENT_BRANCH" == "refs/heads/develop" ]; then
    __print_fail "Commits na develop não são permitidos. Abra uma bugfix para ajustes pontuais ou uma feature para novos desenolvimentos"
    return 1
fi

return 0