#* @param geltokia
#* @param ekintza
#* @param x1
#* @param x2
#* @param x3
#* @param x4
#* @param x5
#* @param x6
#* @param x7
#* @param ordua
#* @param eguna
#* @param hilabetea
#* @param eguraldia

#* @get /predict
function(geltokia,ekintza,x1,x2,x3,x4,x5,x6,x7,ordua,eguna,hilabetea,eguraldia){
  install.packages('RandomForest');
  library(RandomForest);
  print(geltokia)
  print(ekintza)
  izena<-paste('/usr/src/landabus/',geltokia,'_',ekintza,".RDS",sep ="")
  modeloa<-readRDS(izena)
  datuak<-data.frame(x1,x2,x3,x4,x5,x6,x7,as.numeric(ordua),eguna,as.numeric(hilabetea),eguraldia)
  names(datuak)<-colnames(modeloa$trainingData[,2:12])
  list(predict(modeloa,datuak))
}


