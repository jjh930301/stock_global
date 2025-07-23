FROM nginx:alpine3.18-slim

RUN rm /etc/nginx/conf.d/default.conf
RUN rm /etc/nginx/nginx.conf

COPY ./default.conf /etc/nginx/conf.d/
COPY ./nginx.conf /etc/nginx/

# gocron
EXPOSE 8000 
# api 
EXPOSE 8090
# ws 
EXPOSE 8100

# default
EXPOSE 80
EXPOSE 443