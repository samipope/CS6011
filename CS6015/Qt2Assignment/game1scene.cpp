#include "game1scene.h"
#include "bucket.h"
#include "water.h"
#include <QRandomGenerator>


game1scene::game1scene() {
    setBackgroundBrush(QBrush(QImage(":/background").scaledToHeight(512).scaledToWidth(910)));
    setBackgroundBrush(QBrush(QImage(":/background").scaled(910, 512, Qt::KeepAspectRatioByExpanding)));
    setSceneRect(0, 0, 908, 510);  // Setting the exact dimensions of the scene

    myBucket = new bucket();
    addItem(myBucket);  // Add the bucket to the scene

    // Make the bucket focusable and give it focus
    myBucket->setFlag(QGraphicsItem::ItemIsFocusable);
    myBucket->setFocus();
    myBucket->setPos(400, 365);  // Example: center position in a fixed-size view

    water *myDroplet = new water();
    addItem(myDroplet);
    int randomX = QRandomGenerator::global()->bounded(0, 910);  // Random x within scene width
    int randomY = QRandomGenerator::global()->bounded(0,100);
    myDroplet->setPos(randomX, randomY);  // Positioned randomly under the clouds


    timer = new QTimer(this);
    connect(timer, &QTimer::timeout, this, &game1scene::createDroplet);
    timer->start(1000);

}

void game1scene::createDroplet() {
    water *droplet = new water();
    addItem(droplet);
    int randomX = QRandomGenerator::global()->bounded(0, static_cast<int>(this->width()));
        droplet->setPos(randomX, 0);
}
