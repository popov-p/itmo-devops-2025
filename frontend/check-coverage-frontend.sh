#!/bin/bash

COVERAGE=$(npm test -- --run --coverage | awk -F'|' '/All files/ {gsub(/ /, "", $5); print $5; exit}')

echo "Общее покрытие строк: $COVERAGE%"


if (( $(echo "$COVERAGE < 80" | bc -l) )); then
  echo "Покрытие ниже 80%. CI провален!"
  exit 1
else
  echo "Покрытие выше 80%. Все ок!"
fi
