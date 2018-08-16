#include <QGuiApplication>
#include <QQmlApplicationEngine>
#include "scaling.h"
#include "actions.h"
#include <QQmlContext>
int main(int argc, char *argv[])
{
#if defined(Q_OS_WIN)
    QCoreApplication::setAttribute(Qt::AA_EnableHighDpiScaling);
#endif

    Scaling scaling;
    Actions actions;
    QGuiApplication app(argc, argv);

    QQmlApplicationEngine engine;
    engine.load(QUrl(QStringLiteral("qrc:/main.qml")));
    engine.rootContext()->setContextProperty("scaling", &scaling);
    engine.rootContext()->setContextProperty("actions", &actions);
    if (engine.rootObjects().isEmpty())
        return -1;

    return app.exec();
}
