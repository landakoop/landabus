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
    if(irt>hel) dist<-dist+10
  }
  print(1/dist)
  return(1/dist)
}

datos <- fromJSON(getURL("http://localhost:8080/api/eskaera/list"))
dis <- fromJSON(getURL("http://localhost:8080/api/geltokia/distantziak"))
head(datos)
lista<-NULL
for(i in datos){
  lista<-c(lista, i$irteera)
  lista<-c(lista, i$helmuga)
}
lista<-unique(lista)


result <- ga(type="permutation",  fitness=f, lower=1, upper=length(dis), popSize = 50, maxiter = 500,
             run = 500, pmutation = 0.2)

datos <- fromJSON(getURL("http://localhost:8080/api/eskaera/list"))
dis <- fromJSON(getURL("http://localhost:8080/api/geltokia/distantziak"))
dataset<-data.frame(do.call("rbind",datos))

num<-2
indice<-c(2,6)
datasetHorarios<-dataset[indice]
kmeans.fit<-kmeans(datasetHorarios,num)
list<-kmeans.fit$cluster
dataset<-cbind(dataset,list)
geltokiak<-NULL
for(i in num){
  cluster<-dataset[dataset$list==i,]
  for(i in length(cluster)){
    geltokiak<-c(geltokiak, cluster$irteera[i])
    geltokiak<-c(geltokiak, cluster$helmuga[i])
  }
  geltokiak<-unique(c(cluster$irteera,cluster$helmuga))
  geltokiak<-unique(geltokiak)
}
