PSI_WEB_IMAGE_NAME="psi-backend"
PSI_FRONT_IMAGE_NAME="psi-frontend"

for ARGUMENT in "$@"
do
    KEY=$(echo $ARGUMENT | cut -f1 -d=)

    case "$KEY" in
            rebuild)              rebuild=true ;;
            *)
    esac
done

eval $(minikube docker-env)

if [ "$(docker images | grep $PSI_WEB_IMAGE_NAME)" == "" ] || [ "$rebuild" == true ] || [ "$(docker images | grep $PSI_FRONT_IMAGE_NAME)" == "" ]; then
  if [[ "$rebuild" == true ]]; then
    echo "Deleting old $PSI_WEB_IMAGE_NAME and $PSI_FRONT_IMAGE_NAME image"
    docker rmi "$PSI_WEB_IMAGE_NAME" --force
    docker rmi "$PSI_FRONT_IMAGE_NAME" --force
    echo "Old $PSI_WEB_IMAGE_NAME and $PSI_FRONT_IMAGE_NAME image deleted"
  fi
  echo 'Building images'
  docker build -t "$PSI_WEB_IMAGE_NAME" ../backend/
  docker build -t "$PSI_FRONT_IMAGE_NAME" ../frontend/
  echo "Images built"
fi

kubectl apply -f psi_config_map.yml
kubectl apply -f psi_secrets.yml
kubectl apply -f web_db_service.yml
kubectl apply -f front_web_service.yml
kubectl apply -f front_expose_service.yml
kubectl apply -f psi_db.yml 
kubectl apply -f psi_web.yml
kubectl apply -f psi_front.yml
