#ifndef BUCKET_H
#define BUCKET_H
#include <QGraphicsPixmapItem>

class bucket : public QObject, public QGraphicsPixmapItem
{
    Q_OBJECT
public:
    bucket();
protected:
    void keyPressEvent(QKeyEvent *event) override;
};

#endif // BUCKET_H
