FROM r-base:latest

RUN apt update
RUN apt install -y curl
RUN Rscript -e "install.packages('GA')"
RUN Rscript -e "install.packages('rjson')"
RUN Rscript -e "install.packages('httr')"
RUN Rscript -e "install.packages('parallel')"
RUN Rscript -e "install.packages('doParallel')"
RUN Rscript -e "install.packages('rngtools')"
RUN Rscript -e "install.packages('doRNG')"
RUN Rscript -e "install.packages('knitr')"
RUN apt install -y libcurl4-gnutls-dev
RUN Rscript -e "install.packages('RCurl')"
RUN Rscript -e "install.packages('profvis')"
RUN apt install -y libssl-dev
RUN Rscript -e "install.packages('openssl')"
RUN Rscript -e "install.packages('httr')"

COPY v1.R /home

WORKDIR /home

CMD ["r v1.R"]
