#!/bin/bash

kubectl apply -f k8s/db/postgres-pv-do.yaml
kubectl apply -f k8s/db/postgres-pvc-do.yaml

kubectl apply -f k8s/db/postgres-secret.yaml
kubectl apply -f k8s/db/postgres-statefulset.yaml
kubectl apply -f k8s/db/postgres-svc.yaml

# wait db to up
echo "Waiting to db goes up"
sleep 20s

# Initialize database
# Create Role and Database
cat script/db/my_burger_db_creation.sql |  kubectl exec statefulsets/postgres -it -- psql -U postgres -W -h localhost

