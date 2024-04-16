#include "bucket.h"
#include "QtWidgets/qgraphicsscene.h"
#include <QKeyEvent>

bucket::bucket() {
    QPixmap originalPixmap(":/bucket");
    setPixmap(originalPixmap.scaled(150, 150, Qt::KeepAspectRatio));  // Scale down and maintain aspect ratio

}

void bucket::keyPressEvent(QKeyEvent *event) {
    int step = 10;  // Movement step
    if (event->key() == Qt::Key_Right) {
        // Move right but check boundary first
        if (x() + pixmap().width() + step <= scene()->width()) {
            setPos(x() + step, y());
        }
    } else if (event->key() == Qt::Key_Left) {
        // Move left but check boundary first
        if (x() - step >= 0) {
            setPos(x() - step, y());
        }
    }
}
