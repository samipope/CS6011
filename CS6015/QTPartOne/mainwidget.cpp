// mainWidget.cpp
#include "mainWidget.h"
#include <QSpacerItem>

mainWidget::mainWidget(QWidget *parent) : QWidget(parent)
{
    // Initialize the widgets
    firstNameLabel = new QLabel("First Name");
    firstNameEdit = new QLineEdit;

    lastNameLabel = new QLabel("Last Name");
    lastNameEdit = new QLineEdit;

    ageLabel = new QLabel("Age");
    ageSpinBox = new QSpinBox;

    genderGroupBox = new QGroupBox;
    QVBoxLayout *genderLayout = new QVBoxLayout;
    maleRadioButton = new QRadioButton("Male");
    femaleRadioButton = new QRadioButton("Female");
    genderLayout->addWidget(maleRadioButton);
    genderLayout->addWidget(femaleRadioButton);
    genderGroupBox->setLayout(genderLayout);

    refreshButton = new QPushButton("Refresh");
    summaryTextEdit = new QTextEdit;
    finishButton = new QPushButton("Finish");

    // Create the layouts
    mainLayout = new QVBoxLayout;
    gridLayout = new QGridLayout;

    setGridLayout();
    setVerticalLayout();

    // Connect the signals to the slots
    connect(refreshButton, SIGNAL(clicked()), this, SLOT(fillSummary()));
    connect(finishButton, &QPushButton::clicked, this, &mainWidget::clearAll);

    // Set the layout
    setLayout(mainLayout);
}

void mainWidget::setGridLayout()
{
    gridLayout->addWidget(firstNameLabel, 0, 0);
    gridLayout->addWidget(firstNameEdit, 0, 1);
    gridLayout->addWidget(lastNameLabel, 1, 0);
    gridLayout->addWidget(lastNameEdit, 1, 1);
    gridLayout->addWidget(ageLabel, 2, 0);
    gridLayout->addWidget(ageSpinBox, 2, 1);

    // Add space for better display
    gridLayout->addItem(new QSpacerItem(50, 10), 0, 2, 1, 1);
}


void mainWidget::setVerticalLayout()
{
    mainLayout->addLayout(gridLayout);
    mainLayout->addWidget(genderGroupBox);
    mainLayout->addWidget(refreshButton);
    mainLayout->addWidget(summaryTextEdit);
    mainLayout->addWidget(finishButton);
}

void mainWidget::fillSummary()
{
    QString summary;
    summary += "First Name: " + firstNameEdit->text() + "\n";
    summary += "Last Name: " + lastNameEdit->text() + "\n";
    summary += "Age: " + QString::number(ageSpinBox->value()) + "\n";
    summary += QString("Gender: ") + (maleRadioButton->isChecked() ? "Male" : "Female") + "\n";
    summaryTextEdit->setText(summary);
}

void mainWidget::clearAll(){
    firstNameEdit->clear();
    lastNameEdit->clear();
    ageSpinBox->setValue(0);
    maleRadioButton->setChecked(false);
    femaleRadioButton->setChecked(false);
    summaryTextEdit->clear();
}


