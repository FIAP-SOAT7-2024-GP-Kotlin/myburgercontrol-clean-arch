#!/bin/bash

kubectl apply -f k8s/db/postgres-pv-do.yaml
kubectl apply -f k8s/db/postgres-pvc-do.yaml

kubectl apply -f k8s/db/postgres-configmap.yaml
kubectl apply -f k8s/db/postgres-deployment.yaml
kubectl apply -f k8s/db/postgres-svc.yaml

# Initialize database
# Create Role and Database
cat script/db/my_burger_db_creation.sql |  kubectl exec deployments/postgres -it -- psql -U postgres -W -h localhost

