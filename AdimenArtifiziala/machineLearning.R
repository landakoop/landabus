library(caret)

for(i in 1:7){
  izena<-paste('/home/inigo/landabus/',1,'_jaitsi','.csv',sep ="")
  dataset<- read.csv(izena, header=TRUE)
  modeloa<-train(kopurua~., data=dataset, method="rf")
  izenaGorde<-paste('/home/inigo/landabus/',i,'_jaitsi',sep="")
  save(modeloa,file=izenaGorde)
  predictions<-predict(modeloa,dataset[,1:11])
  print(summary(predictions))
  izena<-paste('/home/inigo/landabus/',i,'_igo','.csv',sep ="")
  dataset<- read.csv(izena, header=TRUE)
  modeloa<-train(kopurua~., data=dataset, method="rf")
  izenaGorde<-paste('/home/inigo/landabus/',i,'_igo',sep="")
  save(modeloa,file=izenaGorde)
  predictions<-predict(modeloa,dataset[,1:11])
  print(summary(predictions))
}
predictions<-predict(modeloa,dataset)
