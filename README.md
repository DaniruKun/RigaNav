# RigaNav Android App
![RigaNav AppIcon](https://i.imgur.com/78CNovb.png)

An Android application built in Java to help tourists arriving at the Riga International Airport (RIX) quickly get relevant bus and other transport information.
## What it can do
The app displays the following information:
* Arrival times of the 22nd bus at the airport (up to 3 soonest).
* Departure times of the corresponding buses.
* Fare for taking the bus
* Fare of different taxi providers from the airport
## What it cannot do
* Fetch data from saraksts.rigassatiksme.lv
* Manually refresh screen
* Adjust screen XML data using a UI
## How it works
### Display
To display data on the main screen, a **TableLayout** object is used, which then displays other objects such as **TextView**s.

By using child elements such as **Rows** and **Columns**, new elements can be added if needed without interfering with the existing layout properties.

A child element of **TableLayout** - **TableRow** is used and added to the screen using the **addView()** method.

### Time
To find out the local time, the app uses the **Calendar** object and the **get()** method to write the object's properties to corresponding variables of type **Int**.

## Test Cases
![Test Cases](https://i.imgur.com/RiQ3ecf.png)
