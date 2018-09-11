#include "actions.h"
#include <QQuickItem>
#include <QQmlApplicationEngine>
#include <QQuickView>
#include <QHostInfo>
#include <QNetworkInterface>
Actions::Actions()
{

}
   QString Actions::commandMaker(QString operation,QString devName){
        QString command;
        command = "DO,"+devName+","+operation;
        return command;
    }

    void Actions::connectToServer(QTcpSocket* sock)
    {
        QStringList tempListBack,tempList ;
        QList<QHostAddress> list = QNetworkInterface::allAddresses();


        qDebug()<<"Ip list "<< list;
        foreach (const  QHostAddress &address, list) {
            if(address.protocol() == QAbstractSocket::IPv4Protocol  && address != QHostAddress(QHostAddress::LocalHost) ){
                qDebug()<<"Device ipv4 address is "<<address;
                tempListBack = address.toString().split(".");
                if(tempListBack[0] == "192"){
                    tempList = tempListBack;
                    break;
                }
            }
        }
        qDebug()<<"hostIp "<<tempList;
        QString hostIp = tempList[0]+"."+tempList[1]+"."+tempList[2]+"."+"1";
        qDebug()<<"Going to connect to Server";
        QString hostName   = hostIp;
        QString portString = "40000";
        quint16 port = portString.toUInt();
        sock->connectToHost(hostName ,port);
    }

    void Actions::sendCommand(QTcpSocket *sock, QString command)
    {
        //qDebug()<<"Going to send command "<< command;
        //qDebug()<<command.toLocal8Bit();
        sock->write(command.toLocal8Bit());
        //sock->flush();
        sock->waitForBytesWritten();

    }

    QString Actions::getDeviceName(QString itemName){

        if (itemName == "light1Button")
            return "hall_light";
        else if (itemName == "light2Button")
            return "portico_light";
        else if (itemName ==  "fan1Button")
            return "hall_fan";
        else if (itemName ==  "fan2Button")
            return "portico_fan";
            
    }

    QString Actions::getDevStatusAfterOperation(QTcpSocket* sock){
        QString retString = sock->readAll();
        //qDebug()<< retString.split(",");
        QString statusString = retString.split(",")[3];
        //qDebug()<<statusString[2];
        return statusString;
    }

    void Actions::changeDeviceStatus(QObject* presentItem, QString status){
        QString presentItemName = presentItem->property("objectName").toString();
        QString presentItemStatusString = presentItemName.mid(0,presentItemName.indexOf("Button")) + "Status";
        //qDebug()<<presentItemName.indexOf("Button");
        //qDebug()<<presentItemStatusString;
        QObject* parentItem = presentItem->parent();
        QObject* presentItemStatus = parentItem->findChild<QObject*>(presentItemStatusString);
        if(status=="ON"){
            presentItemStatus->setProperty("text","ON");
        }
        else if(status == "OFF"){
            presentItemStatus->setProperty("text","OFF");
        }
    }

   void Actions::buttonClicked(QQuickItem* presentItem){

        QString operation = presentItem->property("text").toString();
        QString itemName  = presentItem->property("objectName").toString();
        QString devName   = getDeviceName(itemName);
        //qDebug()<<operation<<devName;
        QString command = commandMaker(operation,devName);
        //qDebug() << command;
        QTcpSocket sock;
        connectToServer(&sock);
        if(sock.waitForConnected(5000)){
            //qDebug()<<"Connected to server";
            sendCommand(&sock,command);
            if(!sock.waitForReadyRead(3000)){
                qDebug()<<"Error while reading Tcp";
            }
            //qDebug()<<"wait Over";
            QString opStatus = getDevStatusAfterOperation(&sock);
            //qDebug()<<opStatus;
            changeDeviceStatus(presentItem,opStatus);
            sock.close();
            //qDebug()<<"lol";
        }
        else{
            qDebug()<<"Error connecting to server";
        }
        qDebug()<<"Out of the connectToServer";

        if(operation == "OFF"){
            presentItem->setProperty("text","ON");
        }
        else if(operation =="ON"){
            presentItem->setProperty("text","OFF");
        }
    }
