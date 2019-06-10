library(caret)

for(i in 1:8){
  izena<-paste('/home/inigo/landabus/',i,'_jaitsi','.csv',sep ="")
  dataset<- read.csv(izena, header=TRUE)
  modeloa<-train(kopurua~., data=dataset, method="rf")
  #izenaGorde<-paste('/home/inigo/landabus/',i,'_jaitsi',".rds",sep="")
  s3saveRDS(modeloa, object = paste0(i,"_jaitsi", ".rds"), bucket)
  #model<-s3readRDS("_jaitsi.rds", bucket)
  #saveRDS(modeloa,file=izenaGorde)
  #predictions<-predict(modeloa,dataset[1,1:11])
  print(i)
  
  izena<-paste('/home/inigo/landabus/',i,'_igo','.csv',sep ="")
  dataset<- read.csv(izena, header=TRUE)
  modeloa<-train(kopurua~., data=dataset, method="rf")
  s3saveRDS(modeloa, object = paste0(i,"_igo", ".rds"), bucket)
  #izenaGorde<-paste('/home/inigo/landabus/',i,'_igo',".rds",sep="")
  #saveRDS(modeloa,file=izenaGorde)
  #predictions<-predict(modeloa,dataset[1,1:11])
  print(i)
}
predictions<-predict(modeloa,dataset)


izena<-paste('/home/inigo/landabus/',1,'_',"igo",".RData",sep ="")
modeloa<-load(izena)
datuak<-data.frame(x1,x2,x3,x4,x5,x6,x7,as.numeric(ordua),eguna,as.numeric(hilabetea),eguraldia)
names(datuak)<-colnames(modeloa$trainingData[,2:12])
list(predict(modeloa,datuak))
