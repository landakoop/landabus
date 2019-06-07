library(caret)

for(i in 1:8){
  izena<-paste(i,'_jaitsi','.csv',sep ="")
  dataset<- read.csv(izena, header=TRUE)
  modeloa<-train(kopurua~., data=dataset, method="rf")
  izenaGorde<-paste(i,'_jaitsi',".RDS",sep="")
  save(modeloa,file=izenaGorde)
  predictions<-predict(modeloa,dataset[,1:11])
  print(summary(predictions))
  
  izena<-paste(i,'_igo','.csv',sep ="")
  dataset<- read.csv(izena, header=TRUE)
  modeloa<-train(kopurua~., data=dataset, method="rf")
  izenaGorde<-paste(i,'_igo',".RDS",sep="")
  saveRDS(modeloa,file=izenaGorde)
  predictions<-predict(modeloa,dataset[,1:11])
  print(summary(predictions))
}

