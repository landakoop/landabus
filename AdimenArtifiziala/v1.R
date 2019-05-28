library(GA)
library(rjson)
library(RCurl)

f<-function(ruta, gelt){
  from <- 2
  to <- length(ruta)
  aurrekoa<-gelt[ruta[[1]]]
  dist<-0
  for(parada in from:to){
    par<-gelt[ruta[[parada]]]
    par<-par[[1]]
    aurrekoa<-aurrekoa[[1]]
    dist<-dist+ dis[[aurrekoa]][[par]]
    aurrekoa<-gelt[ruta[[parada]]]
  }
  for(i in 1:nrow(cluster)){

    irt<-match(cluster[i,]$irteera,geltokiak)

    irt<-match(irt,ruta)
    hel<-match(cluster[i,]$helmuga,geltokiak)
   
    hel<-match(hel,ruta)
    if(irt>hel) dist<-dist+10
  }
  
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


result <- ga(type="permutation",  fitness=f, gelt=geltokiak, lower=1, upper=length(geltokiak), popSize = 50, maxiter = 500,
             run = 50, pmutation = 0.2)

datos <- fromJSON(getURL("http://localhost:8080/api/eskaera/list"))
dis <- fromJSON(getURL("http://localhost:8080/api/geltokia/distantziak"))
dataset<-data.frame(do.call("rbind",datos))

num<-2
indice<-c(5,6)
datasetHorarios<-dataset[indice]
kmeans.fit<-kmeans(datasetHorarios,num)
list<-kmeans.fit$cluster
dataset<-cbind(dataset,list)
geltokiak<-NULL
for(i in 1:num){
  cluster<-dataset[dataset$list==i,]
  for(i in 1:nrow(cluster)){
    geltokiak<-c(geltokiak, cluster$irteera[i])
    geltokiak<-c(geltokiak, cluster$helmuga[i])
  }
  geltokiak<-unique(c(cluster$irteera,cluster$helmuga))
  geltokiak<-unique(geltokiak)
  result <- ga(type="permutation",  fitness=f, gelt=geltokiak, lower=1, upper=length(geltokiak), popSize = 50, maxiter = 500,
               run = 50, pmutation = 0.2)
  summary(result)
}
