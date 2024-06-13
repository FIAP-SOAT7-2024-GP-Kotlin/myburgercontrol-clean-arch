export BUILD_DATE_FORMAT="+%Y%m%d%H%M"
export BUILD_DATE=`date $BUILD_DATE_FORMAT`
export CI_PROJECT_NAME="$(basename $PWD)"
export CI_COMMIT_BRANCH="latest"

echo "building $CI_PROJECT_NAME nametag=$CI_COMMIT_BRANCH datetag=$BUILD_DATE"

docker buildx build . -t $CI_PROJECT_NAME:$CI_COMMIT_BRANCH -t $CI_PROJECT_NAME:$BUILD_DATE
#docker push -a $DOCKER_REGISTRY_URL/$CI_PROJECT_NAME

