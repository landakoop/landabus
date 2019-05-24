library(GA)
library(rjson)
library(RCurl)

f<-function(ruta){
  from <- 2
  to <- length(ruta)
  aurrekoa<-ruta[[1]]
  dist<-0
  for(parada in from:to){
    dist<-dist+dis[[aurrekoa]][[ruta[[parada]]]]
    aurrekoa<-ruta[[parada]]
  }
  for(i in datos){
    irt<-match(i$irteera,ruta)
    print(irt)
    hel<-match(i$helmuga,ruta)
    print(hel)
    if(irt>hel) dist<-dist+100
  }
  print(1/dist)
  return(1/dist)
}

datos <- fromJSON(getURL("http://localhost:8080/api/eskaera/list"))
dis <- fromJSON(getURL("http://localhost:8080/api/geltokia/distantziak"))
head(datos)
datos[0].list()
lista<-NULL
for(i in datos){
  lista<-c(lista, i$irteera)
  lista<-c(lista, i$helmuga)
}
lista<-unique(lista)


result <- ga(type="permutation",  fitness=f, lower=1, upper=length(lista), popSize = 50, maxiter = 500,
             run = 500, pmutation = 0.2)
