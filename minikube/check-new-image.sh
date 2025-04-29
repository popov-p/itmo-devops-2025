#!/bin/bash

NAMESPACE="default"

REPOS=("pavel5926/devel-frontend" "pavel5926/devel-backend")
DEPLOYMENTS=("frontend" "backend")

for i in "${!REPOS[@]}"; do
  DOCKER_REPO="${REPOS[$i]}"
  DEPLOYMENT_NAME="${DEPLOYMENTS[$i]}"

  echo "Checking deployment: $DEPLOYMENT_NAME"

  if ! kubectl get deployment "$DEPLOYMENT_NAME" -n "$NAMESPACE" > /dev/null 2>&1; then
    echo "Error: Deployment '$DEPLOYMENT_NAME' not found in namespace '$NAMESPACE'!"
    continue
  fi

  CURRENT_TAG=$(kubectl get deployment "$DEPLOYMENT_NAME" -n "$NAMESPACE" \
    -o jsonpath='{.spec.template.spec.containers[0].image}' | cut -d':' -f2)

  LATEST_BUILD_TAG=$(curl -s "https://hub.docker.com/v2/repositories/$DOCKER_REPO/tags/?page_size=100" | \
    jq -r '.results[] | select(.name | startswith("build-")) | [.name, .last_updated] | @tsv' | \
    sort -k2 -r | head -n1 | cut -f1)

  if [ -z "$LATEST_BUILD_TAG" ]; then
    echo "Error: No build-* tags found in repository '$DOCKER_REPO'!"
    continue
  fi

  if [ "$LATEST_BUILD_TAG" != "$CURRENT_TAG" ]; then
    echo "New build tag detected for $DEPLOYMENT_NAME: $LATEST_BUILD_TAG (was $CURRENT_TAG). Updating..."
    CONTAINER_NAME=$(kubectl get deployment "$DEPLOYMENT_NAME" -n "$NAMESPACE" \
      -o jsonpath='{.spec.template.spec.containers[0].name}')

    kubectl set image deployment/"$DEPLOYMENT_NAME" \
      -n "$NAMESPACE" \
      "$CONTAINER_NAME"="$DOCKER_REPO:$LATEST_BUILD_TAG"

    echo "Update applied to $DEPLOYMENT_NAME."

  else
    echo "No new build tags for $DEPLOYMENT_NAME. Current: $CURRENT_TAG"
  fi

  echo "-------------------------------"
done
