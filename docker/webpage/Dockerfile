FROM clojure:lein
COPY ./webpage /webpage
WORKDIR /webpage
RUN curl -sL https://deb.nodesource.com/setup_lts.x | bash -
RUN apt-get install -y nodejs
RUN npm install
CMD [ "npm","run-script","watch" ]
