import QtQuick 2.9
import QtQuick.Window 2.2
import QtQuick.Controls 2.2

Window {
    visible: true
    width: 640
    height: 480

    Button {
        id: light1Button
        objectName:"light1Button"
        x: (parent.width * scaling.buttonColumnScale) - (parent.width * scaling.buttonWidthScale )/2
        y: (parent.height *0.2) -(parent.height * scaling.buttonHeightScale) / 2
        width:parent.width * scaling.buttonWidthScale
        height:parent.height * scaling.buttonHeightScale
        text: qsTr("ON")


        onClicked:actions.buttonClicked(this)
    }

    Button {
        id: light2Button
        objectName: "light2Button"
        x: (parent.width * scaling.buttonColumnScale) - (parent.width * scaling.buttonWidthScale )/2
        y: (parent.height *0.4) -(parent.height * scaling.buttonHeightScale) / 2
        width:parent.width * scaling.buttonWidthScale
        height:parent.height * scaling.buttonHeightScale
        text: qsTr("ON")
        onClicked:actions.buttonClicked(this)
    }

    Button {
        id: fan1Button
        objectName: "fan1Button"
        x: (parent.width * scaling.buttonColumnScale) - (parent.width * scaling.buttonWidthScale )/2
        y: (parent.height *0.6) -(parent.height * scaling.buttonHeightScale) / 2
        width:parent.width * scaling.buttonWidthScale
        height:parent.height * scaling.buttonHeightScale
        text: qsTr("ON")
        onClicked:actions.buttonClicked(this)
    }

    Button {
        id: fan2Button
        objectName:"fan2Button"
        x: (parent.width * scaling.buttonColumnScale) - (parent.width * scaling.buttonWidthScale )/2
        y: (parent.height *0.8) -(parent.height * scaling.buttonHeightScale) / 2
        width:parent.width * scaling.buttonWidthScale
        height:parent.height * scaling.buttonHeightScale
        text: qsTr("ON")
        onClicked:actions.buttonClicked(this)
    }

    Text{

        id: light1Text
        objectName:"light1Text"
        x: (parent.width * scaling.devNameColumnScale) - (parent.width * scaling.devNameWidthScale )/2
        y: parent.height *0.2
        width:parent.width * scaling.devNameWidthScale
        height:parent.height *scaling.devNameHeightScale
        text: qsTr("Living\nroom\nLight")

    }

    Text{

        id: light2Text
        objectName: "light2Text"
        x: (parent.width * scaling.devNameColumnScale) - (parent.width * scaling.devNameWidthScale )/2
        y: parent.height *0.4
        width:parent.width * scaling.devNameWidthScale
        height:parent.height *scaling.devNameHeightScale
        text: qsTr("Portico\nLight")

    }

    Text{

        id: fan1Text
        objectName: "fan1Text"
        x: (parent.width * scaling.devNameColumnScale) - (parent.width * scaling.devNameWidthScale )/2
        y: parent.height *0.6
        width:parent.width * scaling.devNameWidthScale
        height:parent.height *scaling.devNameHeightScale
        text: qsTr("Living\nroom\nFan")

    }

    Text{

        id: fan2Text
        objectName: "fan2Text"
        x: (parent.width * scaling.devNameColumnScale) - (parent.width * scaling.devNameWidthScale )/2
        y: parent.height *0.8
        width:parent.width * scaling.devNameWidthScale
        height:parent.height *scaling.devNameHeightScale
        text: qsTr("Portico\nFan")

    }


    Text{

        id: light1Status
        objectName: "light1Status"
        x: (parent.width * scaling.devStatusColumnScale) - (parent.width * scaling.devStatusWidthScale )/2
        y: parent.height *0.2
        width:parent.width * scaling.devStatusWidthScale
        height:parent.height *scaling.devStatusHeightScale
        text: qsTr("OFF")
        wrapMode: Text.WrapAnywhere

    }

    Text{

        id: light2Status
        objectName: "light2Status"
        x: (parent.width * scaling.devStatusColumnScale) - (parent.width * scaling.devStatusWidthScale )/2
        y: parent.height *0.4
        width:parent.width * scaling.devStatusWidthScale
        height:parent.height *scaling.devStatusHeightScale
        text: qsTr("OFF")

    }

    Text{

        id: fan1Status
        objectName: "fan1Status"
        x: (parent.width * scaling.devStatusColumnScale) - (parent.width * scaling.devStatusWidthScale )/2
        y: parent.height *0.6
        width:parent.width * scaling.devStatusWidthScale
        height:parent.height *scaling.devStatusHeightScale
        text: qsTr("OFF")

    }

    Text{

        id: fan2Status
        objectName: "fan2Status"
        x: (parent.width * scaling.devStatusColumnScale) - (parent.width * scaling.devStatusWidthScale )/2
        y: parent.height *0.8
        width:parent.width * scaling.devStatusWidthScale
        height:parent.height *scaling.devStatusHeightScale
        text: qsTr("OFF")

    }


}
