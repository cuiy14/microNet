quantreg=function(string, i){
table <- read.csv(string)
subtable <- table[c(1:2880), ]
library("SparseM")
library("quantreg")
if(i==1){
fit1 <- rq(machine45 ~ solar, tau = 0.05 , data = subtable)
fit2 <- rq(machine45 ~ solar,tau = 0.5, data = subtable)
fit3 <- rq(machine45 ~ solar,tau = 0.95 ,  data = subtable)
}
else if(i==2){
fit1 <- rq(machine53 ~ solar, tau = 0.05 , data = subtable)
fit2 <- rq(machine53 ~ solar,tau = 0.5, data = subtable)
fit3 <- rq(machine53 ~ solar,tau = 0.95 ,  data = subtable)
}
in1 <- coef(fit1)[1]  # index start from 1
co1 <- coef(fit1)[2]
in2 <- coef(fit2)[1]
co2 <- coef(fit2)[2]
in3 <- coef(fit3)[1]
co3 <- coef(fit3)[2]
coeff <- c(in1,co1,in2,co2,in3,co3)
return (coeff)
#anova(fit1,fit2,fit3)             # 不同分位点模型比较-方差分析
#anova(fit1,fit2,fit3,joint=FALSE)
#plot(subtable$machine45,pch=20, col = "#2E8B57",
#     main = "不同分位点拟合曲线的比较")
#lines(fitted(fit1),lwd=2, col = "#FF00FF")
#lines(fitted(fit2),lwd=2, col = "#EEEE00")
#legend("topleft", c("tau=.25","tau=.50","tau=.75"), lty=c(1,1),
#       col=c( "#FF00FF","#EEEE00","#EE6363"))
}