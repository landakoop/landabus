
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
  library(aws.s3)
  bucket<-get_bucket(bucket = 'modeloa',key = 'AKIA2EYPQL5TCO3XPWHS',secret = '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')
  izena<-paste(geltokia,'_',ekintza,".rds",sep ="")
  modeloa<-s3readRDS(izena, bucket)
  datuak<-data.frame(x1,x2,x3,x4,x5,x6,x7,as.numeric(ordua),eguna,as.numeric(hilabetea),eguraldia)
  names(datuak)<-colnames(modeloa$trainingData[,2:12])
  list(predict(modeloa,datuak))
}


