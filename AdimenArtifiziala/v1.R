library(GA)
library(rjson)
library(RCurl)
library(profvis)

fit<-function(ruta, gelt){
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

distantziaKalkuloa<-function(ruta,distantziak){
  from <- 2
  to <- length(ruta)
  aurrekoa<-ruta[[1]]
  distantzia<-0
  for(parada in from:to){
    distantzia<-distantzia+distantziak[[aurrekoa]][[ruta[[parada]]]]
    aurrekoa<-ruta[[parada]]
  }
  return(distantzia)
}

pasatzeOrduakKalkulatu<-function(ruta,distantziak){
 denbora<-0
 lista<-c(denbora)
  for(i in 2:length(ruta)){
    denbora<-denbora+distantziak[[i-1]][[i]]
    lista<-c(lista,denbora)
  }
 return(lista)
}

ordutegiOnenaKalkulatu<-function(eskaerak, pasatzeOrduErlatiboak,ruta){
  hoberena<-0
  kontHoberena<-0
  onartuListaHoberena<-NULL
  for(irteeraOrdua in seq(min(unlist(eskaerak$irteeraOrdua)),max(unlist(eskaerak$irteeraOrdua)),60)){
    helmugaOrdua<-irteeraOrdua+pasatzeOrduErlatiboak[length(pasatzeOrduErlatiboak)]
    url<-paste('http://localhost:8080/api/autobusa/eskuragarritasuna?irteeraOrdua=',irteeraOrdua,'&helmugaOrdua=',helmugaOrdua,sep="")
    #print(getURL(url))
    if(getURL(url)=='false') next()
    kont<-0
    onartuLista<-NULL
    for(i in 1:nrow(eskaerak)){
      hasieraPos<-match(eskaerak[i,]$irteera,ruta)
      helmugaPos<-match(eskaerak[i,]$helmuga,ruta)
      irteeraPasatzeOrdua<-pasatzeOrduErlatiboak[hasieraPos]+irteeraOrdua
      helmugaPasatzeOrdua<-pasatzeOrduErlatiboak[helmugaPos]+ irteeraOrdua
      #print(ruta)
      #print(pasatzeOrduErlatiboak)
      if((irteeraPasatzeOrdua>eskaerak[i,]$irteeraOrdua)&&(helmugaPasatzeOrdua<eskaerak[i,]$helmugaOrdua)&&hasieraPos<helmugaPos){
        kont<-kont+1
        onartuLista<-c(onartuLista,eskaerak[i,]$id)
      }
    }
    if(kont>kontHoberena){
      hoberena<-irteeraOrdua
      kontHoberena<-kont
      onartuListaHoberena<-onartuLista
    }
  }
  return(list(hoberena,kontHoberena,onartuListaHoberena))
}

fitnessRuta<-function(permutazioa, geltokiak,eskaerak){
  # Geltokien lista ordenatu jasotako permutazioaren ordena index bezala erabilita
  ruta<-geltokiak[order(permutazioa)]
  # Ruta osoa egiteko behar den denbora kalkulatu
  distantzia<-distantziaKalkuloa(ruta,distantziak)
  # Eskaerak betetzen diren begiratu eta betzen ez badira penalizaio bat gehitu
  for(i in 1:nrow(eskaerak)){
    irteera<-match(eskaerak[i,]$irteera,ruta)
    helmuga<-match(eskaerak[i,]$helmuga,ruta)
    if(irteera>helmuga) distantzia<-distantzia+10
  }
  pasatzeOrduErlatiboak<-pasatzeOrduakKalkulatu(ruta,distantziak)
  orduhoberena<-ordutegiOnenaKalkulatu(eskaerak,pasatzeOrduErlatiboak,ruta)
  distantzia<-distantzia+((nrow(eskaerak)-orduhoberena[[2]])*10)
  return(1/distantzia)
}

main<-function(){
library(GA)
library(rjson)
library(RCurl)
library(httr)
eskaerak <- fromJSON(getURL("http://localhost:8080/api/eskaera/list"))
distantziak <- fromJSON(getURL("http://localhost:8080/api/geltokia/distantziak"))
dataset<-data.frame(do.call("rbind",eskaerak))
dataset<-head(dataset,4)
while(nrow(dataset)>1){
  geltokiak<-NULL
  for(i in 1:nrow(dataset)){
    geltokiak<-c(geltokiak, dataset$irteera[i])
    geltokiak<-c(geltokiak, dataset$helmuga[i])
  }
  geltokiak<-unique(c(dataset$irteera,dataset$helmuga))
  print(length(geltokiak))
  result <- ga(type="permutation",  fitness=fitnessRuta, gelt=geltokiak, eskaerak=dataset, lower=1, upper=length(geltokiak), popSize = 100, maxiter = 500,
               run = 50, pmutation = 0.2, parallel = 4)
  resultDef<-geltokiak[order(summary(result)$solution[1,])]
  print(resultDef)
  ordutegiak<-ordutegiOnenaKalkulatu(dataset,pasatzeOrduakKalkulatu(resultDef,distantziak),resultDef)
  print(ordutegiak)
  onartuak<- ordutegiak[[3]]
  if(length(onartuak)==0) break
  dataset<-dataset[!(c(dataset$id %in% onartuak)),]
  irtOrdua<-ordutegiak[[1]]
  POST("http://localhost:8080/api/ia/bilaketaEmaitza", 
       body=list(linea=resultDef, eskaerak=onartuak,irteeraOrdua=irtOrdua),
       encode="json")
}
}

#POST("",body=dataset,encode="json")


