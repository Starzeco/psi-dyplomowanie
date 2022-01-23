### STAGE 1: Build ###
FROM node:16.13.2 AS build

WORKDIR /usr/src/app
COPY . .
RUN npm install & npm install -g @angular/cli & npm uninstall @angular-devkit/build-angular & npm install --save-dev @angular-devkit/build-angular
RUN ng build --configuration production

### STAGE 2: Run ###
FROM nginx:1.17.1-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /usr/src/app/dist/frontend /usr/share/nginx/html
