library(GA)
library(rjson)
library(RCurl)

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
  for(irteeraOrdua in seq(0,1440,30)){
    kont<-0
    onartuLista<-NULL
    for(i in 1:nrow(eskaerak)){
      helmugaPos<-match(eskaerak[i,]$helmuga,ruta)
      irteeraPasatzeOrdua<-pasatzeOrduErlatiboak[i]+irteeraOrdua
      helmugaPasatzeOrdua<-pasatzeOrduErlatiboak[helmugaPos]+ irteeraOrdua
      #print(ruta)
      #print(pasatzeOrduErlatiboak)
      if((irteeraPasatzeOrdua>eskaerak[i,]$irteeraOrdua)&&(helmugaPasatzeOrdua<eskaerak[i,]$helmugaOrdua)&&i<helmugaPos){
        kont<-kont+1
        onartuLista<-c(onartuLista,eskaerak[i,])
      }
    }
    if(kont>kontHoberena){
      hoberena<-irteeraOrdua
      kontHoberena<-kont
      onartuListaHoberena<-onartuLista
    }
  }
  return(c(hoberena,kontHoberena,onartuListaHoberena))
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


eskaerak <- fromJSON(getURL("http://localhost:8080/api/eskaera/list"))
distantziak <- fromJSON(getURL("http://localhost:8080/api/geltokia/distantziak"))
dataset<-data.frame(do.call("rbind",eskaerak))

num<-2
indice<-c(5,6)
datasetHorarios<-dataset[indice]
kmeans.fit<-kmeans(datasetHorarios,num)
list<-kmeans.fit$cluster
dataset<-cbind(dataset,list)
for(i in 1:num){
  geltokiak<-NULL
  cluster<-dataset[dataset$list==i,]
  for(i in 1:nrow(cluster)){
    geltokiak<-c(geltokiak, cluster$irteera[i])
    geltokiak<-c(geltokiak, cluster$helmuga[i])
  }
  geltokiak<-unique(c(cluster$irteera,cluster$helmuga))
  geltokiak<-unique(geltokiak)
  result <- ga(type="permutation",  fitness=fitnessRuta, gelt=geltokiak, eskaerak=cluster, lower=1, upper=length(geltokiak), popSize = 50, maxiter = 500,
               run = 50, pmutation = 0.2)
  resultDef<-geltokiak[order(summary(result)$solution)]
  print(resultDef)
  print(ordutegiOnenaKalkulatu(cluster,pasatzeOrduakKalkulatu(resultDef,distantziak),resultDef))
}


library(mlbench)
library(caret)
# load the data
data(PimaIndiansDiabetes)
# define the control using a random forest selection function
control <- rfeControl(functions=rfFuncs, method="cv", number=10)
# run the RFE algorithm Recursive Feature Elimination 
results <- rfe(PimaIndiansDiabetes[,1:8], PimaIndiansDiabetes[,9], sizes=c(1:8), rfeControl=control)
trainControl <- trainControl(method="repeatedcv", number=10, repeats=3)
fit.rf <- train(diabetes~., data=PimaIndiansDiabetes, method="rf", trControl=trainControl)

aldaketa(summary(result)$solution, geltokiak)

print(summary(result)$solution)
print(geltokiak)
