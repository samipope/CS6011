#include "game1scene.h"
#include <QApplication>
#include <QGraphicsView>
#include "bucket.h"

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);
    game1scene *scene1 = new game1scene();
    QGraphicsView *view = new QGraphicsView();
    view->setScene(scene1);
    view->setHorizontalScrollBarPolicy(Qt::ScrollBarAlwaysOff);
    view->setVerticalScrollBarPolicy(Qt::ScrollBarAlwaysOff);
    view->setFixedSize(910, 512);
    bucket *myBucket = new bucket();
    myBucket->setFlag(QGraphicsItem::ItemIsFocusable); // Make the bucket focusable
    myBucket->setFocus(); // Set focus to the bucket


    view->show();
    return app.exec();
}
