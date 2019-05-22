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
  return(dist)
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

result <- ga(type="permutation", fitness=f, lower=c(1,1,1), upper=c(3,3,3))
