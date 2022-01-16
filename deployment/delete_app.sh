kubectl delete -f psi_config_map.yml
kubectl delete -f psi_secrets.yml
kubectl delete -f web_db_service.yml
kubectl delete -f front_web_service.yml
kubectl delete -f front_expose_service.yml
kubectl delete -f psi_db.yml 
kubectl delete -f psi_web.yml
kubectl delete -f psi_front.yml
