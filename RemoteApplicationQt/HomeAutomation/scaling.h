#ifndef SCALING_H
#define SCALING_H
#include <QObject>

class Scaling : public QObject
{
public:

    Q_OBJECT
    Q_PROPERTY(float buttonColumnScale READ getButtonColumnScale)
    Q_PROPERTY(float buttonWidthScale READ getButtonWidthScale)
    Q_PROPERTY(float buttonHeightScale READ getButtonHeightScale)

    Q_PROPERTY(float devNameColumnScale READ getDevNameColumnScale)
    Q_PROPERTY(float devNameWidthScale READ getDevNameWidthScale)
    Q_PROPERTY(float devNameHeightScale READ getDevNameHeightScale)

    Q_PROPERTY(float devStatusColumnScale READ getDevStatusColumnScale)
    Q_PROPERTY(float devStatusWidthScale READ getDevStatusWidthScale)
    Q_PROPERTY(float devStatusHeightScale READ getDevStatusHeightScale)

public:
    float getButtonColumnScale(){
        return buttonColumnScale;
    }
    float getButtonWidthScale(){
        return buttonWidthScale;
    }
    float getButtonHeightScale(){
        return buttonHeightScale;
    }
    float getDevNameColumnScale(){
        return devNameColumnScale;
    }
    float getDevNameWidthScale(){
        return devNameWidthScale;
    }
    float getDevNameHeightScale(){
        return devNameHeightScale;
    }
    float getDevStatusColumnScale(){
        return devStatusColumnScale;
    }
    float getDevStatusWidthScale(){
        return devStatusWidthScale;
    }
    float getDevStatusHeightScale(){
        return devStatusHeightScale;
    }
    Scaling();
    private:
        float buttonColumnScale=0.5;
        float buttonWidthScale=0.15;
        float buttonHeightScale=0.08;
        float devNameColumnScale=0.23;
        float devNameWidthScale=0.15;
        float devNameHeightScale=0.1;
        float devStatusColumnScale=0.75;
        float devStatusWidthScale;
        float devStatusHeightScale;

};

#endif // SCALING_H
