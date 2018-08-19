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
        QList<QHostAddress> list = QNetworkInterface::allAddresses();
        qDebug()<<"My Ip "<<list[2].toString();
        QStringList tempList = list[2].toString().split(".");
        QString hostIp = tempList[0]+"."+tempList[1]+"."+tempList[2]+"."+"1";
        qDebug()<<"hostIp "<<hostIp;
        qDebug()<<"Going to connect to Server";
        QString hostName   = hostIp;
        QString portString = "40001";
        quint16 port = portString.toUInt();
        sock->connectToHost(hostName ,port);
    }

    void Actions::sendCommand(QTcpSocket *sock, QString command)
    {
        qDebug()<<"Going to send command "<< command;
        qDebug()<<command.toLocal8Bit();
        sock->write(command.toLocal8Bit());
        //sock->flush();
        sock->waitForBytesWritten();

    }

    QString Actions::getDeviceName(QString itemName){

        if (itemName == "light1Button")
            return "1";
        else if (itemName == "light2Button")
            return "2";
        else if (itemName ==  "fan1Button")
            return "3";
        else if (itemName ==  "fan2Button")
            return "4";
    }

    QString Actions::getDevStatusAfterOperation(QTcpSocket* sock){
        QString retString = sock->readAll();
        QString statusString = retString.split(",")[3];
        //qDebug()<<statusString[2];
        return statusString;
    }

    void Actions::changeDeviceStatus(QObject* presentItem, QString status){
        QString presentItemName = presentItem->property("objectName").toString();
        QString presentItemStatusString = presentItemName.mid(0,presentItemName.indexOf("Button")) + "Status";
        qDebug()<<presentItemName.indexOf("Button");
        qDebug()<<presentItemStatusString;
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
        qDebug()<<operation<<devName;
        QString command = commandMaker(operation,devName);
        //qDebug() << command;
        QTcpSocket sock;
        connectToServer(&sock);
        if(sock.waitForConnected(5000)){
            qDebug()<<"Connected to server";
            sendCommand(&sock,command);
            if(!sock.waitForReadyRead(3000)){
                qDebug()<<"Error while reading Tcp";
            }
            qDebug()<<"wait Over";
            QString opStatus = getDevStatusAfterOperation(&sock);
            qDebug()<<opStatus;
            changeDeviceStatus(presentItem,opStatus);
            sock.close();
            qDebug()<<"lol";
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


