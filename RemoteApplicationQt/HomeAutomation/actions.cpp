#include "actions.h"

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
        qDebug()<<"Going to connect to Server";
        QString hostName   = "127.0.0.1";
        QString portString = "40000";
        quint16 port = portString.toUInt();
        sock->connectToHost(hostName ,port);
    }

    void Actions::sendCommand(QTcpSocket *sock, QString command)
    {
        qDebug()<<"Going to send command "<< command;
        qDebug()<<command.toLocal8Bit();
        sock->write(command.toLocal8Bit());
        sock->flush();
        sock->close();
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

   void Actions::buttonClicked(QQuickItem* presentItem){
        QString operation = presentItem->property("text").toString();
        QString itemName  = presentItem->property("objectName").toString();
        QString devName   = getDeviceName(itemName);

        presentItem->setProperty("text","ON");
        qDebug()<<operation<<devName;
        QString command = commandMaker(operation,devName);
        //qDebug() << command;
        QTcpSocket sock;
        connectToServer(&sock);
        if(sock.waitForConnected(5000)){
            qDebug()<<"Connected to server";
            sendCommand(&sock,command);
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


