#include "water.h"
#include <QPixmap>
#include <QGraphicsScene>
#include "bucket.h"

water::water() {
    // Set the image and scale it
    setPixmap((QPixmap(":/water")).scaled(30, 30));

    // Timer setup to move the droplet downwards
    timer_drop = new QTimer(this);
    connect(timer_drop, &QTimer::timeout, this, &water::move_droplet);
    timer_drop->start(1000);  // Droplet moves every 1000 milliseconds (1 second)
}

void water::move_droplet() {
    setPos(x(), y() + 25);  // Move 25 pixels down each second


    QList<QGraphicsItem *> colliding_items = scene()->collidingItems(this);
    for (QGraphicsItem *item : colliding_items) {
        if (dynamic_cast<bucket*>(item) || dynamic_cast<water*>(item)) {  // Check if colliding with bucket or other droplets
            scene()->removeItem(this);
            delete this;
            return;  // Stop further processing since the droplet has been deleted
        }
    }

    // Check if the droplet is out of the scene bounds
    if (y() > scene()->height()) {
        scene()->removeItem(this);  // Remove from scene
        delete this;                // Delete the object to free memory
    }


}
