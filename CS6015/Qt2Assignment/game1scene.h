#ifndef GAME1SCENE_H
#define GAME1SCENE_H
#include <QGraphicsScene>
#include <QGraphicsPixmapItem>
#include "bucket.h"
#include <QTime>


class game1scene : public QGraphicsScene
{
public:
    game1scene();

public slots:
    void createDroplet();

private:
bucket* myBucket;
    QTimer *timer;
};

#endif // GAME1SCENE_H
