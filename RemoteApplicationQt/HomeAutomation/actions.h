#ifndef ACTIONS_H
#define ACTIONS_H
#include <QObject>
#include<QDebug>
#include<QQuickItem>
#include<QTcpSocket>


class Actions : public QObject
{
Q_OBJECT
public:
    Q_INVOKABLE void buttonClicked(QQuickItem* presentItem);

    Actions();


private:
    void sendCommand(QTcpSocket*, QString);
    QString commandMaker(QString,QString);
    void connectToServer(QTcpSocket*);
    QString getDeviceName(QString);

};

#endif // ACTIONS_H
