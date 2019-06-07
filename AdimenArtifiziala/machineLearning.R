library(caret)

for(i in 1:7){
  izena<-paste('/home/inigo/landabus/',1,'_jaitsi','.csv',sep ="")
  dataset<- read.csv(izena, header=TRUE)
  modeloa<-train(kopurua~., data=dataset, method="rf")
  izenaGorde<-paste('/home/inigo/landabus/',i,'_jaitsi',".RDS",sep="")
  save(modeloa,file=izenaGorde)
  predictions<-predict(modeloa,dataset[,1:11])
  print(summary(predictions))
  
  izena<-paste('/home/inigo/landabus/',i,'_igo','.csv',sep ="")
  dataset<- read.csv(izena, header=TRUE)
  modeloa<-train(kopurua~., data=dataset, method="rf")
  izenaGorde<-paste('/home/inigo/landabus/',i,'_igo',".RDS",sep="")
  saveRDS(modeloa,file=izenaGorde)
  predictions<-predict(modeloa,dataset[,1:11])
  print(summary(predictions))
}
predictions<-predict(modeloa,dataset)


izena<-paste('/home/inigo/landabus/',1,'_',"igo",".RData",sep ="")
modeloa<-load(izena)
datuak<-data.frame(x1,x2,x3,x4,x5,x6,x7,as.numeric(ordua),eguna,as.numeric(hilabetea),eguraldia)
names(datuak)<-colnames(modeloa$trainingData[,2:12])
list(predict(modeloa,datuak))