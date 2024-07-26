#!/bin/bash

kubectl apply -f k8s/app/myburger-configmap.yaml
kubectl apply -f k8s/app/myburger-secret.yaml

kubectl apply -f k8s/app/myburger-deployment.yaml
kubectl apply -f k8s/app/myburger-hpa.yaml
kubectl apply -f k8s/app/myburger-svc.yaml
